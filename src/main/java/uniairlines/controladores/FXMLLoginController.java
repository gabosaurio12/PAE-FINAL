/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniairlines.UNIAIRLINES;
import uniairlines.dao.UsuarioDAO;
import uniairlines.modelo.Usuario;
import uniairlines.util.UtilGeneral;

/**
 * FXML Controller class
 *
 * @author cuent
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfContraseña;

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
    }    

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
    
        String usuario = tfUsuario.getText();
        String contraseña = tfContraseña.getText();

        if(validarCampos(usuario,contraseña)){
            validarCredenciales(usuario,contraseña);
        }
    }
    
    private boolean validarCampos(String usuario,String contraseña){
     
        boolean camposValidos = true;

        if(usuario.isEmpty()){
            camposValidos = false;
        }

        if(contraseña.isEmpty()){
            camposValidos = false;
        }
        return camposValidos;
    }
    
    private void validarCredenciales(String usuario, String contraseña) {
        Usuario usuarioAutenticado = UsuarioDAO.verificarUsuario(usuario, contraseña);

        if (usuarioAutenticado != null) {
            UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING,"Credenciales correctas", "Bienvenido " + usuarioAutenticado.getNombre());
            irPantallaPrincipal(usuarioAutenticado);
            
        } else {
            UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING,"Credenciales incorrectas", "Usuario y/o contraseña incorrectos. Por favor, verifique las credenciales.");
        }
    }
     
    private void irPantallaPrincipal(Usuario usuarioAutenticado) {
        String tipo = (usuarioAutenticado.getTipo().toUpperCase());

        switch (tipo){
            case "MASTER":
                    try {
                    Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
                    FXMLLoader cargador = new FXMLLoader(getClass().getResource("/vista/FXMLPmaster.fxml"));
                    Parent vista = cargador.load();

                  // FXMLPmasterController.inicializarDatos(usuarioAutenticado);

                    Scene escenaPrincipal = new Scene(vista);
                    escenarioBase.setScene(escenaPrincipal);
                    escenarioBase.setTitle("PANTALLA PRINCIPAL MASTER: " + usuarioAutenticado.getNombre());
                    escenarioBase.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case "ADMIN":    
                try {
                    Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
                    FXMLLoader cargador = new FXMLLoader(getClass().getResource("/vista/FXMLPadmin.fxml"));
                    Parent vista = cargador.load();

                    FXMLPadminController.inicializarDatos(usuarioAutenticado);

                    Scene escenaPrincipal = new Scene(vista);
                    escenarioBase.setScene(escenaPrincipal);
                    escenarioBase.setTitle("PANTALLA PRINCIPAL Admin: " + usuarioAutenticado.getNombre());
                    escenarioBase.show();                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "EMPLEADO":
                try {
                    Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
                    FXMLLoader cargador = new FXMLLoader(UNIAIRLINES.class.getResource("/vista/FXMLPempleado.fxml"));
                    Parent vista = cargador.load();

                    FXMLPempleadoController.inicializarDatos(usuarioAutenticado);

                    Scene escenaPrincipal = new Scene(vista);
                    escenarioBase.setScene(escenaPrincipal);
                    escenarioBase.setTitle("PANTALLA PRINCIPAL Empleado: " + usuarioAutenticado.getNombre());
                    escenarioBase.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                UtilGeneral.mostrarAlertaSimple(Alert.AlertType.WARNING,"Credenciales de hacker", "que tu hiciste loco pa llegar aca?. Por favor, verifique las credenciales.");;
                break;
        }
    }
}
