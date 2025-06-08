package uniairlines.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class FMXLInspeccionarVueloController {
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

    public void clicConfigurarTripulacion(ActionEvent actionEvent) {
    }

    public void clicCancelar(ActionEvent actionEvent) {
    }

    public void clicGuardar(ActionEvent actionEvent) {
    }
}
