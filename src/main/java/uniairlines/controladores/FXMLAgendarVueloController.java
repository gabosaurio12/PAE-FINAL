package uniairlines.controladores;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import uniairlines.dao.AerolineaDAO;
import uniairlines.dao.AeropuertoDAO;
import uniairlines.dao.AvionDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.aerolinea.Aeropuerto;
import uniairlines.util.UtilGeneral;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAgendarVueloController implements Initializable {
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
    private ObservableList<Aeropuerto> aeropuertos;

    public void initialize(URL url, ResourceBundle rb) {
        configurarCombos();
        seleccionarAerolinea();
    }

    public void cargarDatos(Vuelo vuelo) {
        if(vuelo != null) {
            //TODO -update
        }
    }

    public void configurarCombos() {
        cargarAerolineas();
    }

    private void cargarAerolineas() {
        aerolineas = FXCollections.observableArrayList();
        try {
            AerolineaDAO dao = new AerolineaDAO();
            List<Aerolinea> aerolineasDAO = dao.listar();
            aerolineas.addAll(aerolineasDAO);
            comboAerolinea.setItems(aerolineas);
        } catch (ArchivoException aex) {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    String.format("%s \n %s", aex.getMessage(), aex.getCause()),
                    Alert.AlertType.ERROR);
        }
    }

    private void seleccionarAerolinea() {
        comboAerolinea.valueProperty().addListener(new ChangeListener<Aerolinea>() {
            @Override
            public void changed(ObservableValue<? extends Aerolinea> observable, Aerolinea oldValue, Aerolinea newValue) {
                if(newValue != null) {
                    cargarAviones(newValue.getNombre());
                    cargarAeropuertos(newValue.getNombre());
                }
            }
        });
    }

    private void cargarAviones(String nombreAerolinea) {
        try {
            AvionDAO dao = new AvionDAO();
            aviones = FXCollections.observableArrayList();
            List<Avion> avionesDAO = dao.listar(nombreAerolinea);
            aviones.addAll(avionesDAO);
            comboAvion.setItems(aviones);
        } catch (ArchivoException aex) {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    String.format("%s \n %s", aex.getMessage(), aex.getCause()),
                    Alert.AlertType.ERROR);
        }
    }

    private void cargarAeropuertos(String nombreAerolinea) {
        try {
            AeropuertoDAO dao = new AeropuertoDAO();
            aeropuertos = FXCollections.observableArrayList();
            List<Aeropuerto> aeropuertoDAO = dao.listar(nombreAerolinea);
            aeropuertos.addAll(aeropuertoDAO);
            comboPuertoSalida.setItems(aeropuertos);
            comboPuertoLlegada.setItems(aeropuertos);
        } catch (ArchivoException aex) {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    String.format("%s \n %s", aex.getMessage(), aex.getCause()),
                    Alert.AlertType.ERROR);
        }
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
