package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uniairlines.dao.AvionDAO;
import uniairlines.dao.BoletoDAO;
import uniairlines.dao.VueloDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.aerolinea.Aeropuerto;
import uniairlines.modelo.pojo.boleto.Boleto;
import uniairlines.util.CSVUtil;
import uniairlines.util.PDFUtil;
import uniairlines.util.UtilGeneral;
import uniairlines.util.XLSXUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        Vuelo vueloSeleccionado = tablaVuelos.getSelectionModel().getSelectedItem();

        if (vueloSeleccionado == null) {
            util.mostrarAlerta("Sin selección", "Selecciona un vuelo para exportar sus boletos", Alert.AlertType.WARNING);
            return;
        }

        try {
            BoletoDAO boletoDAO = new BoletoDAO();
            List<Boleto> boletos = boletoDAO.filtrarPorVuelo(vueloSeleccionado.getCodigoVuelo());

            if (boletos.isEmpty()) {
                util.mostrarAlerta("Sin datos", "No hay boletos registrados para este vuelo", Alert.AlertType.INFORMATION);
                return;
            }

            String ruta = "Documentos/boletos" + vueloSeleccionado.getCodigoVuelo() + ".csv";
            new CSVUtil().generarCSVBoletos(ruta, boletos);

        } catch (ArchivoException e) {
            util.mostrarAlerta("Error", "No se pudieron recuperar los boletos:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void exportarXLSX(ActionEvent actionEvent) {

    }

    public void exportarPDF(ActionEvent actionEvent) {
        Vuelo vueloSeleccionado = tablaVuelos.getSelectionModel().getSelectedItem();

        if (vueloSeleccionado == null) {
            util.mostrarAlerta("Sin selección", "Selecciona un vuelo para exportar sus boletos", Alert.AlertType.WARNING);
            return;
        }

        try {
            BoletoDAO boletoDAO = new BoletoDAO();
            List<Boleto> boletos = boletoDAO.filtrarPorVuelo(vueloSeleccionado.getCodigoVuelo());

            if (boletos.isEmpty()) {
                util.mostrarAlerta("Sin datos", "No hay boletos registrados para este vuelo", Alert.AlertType.INFORMATION);
                return;
            }

            File directorio = new File("Exportaciones");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String ruta = "Documentos/boletos" + vueloSeleccionado.getCodigoVuelo() + ".pdf";
            new PDFUtil().generarPDFBoletos(ruta, boletos);

        } catch (ArchivoException e) {
            util.mostrarAlerta("Error", "No se pudieron recuperar los boletos:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void clicVenderBoleto(ActionEvent actionEvent) {
        Vuelo vueloSeleccionado = tablaVuelos.getSelectionModel().getSelectedItem();

        if (vueloSeleccionado == null) {
            util.mostrarAlerta("Sin selección", "Por favor selecciona un vuelo para continuar con la venta.", Alert.AlertType.WARNING);
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLTablaSeleccionarCliente.fxml"));
            Parent root = loader.load();
            FXMLTablaSeleccionarClienteController controller = loader.getController();
            controller.setVueloSeleccionado(vueloSeleccionado);
            Stage stage = new Stage();
            stage.setTitle("Seleccionar Cliente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException  e) {
            util.mostrarAlerta("Error al abrir la ventana", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    public void clicRegresar(ActionEvent actionEvent) {
        // TODO
    }
    private List<Boleto> obtenerBoletosDeVuelo(Vuelo vuelo) throws ArchivoException {
        BoletoDAO boletoDAO = new BoletoDAO(); // Asegúrate de tener esta clase
        List<Boleto> todos = boletoDAO.recuperarBoletos(); // Esto debe cargar del JSON
        List<Boleto> filtrados = new ArrayList<>();
        for (Boleto b : todos) {
            if (b.getVuelo().getCodigoVuelo().equals(vuelo.getCodigoVuelo())) {
                filtrados.add(b);
            }
        }
        return filtrados;
    }
}
