/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    public void gestionarClientes(ActionEvent actionEvent) {
        irTablaClientes();
    }

    private void irTablaClientes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLTablaClientes.fxml"));
            Parent vista = loader.load();


            FXMLTablaClientesController controlador = loader.getController();


            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(vista);

            escenarioAdmin.setScene(escena);
            escenarioAdmin.setTitle("ADMINISTRACIÓN DE Clientes  ");
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.centerOnScreen();
            escenarioAdmin.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void gestionarVentasBoletos(ActionEvent actionEvent) {
    }
}
