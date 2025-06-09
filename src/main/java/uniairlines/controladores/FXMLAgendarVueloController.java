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
import uniairlines.dao.VueloDAO;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private FXMLTablaVuelosController tablaVuelosController;
    private final UtilGeneral util = new UtilGeneral();

    private ObservableList<Aerolinea> aerolineas;
    private ObservableList<Avion> aviones;
    private ObservableList<Aeropuerto> aeropuertos;
    private Tripulacion tripulacion;
    private Vuelo vueloEdicion;

    public void initialize(URL url, ResourceBundle rb) {
        configurarCombos();
        seleccionarAerolinea();
        seleccionarAeropuertoLlegada();
        seleccionarAeropuertoSalida();
    }

    public void cargarDatos(FXMLTablaVuelosController tablaVuelosController, Vuelo vuelo) {
        this.tablaVuelosController = tablaVuelosController;
        if(vuelo == null) {
            this.tripulacion = null;
        } else {
            this.vueloEdicion = vuelo;
            cargarEdicion(vuelo);
        }
    }

    private void cargarEdicion(Vuelo vuelo) {
        tfCodigoVuelo.setText(vuelo.getCodigoVuelo());
        tfCodigoVuelo.setDisable(true);
        comboAerolinea.getSelectionModel().select(obtenerPosicionAerolinea(vuelo.getAerolinea()));
        comboAvion.getSelectionModel().select(obtenerPosicionAvion(vuelo.getCodigoAvion()));
        comboPuertoSalida.getSelectionModel().select(vuelo.getSalida());
        comboPuertoLlegada.getSelectionModel().select(vuelo.getDestino());
        comboPuertaSalida.getSelectionModel().select(vuelo.getPuertaSalida());
        comboPuertaLlegada.getSelectionModel().select(vuelo.getPuertaLlegada());
        tfFechaHoraSalida.setText(vuelo.getFechaSalida());
        tfFechaHoraLlegada.setText(vuelo.getFechaLlegada());
        tfMinutosEstimados.setText(String.valueOf(vuelo.getMinutosDeVueloEstimados()));
        tfPrecioTurista.setText(String.valueOf(vuelo.getPrecioTurista()));
        tfPrecioNegocios.setText(String.valueOf(vuelo.getPrecioNegocios()));
        tfPrecioPrimeraClase.setText(String.valueOf(vuelo.getPrecioPrimeraClase()));
        tripulacion = new Tripulacion();
        tripulacion.setPiloto(vuelo.getPiloto());
        tripulacion.setCopiloto(vuelo.getCopiloto());
        tripulacion.setAsistentes(vuelo.getAsistentes());
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

    private int obtenerPosicionAerolinea(String nombreAerolinea) {
        int contador = 0;
        for (Aerolinea aerolinea : aerolineas) {
            if (aerolinea.getNombre().equals(nombreAerolinea)) {
                return contador;
            }
            contador++;
        }
        return 0;
    }

    private int obtenerPosicionAvion(String codigoAvion) {
        int contador = 0;
        for (Avion avion : aviones) {
            if (avion.getId().equals(codigoAvion)) {
                return contador;
            }
            contador++;
        }
        return 0;
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
                controlador.cargarDatos(this, tripulacion, nombreAerolinea);
                stage.showAndWait();
            }
        } else {
            UtilGeneral.mostrarAlerta("Error",
                    "Primero debera seleccionar una aerolinea",
                    Alert.AlertType.ERROR);
        }
    }

    public void clicCancelar(ActionEvent actionEvent) {
        Stage stage = (Stage) tfCodigoVuelo.getScene().getWindow();
        stage.close();
    }

    public void clicGuardar(ActionEvent actionEvent) {
        if(validarCampos()) {
            Vuelo candidato = construirVueloCandidato();
            if(validarDatos(candidato)) {
                //Registrar nuevo
                if(vueloEdicion == null) {
                    tablaVuelosController.getVuelos().add(candidato);
                    try{
                        VueloDAO dao = new VueloDAO();
                        dao.agregar(candidato);
                        UtilGeneral.mostrarAlerta(
                                "Exito",
                                "El vuelo se registro exitosamente",
                                Alert.AlertType.INFORMATION);
                    } catch (ArchivoException aex) {
                        UtilGeneral.mostrarAlerta(
                                "Error",
                                aex.getMessage(),
                                Alert.AlertType.ERROR);
                    }
                //Actualizar
                } else {
                    int indiceActualizar = tablaVuelosController.getVuelos().indexOf(vueloEdicion);
                    tablaVuelosController.getVuelos().set(indiceActualizar, candidato);
                    try{
                        VueloDAO dao = new VueloDAO();
                        dao.actualizar(candidato);
                        UtilGeneral.mostrarAlerta(
                                "Exito",
                                "El vuelo se actualizo exitosamente",
                                Alert.AlertType.INFORMATION);
                    } catch (ArchivoException aex) {
                        UtilGeneral.mostrarAlerta(
                                "Error",
                                aex.getMessage(),
                                Alert.AlertType.ERROR);
                    }
                }
            }
        }
    }

    private boolean validarCampos() {
        boolean validos = true;
        try {
            if(tfCodigoVuelo.getText().isEmpty()) {
               validos = false;
            }
            if(comboAerolinea.getSelectionModel().getSelectedItem() == null) {
                validos = false;
            }
            if(comboAvion.getSelectionModel().getSelectedItem() == null) {
                validos = false;
            }
            if(comboPuertoSalida.getSelectionModel().getSelectedItem() == null) {
                validos = false;
            }
            if(comboPuertoLlegada.getSelectionModel().getSelectedItem() == null) {
                validos = false;
            }
            if(tfFechaHoraSalida.getText().isEmpty()) {
                validos = false;
            }
            if(tfFechaHoraLlegada.getText().isEmpty()) {
                validos = false;
            }
            Integer minutosDeVueloEstimados = Integer.parseInt(tfMinutosEstimados.getText());
            if(minutosDeVueloEstimados <= 0) {
                validos = false;
                throw new VueloException("Los minutos del vuelo no pueden ser iguales o menores a cero");
            }
            if(comboPuertaSalida.getSelectionModel().getSelectedItem() == null) {
                validos = false;
            }
            if(comboPuertaLlegada.getSelectionModel().getSelectedItem() == null) {
                validos = false;
            }
            Double precioTurista = Double.parseDouble(tfPrecioTurista.getText());
            if(precioTurista <= 0) {
                validos = false;
                throw new VueloException("El precio de un boleto no puede ser igual o menor a cero");
            }
            Double precioNegocios = Double.parseDouble(tfPrecioNegocios.getText());
            if(precioNegocios <= 0) {
                validos = false;
                throw new VueloException("El precio de un boleto no puede ser igual o menor a cero");
            }
            Double precioPrimeraClase = Double.parseDouble(tfPrecioPrimeraClase.getText());
            if(precioPrimeraClase <= 0) {
                validos = false;
                throw new VueloException("El precio de un boleto no puede ser igual o menor a cero");
            }
            if(this.tripulacion == null) {
                validos = false;
                throw new VueloException("No hay una tripulacion registrada para este vuelo");
            }
            if(tripulacion.getPiloto() == null) {
                validos = false;
                throw new VueloException("Se requiere un piloto");
            }
            if(tripulacion.getCopiloto() == null) {
                validos = false;
                throw new VueloException("Se requiere un copiloto");
            }
            if(tripulacion.getAsistentes() == null || tripulacion.getAsistentes().size() < 4) {
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

    private boolean validarDatos(Vuelo vueloCandidato) {
        boolean validos = true;
        try{
            if(Objects.equals(vueloCandidato.getSalida().toString(), vueloCandidato.getDestino().toString())) {
                validos = false;
                throw new VueloException("Un vuelo no puede salir y llegar al mismo aeropuerto");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fechaSalida = LocalDateTime.parse(vueloCandidato.getFechaSalida(), formatter);
            LocalDateTime fechaLlegada = LocalDateTime.parse(vueloCandidato.getFechaLlegada(), formatter);
            if(fechaLlegada.isBefore(fechaSalida)) {
                validos = false;
                throw new VueloException("No es posible que el avion llegue a su destino antes de partir");
            }
            if(vueloCandidato.getMinutosDeVueloEstimados() <= 0) {
                validos = false;
                throw new VueloException("El tiempo de viaje estimado no puede ser menor o igual a cero");
            }
            Long minutosMinimos = Duration.between(fechaSalida, fechaLlegada).toMinutes();
            if(vueloCandidato.getMinutosDeVueloEstimados() < minutosMinimos) {
                validos = false;
                throw new VueloException(
                        "La cantidad de minutos estimados es menor al tiempo minimo de viaje: "
                        +minutosMinimos+ ". Por favor verifique los datos");
            }
        } catch (VueloException vex) {
            UtilGeneral.mostrarAlerta("Error", vex.getMessage(), Alert.AlertType.ERROR);
        } catch (DateTimeParseException dtpex) {
            validos = false;
            UtilGeneral.mostrarAlerta(
                    "Error",
                    "La fecha de salida/llegada no tiene el formato esperado: yyyy-MM-dd HH:mm:ss",
                    Alert.AlertType.WARNING);
        }
        return validos;
    }

    //Depende de una validacion previa de los campos
    private Vuelo construirVueloCandidato() {
        Vuelo vueloCandidato = new Vuelo();
        vueloCandidato.setCodigoVuelo(tfCodigoVuelo.getText());
        vueloCandidato.setAerolinea(comboAerolinea.getSelectionModel().getSelectedItem().toString());
        vueloCandidato.setCodigoAvion(comboAvion.getSelectionModel().getSelectedItem().toString());
        vueloCandidato.setNumPasajeros(vueloEdicion == null ? 0 : vueloEdicion.getNumPasajeros());
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
        vueloCandidato.setPiloto(tripulacion.getPiloto());
        vueloCandidato.setCopiloto(tripulacion.getCopiloto());
        vueloCandidato.setAsistentes(tripulacion.getAsistentes());
        return vueloCandidato;
    }

    private String obtenerNombreAerolinea() {
        if(comboAerolinea.getSelectionModel().getSelectedItem() != null) {
            return comboAerolinea.getSelectionModel().getSelectedItem().toString();
        } else {
            return null;
        }
    }

    public void instanciarTripulacion() {
        this.tripulacion = new Tripulacion();
    }

    public class Tripulacion {
        private Piloto piloto;
        private Piloto copiloto;
        List<AsistenteVuelo> asistentes;

        public Tripulacion() {

        }

        public Piloto getPiloto() {
            return piloto;
        }

        public void setPiloto(Piloto piloto) {
            this.piloto = piloto;
        }

        public Piloto getCopiloto() {
            return copiloto;
        }

        public void setCopiloto(Piloto copiloto) {
            this.copiloto = copiloto;
        }

        public List<AsistenteVuelo> getAsistentes() {
            return asistentes;
        }

        public void setAsistentes(List<AsistenteVuelo> asistentes) {
            this.asistentes = asistentes;
        }
    }

    public Tripulacion getTripulacion() {
        return tripulacion;
    }
}