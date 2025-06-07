package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniairlines.dao.PilotoDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.empleados.LicenciaAeronautica;
import uniairlines.modelo.pojo.empleados.Piloto;
import uniairlines.util.utilgeneral;

public class FXMLRegistrarPilotoController {
    
    private final utilgeneral util = new utilgeneral();
    private String aerolineaSeleccionada;
    
    @FXML private TextField idTF;
    @FXML private TextField nombreTF;
    @FXML private TextField direccionTF;
    @FXML private TextField telefonoTF;
    @FXML private TextField correoTF;
    @FXML private TextField fechaNacimientoTF;
    @FXML private ChoiceBox<String> generoCB;
    @FXML private TextField salarioTF;
    @FXML private ChoiceBox<LicenciaAeronautica> tipoLicenciaCB;
    @FXML private TextField fechaCertificacionTF;
    @FXML private TextField horasVueloTF;
    
    public void init(String aerolineaSeleccionada) {
        this.aerolineaSeleccionada = aerolineaSeleccionada;
        inicializarChoiceBox();
    }
    
    public void inicializarChoiceBox() {
        ObservableList<String> generos = FXCollections.observableArrayList(
        "FEMENINO", "MASCULINO", "NO BINARIO","OTRO");
        generoCB.setItems(generos);
        tipoLicenciaCB.setItems(FXCollections.observableArrayList(
        LicenciaAeronautica.values()));
    }
    
    public boolean getTextFields(Piloto piloto) {
        piloto.setIdentificador(idTF.getText());
        piloto.setNombre(nombreTF.getText());
        piloto.setDireccion(direccionTF.getText());
        piloto.setTelefono(telefonoTF.getText());
        piloto.setCorreo(correoTF.getText());
        piloto.setFechaNacimiento(fechaNacimientoTF.getText());
        piloto.setGenero(generoCB.getValue());
        piloto.setTipoLicencia(String.valueOf(tipoLicenciaCB.getValue()));
        piloto.setFechaCertificacion(fechaCertificacionTF.getText());
        try {
            piloto.setSalario(Double.valueOf(salarioTF.getText()));
            piloto.setNumHorasVuelo(Integer.valueOf(horasVueloTF.getText()));
        } catch(NumberFormatException ex) {
            System.err.println("Error en campos numéricos" + ex.getMessage());
            util.createAlert("Campos Inválidos", "Los campos de Número de idiomas y Salario deben ser numéricos");
            return false;
        }
        
        return piloto.validarDatos();
    }
    
    public void guardarPiloto() {
        Piloto piloto = new Piloto();
        if (getTextFields(piloto)) {
            PilotoDAO dao = new PilotoDAO();
            try {
                dao.guardarPiloto(piloto, aerolineaSeleccionada);
                util.createAlert("Registro", "Se guardó el piloto con éxito!");
            } catch (ArchivoException ex) {
                util.createAlert("Error", "No se guardó el piloto por un error en el JSON");
                System.err.println("Error al guardar piloto" + ex.getMessage());
            }
            Stage stage = (Stage) idTF.getScene().getWindow();
            stage.close();
        } else {
            util.createAlert("Campos inválidos", "Hay campos inválidos, por favor corrígalos");
        }
    }
}
