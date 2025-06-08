package uniairlines.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import uniairlines.dao.AerolineaDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Contacto;

public class FXMLFormularioAerolineaController {

    @FXML private TextField tfIATA;
    @FXML private TextField tfICAO;
    @FXML private TextField tfNombre;
    @FXML private TextField tfCallsign;
    @FXML private TextField tfNacionalidad;
    @FXML private TextField tfDireccion;
    @FXML private TextField tfSitioOficial;
    @FXML private TextField tfContactoNombre;
    @FXML private TextField tfContactoTelefono;

    private Aerolinea aerolineaActual;  // Para modificar o guardar datos
    private boolean esEdicion = false ;

    // Nueva variable para saber si se guardó o no
    private boolean guardado = false;

    void setAerolinea() { 
    this.aerolineaActual = new Aerolinea();

    }

    public void setAerolinea(Aerolinea a) {
         tfIATA.setDisable(true);
        this.aerolineaActual = a;
        System.out.print(a);
            esEdicion = true;
            tfIATA.setText(a.getIATA());
            tfICAO.setText(a.getICAO());
            tfNombre.setText(a.getNombre());
            tfCallsign.setText(a.getCallsign());
            tfNacionalidad.setText(a.getNacionalidad());
            tfDireccion.setText(a.getDireccion());
            tfSitioOficial.setText(a.getSitioOficial());
            tfContactoNombre.setText(a.getContacto().getNombre());
            tfContactoTelefono.setText(a.getContacto().getTelefono());
            
        }
    

    // Handler para el botón Guardar
    @FXML
    private void guardarAerolinea() throws ArchivoException {
         AerolineaDAO A = new AerolineaDAO();
        if(!esEdicion){
 
        if (tfIATA.getText().isEmpty() || tfNombre.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "Por favor, rellena los campos IATA y Nombre.");
            return;
        }
        
        
        
        Aerolinea nueva = crearaerolineatf();
        A.guardar(nueva);
        }
        
        if(esEdicion){
            
        Aerolinea aerolineaeditar = crearaerolineatf();
        A.actualizar(aerolineaeditar); 
        }

        mostrarAlerta("Éxito", "Aerolinea " + (esEdicion ? "modificada" : "agregada") + " correctamente.");
        guardado = true;
        Stage stage = (Stage) tfIATA.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Aerolinea getAerolinea() {
        return aerolineaActual;
    }

    // Método que indica si se guardó o no
    public boolean isGuardado() {
        return guardado;
    }
    
    public Aerolinea crearaerolineatf(){
        
        aerolineaActual = new Aerolinea();
        Contacto c = new Contacto();

        aerolineaActual.setIATA(tfIATA.getText().trim());
        aerolineaActual.setICAO(tfICAO.getText().trim());
        aerolineaActual.setNombre(tfNombre.getText().trim());
        aerolineaActual.setCallsign(tfCallsign.getText().trim());
        aerolineaActual.setNacionalidad(tfNacionalidad.getText().trim());
        aerolineaActual.setDireccion(tfDireccion.getText().trim());
        aerolineaActual.setSitioOficial(tfSitioOficial.getText().trim());

        c.setNombre(tfContactoNombre.getText().trim());
        c.setTelefono(tfContactoTelefono.getText().trim());

        aerolineaActual.setContacto(c);
        
        return aerolineaActual;
    }

    
}

