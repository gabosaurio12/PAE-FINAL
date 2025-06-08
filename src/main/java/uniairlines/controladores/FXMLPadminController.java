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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uniairlines.modelo.Usuario;
import uniairlines.util.UtilGeneral;

/**
 * FXML Controller class
 *
 * @author cuent
 */
public class FXMLPadminController implements Initializable {
    private final UtilGeneral util = new UtilGeneral();

    @FXML
    private Label lbAerolinea;

    private static Label lbAerolineaStatic; // Referencia estática
    private static String aerolineaSeleccionada;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbAerolineaStatic = lbAerolinea; // Guardamos la referencia
    }

    public static void inicializarDatos(Usuario usuarioAutenticado) {
        if (lbAerolineaStatic != null) {
            lbAerolineaStatic.setText(usuarioAutenticado.getAerolinea());
            aerolineaSeleccionada = usuarioAutenticado.getAerolinea();
        }
    }
    
    public static void inicializarDatos(String aerolinea) {
        lbAerolineaStatic.setText(aerolinea);
        aerolineaSeleccionada = aerolinea;
    }

    public void cerrarSesion() {
        util.abrirFXML("/vista/FXMLLogin.fxml", "INICIO DE SESIÓN", FXMLLoginController.class);
        Stage stage = (Stage) lbAerolinea.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btnAviones(ActionEvent event) {
        irTablaAviones();
    }

    private void irTablaAviones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLTablaAviones.fxml"));
            Parent vista = loader.load();


            FXMLTablaAvionesController controlador = loader.getController();
            controlador.inicializarConAerolinea(aerolineaSeleccionada);

            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(vista);

            escenarioAdmin.setScene(escena);
            escenarioAdmin.setTitle("ADMINISTRACIÓN DE Aviones  ");
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.centerOnScreen();
            escenarioAdmin.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }       
    }
    
    public void gestionarPilotos() {
        FXMLPilotosController controlador = util.abrirFXML(
                "/vista/FXMLPilotos.fxml",
                "Pilotos", FXMLPilotosController.class);
        controlador.initComponentes(aerolineaSeleccionada);
        Stage stage = (Stage) lbAerolinea.getScene().getWindow();
        stage.close();
    }
    
    public void gestionarAsistentesVuelo() {
        FXMLAsistentesVueloController controlador = util.abrirFXML(
                "/vista/FXMLAsistentesVuelo.fxml",
                "Pilotos", FXMLAsistentesVueloController.class);
        controlador.initComponentes(aerolineaSeleccionada);
        Stage stage = (Stage) lbAerolinea.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void btnAerolineas(ActionEvent actionEvent) {
        try {
            Stage escenarioBase = (Stage) lbAerolinea.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/vista/FXMLPmaster.fxml"));
            Parent vista = cargador.load();

            // FXMLPmasterController.inicializarDatos(usuarioAutenticado);

            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("PANTALLA PRINCIPAL AEROLINEAS: ");
            escenarioBase.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gestionarVuelos(ActionEvent actionEvent) {
        util.abrirFXML(
                "/vista/FXMLTablaVuelos.fxml",
                "Vuelos", FXMLTablaVuelosController.class);
        Stage stage = (Stage) lbAerolinea.getScene().getWindow();
        stage.close();
    }
}