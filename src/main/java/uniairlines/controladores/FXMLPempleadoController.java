/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import uniairlines.modelo.Usuario;

/**
 *
 * @author cuent
 */
public class FXMLPempleadoController implements Initializable {

    @FXML
    private AnchorPane Aerolinea;
    @FXML
    private Label lbAerolinea;
    
    private static String seleccionada;
    
     private static Label lbAerolineaStatic;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     lbAerolineaStatic = lbAerolinea;
    }    
    
       public static void inicializarDatos(Usuario usuarioAutenticado) {
        if (lbAerolineaStatic != null) {
            lbAerolineaStatic.setText(usuarioAutenticado.getAerolinea());
            seleccionada = usuarioAutenticado.getAerolinea();
        }
    }

    @FXML
    private void lbAerolinea(MouseEvent event) {
    }
 
}
