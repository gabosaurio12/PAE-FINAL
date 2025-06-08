package uniairlines.controladores;

import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import uniairlines.modelo.Aerolinea;
import uniairlines.util.*;
import uniairlines.excepcion.ArchivoException;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import uniairlines.dao.AerolineaDAO;
import uniairlines.dao.UsuarioDAO;
import uniairlines.modelo.Usuario;


public class FXMLPmasterController {

    static void inicializarDatos(Usuario usuarioAutenticado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private TextField tfFiltro;

    @FXML
    private TableView<Aerolinea> tablaAerolineas;

    @FXML
    private TableColumn<Aerolinea, String> colIATA;
    @FXML
    private TableColumn<Aerolinea, String> colICAO;
    @FXML
    private TableColumn<Aerolinea, String> colNombre;
    @FXML
    private TableColumn<Aerolinea, String> colCallsign;
    @FXML
    private TableColumn<Aerolinea, String> colNacionalidad;
    @FXML
    private TableColumn<Aerolinea, String> colDireccion;
    @FXML
    private TableColumn<Aerolinea, String> colSitioOficial;
    @FXML
    private TableColumn<Aerolinea, String> colContactoNombre;
    @FXML
    private TableColumn<Aerolinea, String> colContactoTelefono;

    @FXML
    private Button btnAgregarAerolinea;
    @FXML
    private Button btnModificarAerolinea;
    @FXML
    private Button btnEliminarAerolinea;
    @FXML
    private Button btnAdministrarCuentas;

    private ObservableList<Aerolinea> listaAerolineas;
    private FilteredList<Aerolinea> listaFiltrada;

    private static final String RUTA_JSON = "Datos/aerolineas.json";
    private final UtilGeneral util = new UtilGeneral();

    public void initialize() {
        try {
            cargarDatos();

            // Asignar columnas para propiedades directas
            colIATA.setCellValueFactory(new PropertyValueFactory<>("IATA"));
            colICAO.setCellValueFactory(new PropertyValueFactory<>("ICAO"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCallsign.setCellValueFactory(new PropertyValueFactory<>("callsign"));
            colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
            colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            colSitioOficial.setCellValueFactory(new PropertyValueFactory<>("sitioOficial"));

            // Para campos anidados en contacto
            colContactoNombre.setCellValueFactory(cell -> {
                Aerolinea a = cell.getValue();
                return new javafx.beans.property.SimpleStringProperty(
                        a.getContacto() != null ? a.getContacto().getNombre() : ""
                );
            });

            colContactoTelefono.setCellValueFactory(cell -> {
                Aerolinea a = cell.getValue();
                return new javafx.beans.property.SimpleStringProperty(
                        a.getContacto() != null ? a.getContacto().getTelefono() : ""
                );
            });

            // Filtro reactivo para el TextField
            listaFiltrada = new FilteredList<>(listaAerolineas, p -> true);
            tfFiltro.textProperty().addListener((obs, oldVal, newVal) -> {
                String filtro = newVal.toLowerCase().trim();
                listaFiltrada.setPredicate(aerolinea -> {
                    if (filtro.isEmpty()) return true;
                    return aerolinea.getNombre().toLowerCase().contains(filtro)
                            || aerolinea.getIATA().toLowerCase().contains(filtro)
                            || aerolinea.getICAO().toLowerCase().contains(filtro);
                });
            });

            tablaAerolineas.setItems(listaFiltrada);



        } catch (ArchivoException e) {
            UtilGeneral.mostrarAlertaSimple(AlertType.ERROR, "Error", "No se pudo cargar la lista de aerolíneas.");
        }
    }

    @FXML
    private void btnAgregarAerolinea(ActionEvent event) {
        agregarAerolinea();
    }

    @FXML
    private void btnModificarAerolinea(ActionEvent event) {
        modificarAerolinea();
    }

    @FXML
    private void btnEliminarAerolinea(ActionEvent event) {
        eliminarAerolinea();
    }

    @FXML
    private void btnAdministrarCuentas(ActionEvent event) {
        administrarCuentas();
    }


    private void cargarDatos() throws ArchivoException {
        Type tipoLista = new TypeToken<List<Aerolinea>>() {
        }.getType();
        List<Aerolinea> aerolineas = ArchivoUtil.leerJson(RUTA_JSON, tipoLista);
        listaAerolineas = FXCollections.observableArrayList(aerolineas);
    }


    private void eliminarAerolinea() {
        AerolineaDAO o = new AerolineaDAO();
        UsuarioDAO e = new UsuarioDAO();
        Aerolinea seleccionada = tablaAerolineas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            UtilGeneral.mostrarAlertaSimple(AlertType.WARNING, "Sin selección", "Por favor, seleccione una aerolínea para eliminar.");
            return;
        }

        // Crear botones personalizados
        ButtonType btnEliminar = new ButtonType("Eliminar");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert confirm = new Alert(AlertType.CONFIRMATION,
                "¿Seguro que deseas eliminar la aerolínea: " + seleccionada.getNombre() + "?",
                btnEliminar, btnCancelar);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null); // O puedes dejar el encabezado si prefieres

        confirm.showAndWait().ifPresent(response -> {
            if (response == btnEliminar) {
                try {
                    String nombre = seleccionada.getNombre();
                    if (e.tieneUsuarios(nombre)) {
                        e.eliminarUsuariosPorAerolinea(nombre);
                    }
                    o.eliminar(seleccionada);
                    listaAerolineas.remove(seleccionada);
                    ArchivoUtil.escribirJson(RUTA_JSON, listaAerolineas);
                    UtilGeneral.mostrarAlertaSimple(AlertType.INFORMATION, "Eliminado", "La aerolínea ha sido eliminada.");
                } catch (ArchivoException ex) {
                    UtilGeneral.mostrarAlertaSimple(AlertType.ERROR, "Error", "No se pudo eliminar la aerolínea.");
                }
            }
        });
    }


    private void administrarCuentas() {
        Aerolinea seleccionada = tablaAerolineas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            UtilGeneral.mostrarAlertaSimple(AlertType.WARNING, "Sin selección", "Por favor, seleccione una aerolínea para modificar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/TablaUsuarios.fxml"));
            Parent vista = loader.load();

            // Obtener controlador y pasar la aerolínea seleccionada

            TablaUsuariosController controlador = loader.getController();
            controlador.inicializarConAerolinea(seleccionada);

            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(vista);

            escenarioAdmin.setScene(escena);
            escenarioAdmin.setTitle("ADMINISTRACIÓN DE USUARIOS - Aerolínea: " + seleccionada.getNombre());
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.centerOnScreen();
            escenarioAdmin.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarAerolinea() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioAerolinea.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            FXMLFormularioAerolineaController ctrl = loader.getController();

            // Pasar null o una nueva aerolínea vacía para indicar modo "Agregar"
            ctrl.setAerolinea();  // crea nueva aerolinea vacía

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Nueva Aerolínea");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar datos después de agregar
            cargarDatos();
            listaFiltrada = new FilteredList<>(listaAerolineas, p -> true);
            tablaAerolineas.setItems(listaFiltrada);

        } catch (IOException | ArchivoException ex) {
            UtilGeneral.mostrarAlertaSimple(AlertType.ERROR, "Error", "No se pudo abrir el formulario para agregar aerolínea.");
        }

    }

    private void modificarAerolinea() {
        Aerolinea seleccionada = tablaAerolineas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin selección", "Por favor, seleccione una aerolínea para modificar.");
            return;
        }
        try {
            System.out.print(seleccionada);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioAerolinea.fxml"));
            Parent root = loader.load();

            FXMLFormularioAerolineaController ctrl = loader.getController();
            ctrl.setAerolinea(seleccionada);  // Pasar aerolínea para modificar

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Aerolínea");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Solo recarga si el usuario guardó los cambios
            if (ctrl.isGuardado()) {
                cargarDatos();
                listaFiltrada = new FilteredList<>(listaAerolineas, p -> true);
                tablaAerolineas.setItems(listaFiltrada);
            }

        } catch (IOException | ArchivoException ex) {
            UtilGeneral.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo abrir el formulario de modificación.");
        }
    }

    public void cerrarSesion() {
        util.abrirFXML("/vista/FXMLLogin.fxml", "INICIO DE SESIÓN", FXMLLoginController.class);
        Stage stage = (Stage) tfFiltro.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void btnSalir(ActionEvent event) {
        cerrarSesion();
    }

    @FXML
    public void btnPDF(ActionEvent actionEvent) {
        try {
            List<Aerolinea> aerolineas = new AerolineaDAO().listar();  // Obtener lista
            String path = "Documentos/TodasLasAerolineas.pdf";              // Ruta simple
            PDFUtil util = new PDFUtil();                              // Util que ya tienes
            util.generarPDFAerolineas(path, aerolineas);               // Generar PDF
        } catch (ArchivoException e) {
            System.err.println("Error al buscar aerolíneas: " + e.getMessage());
        }

    }

    @FXML
    public void btnCSV(ActionEvent actionEvent) {
        try {
            List<Aerolinea> aerolineas = new AerolineaDAO().listar();
            String path = "Documentos/TodasLasAerolineas.csv";
            CSVUtil util = new CSVUtil();
            util.generarCSVAerolineas(path, aerolineas);
        } catch (Exception e) {
            System.err.println("Error al exportar CSV: " + e.getMessage());
        }
    }

    @FXML
    public void btnXLSX(ActionEvent actionEvent) {
        try {
            List<Aerolinea> aerolineas = new AerolineaDAO().listar();
            String path = "Documentos/TodasLasAerolineas.xlsx";  // Corregí la extensión y nombre
            XLSXUtil util = new XLSXUtil();
            util.generarXLSXAerolineas(path, aerolineas);
        } catch (Exception e) {
            System.err.println("Error al exportar XLSX: " + e.getMessage());
        }
    }
}




