package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniairlines.dao.ClienteDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Cliente;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FXMLTablaSeleccionarClienteController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> nombre;
    @FXML private TableColumn<Cliente, String> apellidoP;
    @FXML private TableColumn<Cliente, String> apellidoM;
    @FXML private TableColumn<Cliente, String> nacionalidad;
    @FXML private TableColumn<Cliente, String> fechaNacimiento;
    @FXML private TextField tfBuscarNombre;

    private ObservableList<Cliente> listaClientes;
    private final ClienteDAO clienteDAO = new ClienteDAO();

    private Vuelo vueloSeleccionado;  // NUEVO atributo para vuelo
    private Avion avionSeleccionado;


    @FXML
    public void initialize() {
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoP"));
        apellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoM"));
        nacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        fechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        cargarClientes();
    }

    public void setVueloSeleccionado(Vuelo vuelo) {
        this.vueloSeleccionado = vuelo;
        // Puedes hacer algo con el vuelo si quieres
    }

    public void setAvionSeleccionado(Avion avion) {
        this.avionSeleccionado = avion;
        // Aquí puedes cargar o mostrar asientos si quieres
    }

    private void cargarClientes() {
        try {
            List<Cliente> clientes = clienteDAO.listar();
            listaClientes = FXCollections.observableArrayList(clientes);
            tablaClientes.setItems(listaClientes);
        } catch (ArchivoException e) {
            mostrarAlerta("Error", "No se pudieron cargar los clientes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void clicBuscarNombre(ActionEvent event) {
        String nombreBuscado = tfBuscarNombre.getText().trim().toLowerCase();

        if (nombreBuscado.isEmpty()) {
            tablaClientes.setItems(listaClientes);
            return;
        }

        List<Cliente> filtrados = listaClientes.stream()
                .filter(cliente -> cliente.getNombre().toLowerCase().contains(nombreBuscado))
                .collect(Collectors.toList());

        tablaClientes.setItems(FXCollections.observableArrayList(filtrados));
    }

    @FXML
    public void clicVender(ActionEvent event) {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un cliente antes de continuar.", Alert.AlertType.WARNING);
            return;
        }

        if (vueloSeleccionado == null) {
            mostrarAlerta("Error", "No se ha seleccionado ningún vuelo.", Alert.AlertType.ERROR);
            return;
        }

        if (avionSeleccionado == null) {
            mostrarAlerta("Error", "No se ha cargado el avión correspondiente.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLSeleccionarAsientosCliente.fxml"));
            Parent root = loader.load();

            // Pasar el cliente, vuelo y avión seleccionado al controlador de selección de asientos
            FXMLSeleccionarAsientosClienteController controlador = loader.getController();
            controlador.setCliente(clienteSeleccionado);
            controlador.setVuelo(vueloSeleccionado);
            controlador.setAvion(avionSeleccionado);  // <-- aquí el avión

            Stage stage = new Stage();
            stage.setTitle("Seleccionar Asientos para " + clienteSeleccionado.getNombre());
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar ventana actual
            ((Stage) tablaClientes.getScene().getWindow()).close();

        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de selección de asientos: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void clicRegresar(ActionEvent event) {
        Stage stage = (Stage) tablaClientes.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
