package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniairlines.dao.AerolineaDAO;
import uniairlines.dao.UsuarioDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Usuario;


import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.layout.GridPane;
import uniairlines.util.UtilGeneral;

public class TablaUsuariosController implements Initializable {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colUsuario;
    @FXML private TableColumn<Usuario, String> colTipo;
    @FXML private TableColumn<Usuario, String> colNombre;

    @FXML private Button btnAgregarUsuario;
    @FXML private Button btnModificarUsuario;
    @FXML private Button btnEliminarUsuario;

    private ObservableList<Usuario> listaUsuarios;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AerolineaDAO aerolineaDAO = new AerolineaDAO();

    private final ObservableList<String> tiposValidos = FXCollections.observableArrayList("master", "admin", "empleado");

    Aerolinea seleccionada = new Aerolinea();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar columnas
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
    }

   
    

    @FXML
    private void agregarUsuario() {
        Dialog<Usuario> dialog = crearDialogoUsuario(null);
        dialog.setTitle("Agregar Usuario");
        dialog.showAndWait().ifPresent(usuario -> {
            try {
                usuarioDAO.guardar(usuario);
               
                UtilGeneral.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Usuario agregado correctamente.");
                cargarUsuarios();
            } catch (ArchivoException ex) {
                UtilGeneral.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo agregar el usuario: " + ex.getMessage());
            }
        });
    }

    @FXML
    private void modificarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING, "Advertencia", "Seleccione un usuario para modificar.");
            return;
        }

        Dialog<Usuario> dialog = crearDialogoUsuario(seleccionado);
        dialog.setTitle("Modificar Usuario");
        dialog.showAndWait().ifPresent(usuarioModificado -> {
            try {
                usuarioDAO.actualizar(usuarioModificado);
                cargarUsuarios();
                UtilGeneral.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Usuario modificado correctamente.");
            } catch (ArchivoException ex) {
                UtilGeneral.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo modificar el usuario: " + ex.getMessage());
            }
        });
    }

    @FXML
    private void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING, "Advertencia", "Seleccione un usuario para eliminar.");
            return;
        }

        Optional<ButtonType> resultado = UtilGeneral.mostrarAlertaSimple(Alert.AlertType.CONFIRMATION, "Confirmar eliminación",
                "¿Está seguro que desea eliminar el usuario " + seleccionado.getUsuario() + "?");

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                usuarioDAO.eliminar(seleccionado);
                cargarUsuarios();
                UtilGeneral.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente.");
            } catch (ArchivoException ex) {
                UtilGeneral.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el usuario: " + ex.getMessage());
            }
        }
    }

    /**
     * Crea un diálogo para agregar/modificar usuarios con validaciones específicas.
     * Si usuario es null, es para agregar nuevo.
     */
    private Dialog<Usuario> crearDialogoUsuario(Usuario usuario) {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setResizable(false);

        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();
        Label lblNombre = new Label("Nombre:");
        TextField txNombre = new TextField();
        Label lblContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        Label lblTipo = new Label("Tipo:");
        ComboBox<String> cmbTipo = new ComboBox<>(tiposValidos);
        Label lblAerolinea = new Label("Aerolinea:");
        ComboBox<String> cmbAerolinea = new ComboBox<>();

        // Cargar aerolineas
        try {
            List<Aerolinea> aerolineas = aerolineaDAO.listar();

         
            List<String> nombresAerolineas = aerolineas.stream()
                    .map(Aerolinea::getNombre)
                    .collect(Collectors.toList());

            cmbAerolinea.setItems(FXCollections.observableArrayList(nombresAerolineas));
        } catch (ArchivoException e) {
            cmbAerolinea.setItems(FXCollections.observableArrayList());
        }

        if (usuario != null) {
            txtUsuario.setText(usuario.getUsuario());
            txNombre.setText(usuario.getNombre());
            txtContrasena.setText(usuario.getContrasena());
            cmbTipo.setValue(usuario.getTipo());
            cmbAerolinea.setValue(usuario.getAerolinea());
            txtUsuario.setDisable(true); // No permitir cambiar usuario
        }

        // Mostrar/ocultar aerolinea según tipo seleccionado
        cmbTipo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("master".equalsIgnoreCase(newVal)) {
                cmbAerolinea.setDisable(true);
                cmbAerolinea.setValue("");
            } else {
                cmbAerolinea.setDisable(false);
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(lblUsuario, 0, 0);
        grid.add(txtUsuario, 1, 0);

        grid.add(lblNombre, 0, 1);
        grid.add(txNombre, 1, 1);

        grid.add(lblContrasena, 0, 2);
        grid.add(txtContrasena, 1, 2);

        grid.add(lblTipo, 0, 3);
        grid.add(cmbTipo, 1, 3);

        grid.add(lblAerolinea, 0, 4);
        grid.add(cmbAerolinea, 1, 4);


        dialog.getDialogPane().setContent(grid);

        ButtonType botonAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(botonAceptar, ButtonType.CANCEL);

        // Validar antes de cerrar el diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == botonAceptar) {
                String u = txtUsuario.getText().trim();
                String c = txtContrasena.getText().trim();
                String t = cmbTipo.getValue();
                String a = cmbAerolinea.getValue();
                String n = txNombre.getText().trim();

                // Validar campos
                if (u.isEmpty() || c.isEmpty() || t == null || (!"master".equalsIgnoreCase(t) && (a == null || a.isEmpty()))) {
                    UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING, "Validación", "Complete todos los campos correctamente.");
                    return null;
                }

                // Validar tipo válido
                if (!tiposValidos.contains(t.toLowerCase())) {
                    UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING, "Validación", "Tipo de usuario no válido.");
                    return null;
                }

                // Si es master, la aerolinea debe ser vacía
                if ("master".equalsIgnoreCase(t)) {
                    a = "";
                }

                // Retornar usuario con nombre vacio (puedes modificar para pedir nombre)
                return new Usuario(u, c, t.toLowerCase(), a, n);
            }
            return null;
        });

        return dialog;
    }

 
    void inicializarConAerolinea(Aerolinea seleccionada) {
        this.seleccionada = seleccionada;
        cargarUsuarios();
    }

 

    private void cargarUsuarios() {
         try {
        List<Usuario> usuarios = usuarioDAO.listar();

        // Filtrar para que los usuarios tipo "master" no tengan aerolínea (opcional)
        usuarios = usuarios.stream()
                .filter(u -> !("master".equalsIgnoreCase(u.getTipo()) && u.getAerolinea() != null && !u.getAerolinea().isEmpty()))
                .collect(Collectors.toList());

        // Filtrar por nombre de aerolínea
        String nombreAerolinea = seleccionada.getNombre();
        usuarios = usuarios.stream()
                .filter(u -> nombreAerolinea.equalsIgnoreCase(u.getAerolinea()))
                .collect(Collectors.toList());

        listaUsuarios = FXCollections.observableArrayList(usuarios);
        tablaUsuarios.setItems(listaUsuarios);

    } catch (ArchivoException ex) {
        UtilGeneral.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo cargar la lista de usuarios: " + ex.getMessage());
    } 
    }
    


}
