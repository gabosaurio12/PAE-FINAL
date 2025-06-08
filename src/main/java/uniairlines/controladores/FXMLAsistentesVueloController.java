package uniairlines.controladores;

import java.io.File;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniairlines.dao.AsistenteVueloDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.util.*;

public class FXMLAsistentesVueloController {
    private UtilGeneral util = new UtilGeneral();
    private String aerolineaSeleccionada;
    
    @FXML private TableView<AsistenteVuelo> tablaAsistentesVuelo;
    @FXML private TableColumn<AsistenteVuelo, String> colID;
    @FXML private TableColumn<AsistenteVuelo, String> colNombre;
    @FXML private TableColumn<AsistenteVuelo, String> colSalario;
    @FXML private TableColumn<AsistenteVuelo, String> colGenero;
    @FXML private TableColumn<AsistenteVuelo, String> colIdiomas;
    @FXML private TableColumn<AsistenteVuelo, String> colHorasAsistidas;
    @FXML private TableColumn<AsistenteVuelo, String> colDireccion;
    @FXML private TableColumn<AsistenteVuelo, String> colTelefono;
    @FXML private TableColumn<AsistenteVuelo, String> colCorreo;
    @FXML private TableColumn<AsistenteVuelo, String> colFechaNacimiento;
    
    public void initComponentes(String aerolineaSeleccionada) {
        this.aerolineaSeleccionada = aerolineaSeleccionada;
        inicializarTabla();
    }
    
    public void cancelar() {
        Stage stage = (Stage) tablaAsistentesVuelo.getScene().getWindow();
        stage.close();
        util.abrirFXML("/vista/FXMLPadmin.fxml"
                , "Página Principal " + aerolineaSeleccionada,
                FXMLPadminController.class);
        FXMLPadminController.inicializarDatos(aerolineaSeleccionada);
    }
    
    public void inicializarTabla() {
        colID.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colIdiomas.setCellValueFactory(new PropertyValueFactory<>("numIdiomas"));
        colHorasAsistidas.setCellValueFactory(new PropertyValueFactory<>("numHorasAsistidas"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        
        List<AsistenteVuelo> asistentes = null;
        try {
            AsistenteVueloDAO daoAsistentes = new AsistenteVueloDAO();
            asistentes = daoAsistentes.getTodosLosAsistentesVuelo(aerolineaSeleccionada);
        } catch (ArchivoException e) {
            UtilGeneral.createAlert("Error", "Hubo un error al cargar los asistentes de vuelo");
            System.err.println("Error al cargar asistentes de vuelo " + e.getMessage());
        }
        
        tablaAsistentesVuelo.getItems().setAll(asistentes);
    }
    
    public void registrarAsistenteVuelo() {
        ResultadoFXML<FXMLRegistrarAsistenteVueloController> resultado = util.abrirFXMLModal(
                "/vista/FXMLRegistrarAsistenteVuelo.fxml", "Asistente Vuelo",
                FXMLRegistrarAsistenteVueloController.class,
                tablaAsistentesVuelo.getScene().getWindow());
        
        if (resultado != null) {
            FXMLRegistrarAsistenteVueloController controlador = resultado.getControlador();
            Stage stage = resultado.getStage();
            
            controlador.init(aerolineaSeleccionada);
            stage.showAndWait();
            initComponentes(aerolineaSeleccionada);
        }
    }
    
    public void editarAsistenteVuelo() {
        AsistenteVuelo asistente = tablaAsistentesVuelo.getSelectionModel().getSelectedItem();
        if (asistente != null) {
            ResultadoFXML<FXMLEditarAsistenteVueloController> resultado = util.abrirFXMLModal(
                "/uniairlines/vista/FXMLEditarAsistenteVuelo.fxml", "Editar Piloto",
                FXMLEditarAsistenteVueloController.class,
                tablaAsistentesVuelo.getScene().getWindow());
        
            if (resultado != null) {
                FXMLEditarAsistenteVueloController controlador = resultado.getControlador();
                Stage stage = resultado.getStage();

                controlador.init(aerolineaSeleccionada, asistente);
                stage.showAndWait();
                initComponentes(aerolineaSeleccionada);
            }
        } else {
            UtilGeneral.createAlert("Alerta!", "Debes elegir un asistente de vuelo antes de editarlo");
        }
        
    }
    
    
    public void eliminarAsistenteVuelo() {
        AsistenteVuelo asistente = tablaAsistentesVuelo.getSelectionModel().getSelectedItem();
        if (asistente != null) {
            AsistenteVueloDAO daoAsistentes = new AsistenteVueloDAO();
            try {
                daoAsistentes.eliminarAsistenteVuelo(asistente, aerolineaSeleccionada);
                UtilGeneral.createAlert("Eliminación", "Se eliminó el asistente de vuelo correctamente!");
            } catch (ArchivoException ex) {
                UtilGeneral.createAlert("Error", "Error al eliminar asistente de vuelo");
                System.err.println("Error al eliminar piloto: " + ex.getMessage());
            }
            initComponentes(aerolineaSeleccionada);
        } else {
            UtilGeneral.createAlert("Alerta!", "Debes elegir un piloto antes de editarlo");
        }
    }

    public void exportarPDF() {
        try {
            List<AsistenteVuelo> asistentes = new AsistenteVueloDAO().getTodosLosAsistentesVuelo(aerolineaSeleccionada);
            PDFUtil pdfUtil = new PDFUtil();
            String path = "Documentos/" + aerolineaSeleccionada + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = path.concat(aerolineaSeleccionada).concat("_Asistentes_Vuelo.pdf");
            pdfUtil.generarPDFAsistentesVuelo(path, asistentes);
        } catch (ArchivoException e) {
            System.err.println("Error al buscar asistentes de vuelo: " + e.getMessage());
        }
    }

    public void exportarCSV() {
        try {
            List<AsistenteVuelo> asistentes = new AsistenteVueloDAO().getTodosLosAsistentesVuelo(aerolineaSeleccionada);
            CSVUtil csvUtil = new CSVUtil();
            String path = "Documentos/" + aerolineaSeleccionada + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = path.concat(aerolineaSeleccionada).concat("_Asistentes_Vuelo.csv");
            csvUtil.generarCSVAsistentesVuelo(path, asistentes);
        } catch (ArchivoException e) {
            System.err.println("Error al buscar asistentes de vuelo: " + e.getMessage());

        }
    }

    public void exportarXLSX() {
        try {
            List<AsistenteVuelo> asistentes = new AsistenteVueloDAO().getTodosLosAsistentesVuelo(aerolineaSeleccionada);
            XLSXUtil xlsxUtil = new XLSXUtil();
            String path = "Documentos/" + aerolineaSeleccionada + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = path.concat(aerolineaSeleccionada).concat("_Asistentes_Vuelo.xlsx");
            xlsxUtil.generarXLSXAsistentesVuelo(path, asistentes);
        } catch (ArchivoException e) {
            System.err.println("Error al buscar asistentes de vuelo: " + e.getMessage());

        }
    }
}
