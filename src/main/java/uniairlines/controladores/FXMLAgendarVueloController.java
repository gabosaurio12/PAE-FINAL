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
import javafx.stage.Stage;
import uniairlines.dao.AerolineaDAO;
import uniairlines.dao.AeropuertoDAO;
import uniairlines.dao.AvionDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.excepcion.VueloException;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.aerolinea.Aeropuerto;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;
import uniairlines.util.ResultadoFXML;
import uniairlines.util.UtilGeneral;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAgendarVueloController implements Initializable {

    @FXML
    private TextField tfCodigoVuelo;
    @FXML
    private ComboBox<Aerolinea> comboAerolinea;
    @FXML
    private ComboBox<Avion> comboAvion;
    @FXML
    private ComboBox<Aeropuerto> comboPuertoSalida;
    @FXML
    private ComboBox<Aeropuerto> comboPuertoLlegada;
    @FXML
    private ComboBox<Integer> comboPuertaLlegada;
    @FXML
    private ComboBox<Integer> comboPuertaSalida;
    @FXML
    private TextField tfFechaHoraSalida;
    @FXML
    private TextField tfFechaHoraLlegada;
    @FXML
    private TextField tfMinutosEstimados;
    @FXML
    private TextField tfPrecioNegocios;
    @FXML
    private TextField tfPrecioTurista;
    @FXML
    private TextField tfPrecioPrimeraClase;

    private final UtilGeneral util = new UtilGeneral();

    private ObservableList<Aerolinea> aerolineas;
    private ObservableList<Avion> aviones;
    private ObservableList<Aeropuerto> aeropuertos;
    private Piloto piloto;
    private Piloto copiloto;
    private List<AsistenteVuelo> asistentes;

    public void initialize(URL url, ResourceBundle rb) {
        configurarCombos();
        seleccionarAerolinea();
        seleccionarAeropuertoLlegada();
        seleccionarAeropuertoSalida();
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
                    comboPuertaSalida.setItems(null);
                    comboPuertaLlegada.setItems(null);
                }
            }
        });
    }

    private void seleccionarAeropuertoLlegada() {
        comboPuertoLlegada.valueProperty().addListener(new ChangeListener<Aeropuerto>() {
            @Override
            public void changed(ObservableValue<? extends Aeropuerto> observable, Aeropuerto oldValue, Aeropuerto newValue) {
                if(newValue != null) {
                    cargarNumPuertas(comboPuertaLlegada, newValue.getNumPuertas());
                }
            }
        });
    }

    private void seleccionarAeropuertoSalida() {
        comboPuertoSalida.valueProperty().addListener(new ChangeListener<Aeropuerto>() {
            @Override
            public void changed(ObservableValue<? extends Aeropuerto> observable, Aeropuerto oldValue, Aeropuerto newValue) {
                if(newValue != null) {
                    cargarNumPuertas(comboPuertaSalida, newValue.getNumPuertas());
                }
            }
        });
    }

    private void cargarNumPuertas(ComboBox<Integer> combo, Integer numPuertas) {
        List<Integer> puertas = new ArrayList<>();
        for(int i = 1; i <= numPuertas; i++) {
            puertas.add(i);
        }
        combo.setItems(FXCollections.observableArrayList(puertas));
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
        String nombreAerolinea = obtenerNombreAerolinea();
        if(nombreAerolinea != null) {
            ResultadoFXML<FXMLConfigurarTripulacionController> resultado = util.abrirFXMLModal(
                    "/vista/FXMLConfigurarTripulacion.fxml",
                    "Configurar Tripulación",
                    FXMLConfigurarTripulacionController.class,
                    tfCodigoVuelo.getScene().getWindow());
            if(resultado != null) {
                FXMLConfigurarTripulacionController controlador = resultado.getControlador();
                Stage stage = resultado.getStage();
                controlador.cargarDatos(null, nombreAerolinea);
                stage.showAndWait();
            }
        } else {
            UtilGeneral.mostrarAlerta("Error",
                    "Primero debera seleccionar una aerolinea",
                    Alert.AlertType.ERROR);
        }
    }

    public void clicCancelar(ActionEvent actionEvent) {
    }

    public void clicGuardar(ActionEvent actionEvent) {
        if(validarCampos()) {
            Vuelo candidato = construirVueloCandidato();

        }
    }

    private boolean validarCampos() {
        boolean validos = true;
        try {
            String codigoVuelo;
            String aerolinea;
            String codigoAvion;
            Aeropuerto salida;
            Aeropuerto destino;
            String fechaSalida;
            String fechaLlegada;
            Integer minutosDeVueloEstimados;
            Integer puertaSalida;
            Integer puertaLlegada;
            Double precioTurista;
            Double precioNegocios;
            Double precioPrimeraClase;
            codigoVuelo = tfCodigoVuelo.getText();
            if(codigoVuelo.isEmpty()) {
               validos = false;
            }
            aerolinea = comboAerolinea.getSelectionModel().getSelectedItem().toString();
            if(aerolinea.isEmpty()) {
                validos = false;
            }
            codigoAvion = comboAvion.getSelectionModel().getSelectedItem().toString();
            if(codigoAvion.isEmpty()) {
                validos = false;
            }
            salida = comboPuertoSalida.getSelectionModel().getSelectedItem();
            if(salida == null) {
                validos = false;
            }
            destino = comboPuertoLlegada.getSelectionModel().getSelectedItem();
            if(destino == null) {
                validos = false;
            }
            fechaSalida = tfFechaHoraSalida.getText();
            if(fechaSalida.isEmpty()) {
                validos = false;
            }
            fechaLlegada = tfFechaHoraLlegada.getText();
            if(fechaLlegada.isEmpty()) {
                validos = false;
            }
            minutosDeVueloEstimados = Integer.parseInt(tfMinutosEstimados.getText());
            if(minutosDeVueloEstimados <= 0) {
                validos = false;
                throw new VueloException("Los minutos del vuelo no pueden ser iguales o menores a cero");
            }
            puertaSalida = comboPuertaSalida.getSelectionModel().getSelectedItem();
            if(puertaSalida == null) {
                validos = false;
            }
            puertaLlegada = comboPuertaLlegada.getSelectionModel().getSelectedItem();
            if(puertaLlegada == null) {
                validos = false;
            }
            precioTurista = Double.parseDouble(tfPrecioTurista.getText());
            if(precioTurista <= 0) {
                validos = false;
                throw new VueloException("El precio de un boleto no puede ser igual o menor a cero");
            }
            precioNegocios = Double.parseDouble(tfPrecioNegocios.getText());
            if(precioNegocios <= 0) {
                validos = false;
                throw new VueloException("El precio de un boleto no puede ser igual o menor a cero");
            }
            precioPrimeraClase = Double.parseDouble(tfPrecioPrimeraClase.getText());
            if(precioPrimeraClase <= 0) {
                validos = false;
                throw new VueloException("El precio de un boleto no puede ser igual o menor a cero");
            }
            if(piloto == null) {
                validos = false;
                throw new VueloException("Se requiere un piloto");
            }
            if(copiloto == null) {
                validos = false;
                throw new VueloException("Se requiere un copiloto");
            }
            if(asistentes == null || asistentes.size() < 4) {
                validos = false;
                throw new VueloException("No hay suficientes asistentes registrados para este vuelo");
            }
            if(!validos) {
                throw new VueloException("Faltan campos por llenar");
            }
        } catch (VueloException vex) {
            UtilGeneral.mostrarAlerta("Error", vex.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException nfex) {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    "Por favor ingrese datos numericos en los campos requeridos",
                    Alert.AlertType.WARNING);
        }
        return validos;
    }

    private boolean validarDatos() {
        //TODO
        return false;
    }

    //Depende de una validacion previa de los campos
    private Vuelo construirVueloCandidato() {
        Vuelo vueloCandidato = new Vuelo();
        vueloCandidato.setCodigoVuelo(tfCodigoVuelo.getText());
        vueloCandidato.setAerolinea(comboAerolinea.getSelectionModel().getSelectedItem().toString());
        vueloCandidato.setCodigoAvion(comboAvion.getSelectionModel().getSelectedItem().toString());
        vueloCandidato.setNumPasajeros(0);
        vueloCandidato.setSalida(comboPuertoSalida.getSelectionModel().getSelectedItem());
        vueloCandidato.setDestino(comboPuertoLlegada.getSelectionModel().getSelectedItem());
        vueloCandidato.setFechaSalida(tfFechaHoraSalida.getText());
        vueloCandidato.setFechaLlegada(tfFechaHoraLlegada.getText());
        vueloCandidato.setFechaSalida(tfFechaHoraSalida.getText());
        vueloCandidato.setFechaLlegada(tfFechaHoraLlegada.getText());
        vueloCandidato.setMinutosDeVueloEstimados(Integer.parseInt(tfMinutosEstimados.getText()));
        vueloCandidato.setPuertaSalida(comboPuertaSalida.getSelectionModel().getSelectedItem());
        vueloCandidato.setPuertaLlegada(comboPuertaLlegada.getSelectionModel().getSelectedItem());
        vueloCandidato.setPrecioTurista(Double.parseDouble(tfPrecioTurista.getText()));
        vueloCandidato.setPrecioNegocios(Double.parseDouble(tfPrecioNegocios.getText()));
        vueloCandidato.setPrecioPrimeraClase(Double.parseDouble(tfPrecioPrimeraClase.getText()));
        return vueloCandidato;
    }

    private String obtenerNombreAerolinea() {
        if(comboAerolinea.getSelectionModel().getSelectedItem() != null) {
            return comboAerolinea.getSelectionModel().getSelectedItem().toString();
        } else {
            return null;
        }
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public void setCopiloto(Piloto copiloto) {
        this.copiloto = copiloto;
    }

    public void setAsistentes(List<AsistenteVuelo> asistentes) {
        this.asistentes = asistentes;
    }
}