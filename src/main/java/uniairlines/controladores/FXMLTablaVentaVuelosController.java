package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uniairlines.dao.AvionDAO;
import uniairlines.dao.VueloDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.aerolinea.Aeropuerto;
import uniairlines.util.UtilGeneral;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FXMLTablaVentaVuelosController implements Initializable {
    @FXML
    private TextField tfBuscarPorCodigo;
    @FXML
    private TableView<Vuelo> tablaVuelos;
    @FXML
    private TableColumn<Vuelo, String> colCodigoVuelo;
    @FXML
    private TableColumn<Vuelo, String> colAerolinea;
    @FXML
    private TableColumn<Vuelo, String> colAvion;
    @FXML
    private TableColumn<Vuelo, Integer> colPasajeros;
    @FXML
    private TableColumn<Vuelo, Aeropuerto> colDestino;
    @FXML
    private TableColumn<Vuelo, String> colTiempoLlegada;
    @FXML
    private TableColumn<Vuelo, Aeropuerto> colOrigen;
    @FXML
    private TableColumn<Vuelo, String> colTiempoPartida;

    private ObservableList<Vuelo> vuelos;
    private final VueloDAO vueloDAO = new VueloDAO();
    private final UtilGeneral util = new UtilGeneral();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        cargarVuelos();
    }

    private void configurarTabla() {
        colCodigoVuelo.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        colAerolinea.setCellValueFactory(new PropertyValueFactory<>("aerolinea"));
        colAvion.setCellValueFactory(new PropertyValueFactory<>("codigoAvion"));
        colPasajeros.setCellValueFactory(new PropertyValueFactory<>("numPasajeros"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colTiempoLlegada.setCellValueFactory(new PropertyValueFactory<>("fechaLlegada"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("salida"));
        colTiempoPartida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
    }

    private void cargarVuelos() {
        try {
            ObservableList<Vuelo> todosLosVuelos = FXCollections.observableArrayList(vueloDAO.recuperarVuelos());
            vuelos = FXCollections.observableArrayList();

            LocalDateTime ahora = LocalDateTime.now();

            for (Vuelo vuelo : todosLosVuelos) {
                try {
                    LocalDateTime fechaHoraSalida = LocalDateTime.parse(vuelo.getFechaSalida(), formatter);
                    if (fechaHoraSalida.isAfter(ahora)) {
                        vuelos.add(vuelo);
                    }
                } catch (Exception e) {
                    System.err.println("Error al parsear la fecha de salida del vuelo " + vuelo.getCodigoVuelo() + ": " + e.getMessage());
                }
            }

            tablaVuelos.setItems(vuelos);
        } catch (ArchivoException ex) {
            util.mostrarAlerta("Error", String.format("%s\n%s", ex.getMessage(), ex.getCause()), Alert.AlertType.ERROR);
        }
    }

    public void exportarCSV(ActionEvent actionEvent) {
        // TODO
    }

    public void exportarXLSX(ActionEvent actionEvent) {
        // TODO
    }

    public void exportarPDF(ActionEvent actionEvent) {
        // TODO
    }

    public void clicVenderBoleto(ActionEvent actionEvent) {
        Vuelo vueloSeleccionado = tablaVuelos.getSelectionModel().getSelectedItem();

        if (vueloSeleccionado == null) {
            util.mostrarAlerta("Sin selección", "Por favor selecciona un vuelo para continuar con la venta.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Instancia DAO para avión
            AvionDAO avionDAO = new AvionDAO();

            // Recupera el avión usando aerolínea y código de avión del vuelo
            Avion avionSeleccionado = avionDAO.buscarPorId(vueloSeleccionado.getAerolinea(), vueloSeleccionado.getCodigoAvion());

            if (avionSeleccionado == null) {
                util.mostrarAlerta("Error", "No se encontró el avión para el vuelo seleccionado.", Alert.AlertType.ERROR);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLTablaSeleccionarCliente.fxml"));
            Parent root = loader.load();

            // Pasar vuelo y avión al controlador de selección de cliente
            FXMLTablaSeleccionarClienteController controller = loader.getController();
            controller.setVueloSeleccionado(vueloSeleccionado);
            controller.setAvionSeleccionado(avionSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Seleccionar Cliente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException | ArchivoException e) {
            util.mostrarAlerta("Error al abrir la ventana", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void clicInspeccionar(ActionEvent actionEvent) {
        // TODO
    }

    public void clicRegresar(ActionEvent actionEvent) {
        // TODO
    }
}
