/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package uniairlines;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniairlines.excepcion.ArchivoException;

import static uniairlines.util.estructuraUtil.Arranque;

/**
 *
 * @author cuent
 */
public class UNIAIRLINES extends Application {

    @Override
    public void start(Stage primaryStage) throws ArchivoException {

        try {
            Parent vista = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/vista/FXMLLogin.fxml")));
            Scene escenaInicioSesion = new Scene(vista);
            
            primaryStage.setScene(escenaInicioSesion);
            primaryStage.setTitle("INICIO DE SESIÓN");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Arranque();
            launch(args);
        } catch (ArchivoException ex) {
            Logger.getLogger(UNIAIRLINES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
