package uniairlines.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.util.CSVUtil;
import uniairlines.util.PDFUtil;
import uniairlines.util.UtilGeneral;
import uniairlines.util.XLSXUtil;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLImprimirVuelosController implements Initializable {
    @FXML
    private Label lbTitulo;

    private final File DIRECTORIO_IMPRESION = new File("Exportaciones");

    private List<Vuelo> vuelos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cargarDatos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public void clicRegresar(ActionEvent actionEvent) {
        Stage stage = (Stage) lbTitulo.getScene().getWindow();
        stage.close();
    }

    public void clicCSV(ActionEvent actionEvent) {
        if(!vuelos.isEmpty()) {
            if(!DIRECTORIO_IMPRESION.exists()) {
                DIRECTORIO_IMPRESION.mkdir();
            }
            String nombreArchivo = String.format("%s.csv", generarNombreArchivo());
            new CSVUtil().generarCSVVuelos(nombreArchivo, vuelos);
        } else {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    "No se puede realizar la impresión porque no hay vuelos registrados",
                    Alert.AlertType.INFORMATION);
        }
    }

    public void clicExcel(ActionEvent actionEvent) {
        if(!vuelos.isEmpty()) {
            if(!DIRECTORIO_IMPRESION.exists()) {
                DIRECTORIO_IMPRESION.mkdir();
            }
            String nombreArchivo = String.format("%s.xlsx", generarNombreArchivo());
            new XLSXUtil().generarXLSXVuelos(nombreArchivo, vuelos);
        } else {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    "No se puede realizar la impresión porque no hay vuelos registrados",
                    Alert.AlertType.INFORMATION);
        }
    }

    public void clicPDF(ActionEvent actionEvent) {
        if(!vuelos.isEmpty()) {
            if(!DIRECTORIO_IMPRESION.exists()) {
                DIRECTORIO_IMPRESION.mkdir();
            }
            String nombreArchivo = String.format("%s.pdf", generarNombreArchivo());
            new PDFUtil().generarPDFVuelos(nombreArchivo, vuelos);
        } else {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    "No se puede realizar la impresión porque no hay vuelos registrados",
                    Alert.AlertType.INFORMATION);
        }
    }

    private String generarNombreArchivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String fechaActual = LocalDateTime.now().format(formatter);
        return String.format("%s/%s %s", DIRECTORIO_IMPRESION.getPath(), "vuelos", fechaActual);
    }
}
