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
import uniairlines.UNIAIRLINES;
import uniairlines.modelo.Usuario;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.util.ResultadoFXML;
import uniairlines.util.utilgeneral;

/**
 *
 * @author cuent
 */
public class FXMLPempleadoController implements Initializable {
    private final String RUTA_FXML_REGISTRAR_CLIENTE = "resources/vista/FXMLRegistrarClientes.fxml";
    private final String RUTA_FXML_CONSULTAR_CLIENTES = "resources/vista/FXMLConsultarClientes.fxml";

    private final utilgeneral util = new utilgeneral();

    @FXML
    private AnchorPane Aerolinea;
    @FXML
    private Label lbAerolinea;
    @FXML
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

    @FXML
    public void buttonConsultarLista(ActionEvent actionEvent) {
        utilgeneral.crearEscenarioSimple(RUTA_FXML_CONSULTAR_CLIENTES, "Consultar Clientes");
    }

    @FXML
    public void buttonRegistrarCliente(ActionEvent actionEvent) {
        utilgeneral.crearEscenarioSimple(RUTA_FXML_REGISTRAR_CLIENTE, "Registrar Cliente");
    }
}
