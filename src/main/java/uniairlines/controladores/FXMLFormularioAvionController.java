/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uniairlines.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import uniairlines.dao.AvionDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Asiento;
import uniairlines.modelo.Avion;
import uniairlines.util.UtilGeneral;

/**
 * FXML Controller class
 *
 * @author cuent
 */
public class FXMLFormularioAvionController implements Initializable {

    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfFilas;
    @FXML
    private TextField tfPeso;
    @FXML
    private ComboBox<String> cbTipo;
   @FXML
   private ComboBox<String> cbEstado;
    @FXML
    private TextField tfCapacidadTtotal;
    @FXML
    private TextField tfAsientosPorFila;
    
    private String NombreAerolinea;
    
    private boolean  esEdicion;
    
    Avion avion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    
    }    
    
       void setAerolinea(String aerolineaSeleccionada) {
       this.NombreAerolinea = aerolineaSeleccionada;
    }
    


    @FXML
    private void btnGuardar(ActionEvent event) throws ArchivoException {
    if(!esEdicion){
    Avion avion = crearAvionTf();
    guardar(avion);
    }if(esEdicion){
    modificar(avion);
    }
    }
    
    private Avion crearAvionTf(){
    Avion avionn = new Avion();
    List<Asiento> Asiento = new ArrayList<>();
    Asiento asiento = new Asiento("prueba");
    Asiento.add(asiento);
    
     // Obtener los valores desde los campos
    String modelo = tfModelo.getText();
    String tipo = (String) cbTipo.getValue();
    String estado = (String) cbEstado.getValue();
    int filas = Integer.parseInt(tfFilas.getText());
    int asientosPorFila = Integer.parseInt(tfAsientosPorFila.getText());
    int peso = Integer.parseInt(tfPeso.getText());
    int capacidadTotal = filas * asientosPorFila;
    //modelo,capacidadTotal,filas,asientosPorFila,peso,tipo,asientos,estado;
            
    avionn.setModelo(modelo);
    avionn.setCapacidadTotal(capacidadTotal);
    avionn.setFilas(filas);
    avionn.setAsientosPorFila(asientosPorFila);
    avionn.setPeso(peso);
    avionn.setTipo(tipo);
    avionn.setEstado("Mantenimiento");
    avionn.setAsientos(Asiento);
     
    return avionn; 
    }
    
    private boolean guardar(Avion avion){
        AvionDAO a = new AvionDAO();
        
        try {
            
            a.agregar(avion, NombreAerolinea);
            UtilGeneral.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Exito", "Avion registrado en modo mantenimiento");
        } catch (ArchivoException ex) {
            
        }
        return true;
    }    

    void setAvion(Avion avion, boolean esEdicion) {
     this.esEdicion= esEdicion;
     this.avion =avion;
    if(!esEdicion){
        cbEstado.setDisable(true); 
        tfCapacidadTtotal.setDisable(true); 
        tfAsientosPorFila.setText("4");
        tfAsientosPorFila.setDisable(true);
        cbEstado.setValue("En mantenimiento");
    }else{
        tfCapacidadTtotal.setDisable(true); 
        tfAsientosPorFila.setText("4");
        tfAsientosPorFila.setDisable(true);
        cargarAvion(avion);
      
    }
    
    
    }

    private void cargarAvion(Avion avion){
    
     tfModelo.setText(avion.getModelo());
     tfFilas.setText(Integer.toString(avion.getFilas()));
     tfPeso.setText(Integer.toString((int) avion.getPeso()));
     cbEstado.setValue(avion.getEstado());
     cbTipo.setValue(avion.getTipo());
     
     tfCapacidadTtotal.setText(Integer.toString(avion.getCapacidadTotal()));
     tfAsientosPorFila.setText(Integer.toString(avion.getAsientosPorFila()));
   
    }

    private void modificar(Avion avion) throws ArchivoException {
    Avion aviona = crearAvionTf();
    String id = this.avion.getId();
    aviona.setId(id);
    
    AvionDAO a = new AvionDAO();
    
    a.modificar(aviona, NombreAerolinea);
    UtilGeneral.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Exito", "Avion modificado");
    }
}
 
