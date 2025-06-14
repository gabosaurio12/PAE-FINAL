package uniairlines.controladores;

import java.io.File;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniairlines.dao.PilotoDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.util.*;
import uniairlines.modelo.pojo.empleados.Piloto;

public class FXMLPilotosController {
    private final UtilGeneral util = new UtilGeneral();
    private String aerolineaSeleccionada;
    
    @FXML private TableView<Piloto> tablaPilotos;
    @FXML private TableColumn<Piloto, String> colID;
    @FXML private TableColumn<Piloto, String> colNombre;
    @FXML private TableColumn<Piloto, String> colSalario;
    @FXML private TableColumn<Piloto, String> colGenero;
    @FXML private TableColumn<Piloto, String> colLicencia;
    @FXML private TableColumn<Piloto, String> colHorasVuelo;
    @FXML private TableColumn<Piloto, String> colFechaCertificacion;
    @FXML private TableColumn<Piloto, String> colDireccion;
    @FXML private TableColumn<Piloto, String> colTelefono;
    @FXML private TableColumn<Piloto, String> colCorreo;
    @FXML private TableColumn<Piloto, String> colFechaNacimiento;
    
    public void initComponentes(String aerolineaSeleccionada) {
        this.aerolineaSeleccionada = aerolineaSeleccionada;
        inicializarTabla();
    }
    
    public void cancelar() {
        Stage stage = (Stage) tablaPilotos.getScene().getWindow();
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
        colLicencia.setCellValueFactory(new PropertyValueFactory<>("tipoLicencia"));
        colHorasVuelo.setCellValueFactory(new PropertyValueFactory<>("numHorasVuelo"));
        colFechaCertificacion.setCellValueFactory(new PropertyValueFactory<>("fechaCertificacion"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        
        refrezcarTabla();
    }

    public void refrezcarTabla() {
        List<Piloto> pilotos = null;
        try {
            PilotoDAO daoPilotos = new PilotoDAO();
            pilotos = daoPilotos.getTodosLosPilotos(aerolineaSeleccionada);
        } catch (ArchivoException e) {
            System.err.println("Error al cargar pilotos: " + e.getMessage());
        }
        tablaPilotos.getItems().setAll(pilotos);
    }
    
    public void registrarPiloto() {
        ResultadoFXML<FXMLRegistrarPilotoController> resultado = util.abrirFXMLModal(
                "/vista/FXMLRegistrarPiloto.fxml", "Pilotos",
                FXMLRegistrarPilotoController.class,
                tablaPilotos.getScene().getWindow());
        
        if (resultado != null) {
            FXMLRegistrarPilotoController controlador = resultado.getControlador();
            Stage stage = resultado.getStage();
            
            controlador.init(aerolineaSeleccionada);
            stage.showAndWait();
            refrezcarTabla();
        }
    }
    
    public void editarPiloto() {
        Piloto piloto = tablaPilotos.getSelectionModel().getSelectedItem();
        if (piloto != null) {
            ResultadoFXML<FXMLEditarPilotoController> resultado = util.abrirFXMLModal(
                "/vista/FXMLEditarPiloto.fxml",
                "Editar Piloto", FXMLEditarPilotoController.class,
                tablaPilotos.getScene().getWindow());
        
            if (resultado != null) {
                FXMLEditarPilotoController controlador = resultado.getControlador();
                Stage stage = resultado.getStage();

                controlador.init(aerolineaSeleccionada, piloto);
                stage.showAndWait();
                refrezcarTabla();
            }
        } else {
            UtilGeneral.createAlert("Alerta!", "Debes elegir un piloto antes de editarlo");
        }
        
    }
    
    
    public void eliminarPiloto() {
        Piloto piloto = tablaPilotos.getSelectionModel().getSelectedItem();
        if (piloto != null) {
            PilotoDAO dao = new PilotoDAO();
            try {
                dao.eliminarPiloto(piloto, aerolineaSeleccionada);
                UtilGeneral.createAlert("Eliminación", "Se eliminó el piloto correctamente!");
            } catch (ArchivoException ex) {
                UtilGeneral.createAlert("Error", "Error al eliminar piloto");
                System.err.println("Error al eliminar piloto: " + ex.getMessage());
            }
            refrezcarTabla();
        } else {
            UtilGeneral.createAlert("Alerta!", "Debes elegir un piloto antes de editarlo");
        }
    }

    public void exportarPDF() {
        try {
            List<Piloto> pilotos = new PilotoDAO().getTodosLosPilotos(aerolineaSeleccionada);
            PDFUtil pdfUtil = new PDFUtil();
            String path = "Documentos/" + aerolineaSeleccionada + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = path.concat(aerolineaSeleccionada).concat("_Pilotos.pdf");
            pdfUtil.generarPDFPilotos(path, pilotos);
        } catch (ArchivoException e) {
            System.err.println("Error al buscar pilotos: " + e.getMessage());
        }
    }

    public void exportarCSV() {
        try {
            List<Piloto> pilotos = new PilotoDAO().getTodosLosPilotos(aerolineaSeleccionada);
            CSVUtil csvUtil = new CSVUtil();
            String path = "Documentos/" + aerolineaSeleccionada + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = path.concat(aerolineaSeleccionada).concat("_Pilotos.csv");
            csvUtil.generarCSVPilotos(path, pilotos);
        } catch (ArchivoException e) {
            System.err.println("Error al buscar pilotos: " + e.getMessage());
        }
    }

    public void exportarXLSX() {
        try {
            List<Piloto> pilotos = new PilotoDAO().getTodosLosPilotos(aerolineaSeleccionada);
            XLSXUtil xlsxUtil = new XLSXUtil();
            String path = "Documentos/" + aerolineaSeleccionada + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = path.concat(aerolineaSeleccionada).concat("_Pilotos.xlsx");
            xlsxUtil.generarXLSXPilotos(path, pilotos);
        } catch (ArchivoException e) {
            System.err.println("Error al buscar pilotos: " + e.getMessage());
        }
    }
}
