package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniairlines.dao.AsistenteVueloDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.util.UtilGeneral;

public class FXMLRegistrarAsistenteVueloController {
    private String aerolineaSeleccionada;
    
    @FXML private TextField idTF;
    @FXML private TextField nombreTF;
    @FXML private TextField direccionTF;
    @FXML private TextField telefonoTF;
    @FXML private TextField correoTF;
    @FXML private TextField fechaNacimientoTF;
    @FXML private ChoiceBox<String> generoCB;
    @FXML private TextField salarioTF;
    @FXML private TextField idiomasTF;
    @FXML private TextField horasVueloAsistidasTF;
    
    public void init(String aerolineaSeleccionada) {
        this.aerolineaSeleccionada = aerolineaSeleccionada;
        inicializarChoiceBox();
    }
    
    public void inicializarChoiceBox() {
        ObservableList<String> generos = FXCollections.observableArrayList(
        "FEMENINO", "MASCULINO", "NO BINARIO","OTRO");
        generoCB.setItems(generos);
    }
    
    public boolean getTextFields(AsistenteVuelo asistente) {
        asistente.setIdentificador(idTF.getText());
        asistente.setNombre(nombreTF.getText());
        asistente.setDireccion(direccionTF.getText());
        asistente.setTelefono(telefonoTF.getText());
        asistente.setCorreo(correoTF.getText());
        asistente.setFechaNacimiento(fechaNacimientoTF.getText());
        asistente.setGenero(generoCB.getValue());
        try {
            asistente.setSalario(Double.valueOf(salarioTF.getText()));
            asistente.setNumIdiomas(Integer.valueOf(idiomasTF.getText()));
        } catch(NumberFormatException ex) {
            System.err.println("Error en campos numéricos" + ex.getMessage());
            UtilGeneral.createAlert("Campos Inválidos", "Los campos de Número de idiomas y Salario deben ser numéricos");
            return false;
        }
        asistente.setNumHorasAsistidas(Integer.valueOf(horasVueloAsistidasTF.getText()));
        
        return asistente.validarDatos();
    }
    
    public void guardarAsistenteVuelo() {
        AsistenteVuelo asistente = new AsistenteVuelo();
        if (getTextFields(asistente)) {
            AsistenteVueloDAO dao = new AsistenteVueloDAO();
            try {
                dao.guardarAsistenteVuelo(asistente, aerolineaSeleccionada);
                UtilGeneral.createAlert("Registro", "Se guardó el asistente de vuelo con éxito!");
            } catch (ArchivoException ex) {
                UtilGeneral.createAlert("Error", "No se guardó el asistente de vuelo por un error en el JSON");
                System.err.println("Error al guardar asistente" + ex.getMessage());
            }
            Stage stage = (Stage) idTF.getScene().getWindow();
            stage.close();
        } else {
            UtilGeneral.createAlert("Campos inválidos", "Hay campos inválidos, por favor corrígalos");
        }
    }
}
