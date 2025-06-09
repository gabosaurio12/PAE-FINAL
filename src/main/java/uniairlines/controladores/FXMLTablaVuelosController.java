package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniairlines.dao.VueloDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.aerolinea.Aeropuerto;
import uniairlines.util.ResultadoFXML;
import uniairlines.util.UtilGeneral;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLTablaVuelosController implements Initializable {
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
    private UtilGeneral util = new UtilGeneral();
    private final VueloDAO vueloDAO = new VueloDAO();

    @Override
    public void initialize(URL url, ResourceBundle resources) {
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
            vuelos = FXCollections.observableArrayList(vueloDAO.recuperarVuelos());
            tablaVuelos.setItems(vuelos);
        } catch (ArchivoException aex) {
            UtilGeneral.mostrarAlerta("Error", String.format("%s \n %s", aex.getMessage(), aex.getCause()), Alert.AlertType.ERROR);
        }
    }

    public void clicRegresar(ActionEvent actionEvent) {
        util.abrirFXML("/vista/FXMLPadmin.fxml", "Principal", FXMLPadminController.class);
        ((Stage) tfBuscarPorCodigo.getScene().getWindow()).close();
    }

    public void clicInspeccionar(ActionEvent actionEvent) {

    }

    public void clicAgregar(ActionEvent actionEvent) {
        ResultadoFXML<FXMLAgendarVueloController> resultado = util.abrirFXMLModal(
                "/vista/FMXLAgendarVuelo.fxml",
                "Registrar Vuelo",
                FXMLAgendarVueloController.class,
                tfBuscarPorCodigo.getScene().getWindow());
        if(resultado != null) {
            FXMLAgendarVueloController controlador = resultado.getControlador();
            Stage stage = resultado.getStage();
            controlador.cargarDatos(this,null);
            stage.showAndWait();
            //TODO refrescarTabla
        }
    }

    public void clicActualizar(ActionEvent actionEvent) {
    }

    public void clicEliminar(ActionEvent actionEvent) {
    }

    public void clicImprimir(ActionEvent actionEvent) {

    }

    public ObservableList<Vuelo> getVuelos() {
        return this.vuelos;
    }
}