package uniairlines.controladores;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;

import java.net.URL;
import java.util.ResourceBundle;

public class FMXLAgendarVueloController implements Initializable {
    @FXML
    private TextField tfCodigoVuelo;
    @FXML
    private ComboBox comboAerolinea;
    @FXML
    private ComboBox comboAvion;
    @FXML
    private ComboBox comboPuertoSalida;
    @FXML
    private ComboBox comboPuertoLlegada;
    @FXML
    private Slider sliderPuertaSalida;
    @FXML
    private Slider sliderPuertaLlegada;
    @FXML
    private TextField tfFechaHoraSalida;
    @FXML
    private TextField tfFechaHoraLlegada;
    @FXML
    private TextField tfPrecioNegocios;
    @FXML
    private TextField tfPrecioTurista;
    @FXML
    private TextField tfPrecioPrimeraClase;

    private ObservableList<Aerolinea> aerolineas;
    private ObservableList<Avion> aviones;

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void configurarCombos() {

    }

    public void cargarAerolineas() {

    }

    public void clicConfigurarTripulacion(ActionEvent actionEvent) {
    }

    public void clicCancelar(ActionEvent actionEvent) {
    }

    public void clicGuardar(ActionEvent actionEvent) {

    }

    public void validarDatos() {
        Vuelo vueloCandidato = new Vuelo();
        vueloCandidato.setCodigoVuelo(tfCodigoVuelo.getText());
    }
}
