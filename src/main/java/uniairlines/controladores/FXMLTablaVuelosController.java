package uniairlines.controladores;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.aerolinea.Aeropuerto;

import java.time.LocalDateTime;

public class FXMLTablaVuelosController {
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
    private TableColumn<Aeropuerto, String> colDestino;
    @FXML
    private TableColumn<Vuelo, LocalDateTime> colTiempoLlegada;
    @FXML
    private TableColumn<Aeropuerto, String> colOrigen;
    @FXML
    private TableColumn<Vuelo, LocalDateTime> colTiempoPartida;

    private ObservableList<Vuelo> vuelos;
    //TODO private final VueloDAO vueloDAO = new VueloDAO();

    @FXML
    private void initialize() {
        colCodigoVuelo.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        colAerolinea.setCellValueFactory(new PropertyValueFactory<>("aerolinea"));
        colAvion.setCellValueFactory(new PropertyValueFactory<>("modeloAvion"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colTiempoLlegada.setCellValueFactory(new PropertyValueFactory<>("fechaLlegada"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("salida"));
        colTiempoPartida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        //TODO cargarVuelos();
    }

    /*TODO
    private void cargarVuelos() {
        try {


        } catch (ArchivoException aex) {

        }
    }
    */

    public void clicRegresar(ActionEvent actionEvent) {
    }

    public void clicInspeccionar(ActionEvent actionEvent) {
    }

    public void clicAgregar(ActionEvent actionEvent) {
    }

    public void clicActualizar(ActionEvent actionEvent) {
    }

    public void clicEliminar(ActionEvent actionEvent) {
    }

    public void clicImprimir(ActionEvent actionEvent) {
    }

    public void clicCancelar(ActionEvent actionEvent) {
    }

    public void clicGuardar(ActionEvent actionEvent) {
    }
}
