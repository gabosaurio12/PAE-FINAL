/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.util;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Window;
import uniairlines.UNIAIRLINES;

/**
 *
 * @author cuent
 */
public class UtilGeneral {
    
    public static Optional<ButtonType> mostrarAlertaSimple(Alert.AlertType tipo,
        String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        return alerta.showAndWait();
    }

    public static boolean mostrarAlertaConfirmacion(String titulo,String contenido){
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText(contenido);


        return (alertaConfirmacion.showAndWait().get()) ==ButtonType.OK;
    }
    
    public static void createAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void mostrarAlerta(String title, String message, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public <T> T abrirFXML(String rutaFXML, String titulo, Class<T> claseControlador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

            T loaderController;
            loaderController = loader.getController();
            if (claseControlador.isInstance(loaderController)) {
                return claseControlador.cast(loaderController);
            } else {
                System.err.println("El controlador no es del tipo esperdado: {}" +
                        claseControlador.getSimpleName());
                return null;
            }
        } catch (IOException e) {
            createAlert("Error al abrir" + titulo,
                    "Volverá a la ventana anterior");
            System.err.println("Error al abrir " + titulo + e.getMessage());
            return null;
        }   
    }
    
    public <T> ResultadoFXML<T> abrirFXMLModal(String rutaFXML, String titulo, Class<T> claseControlador, Window owner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            
            if (owner != null) {
                stage.initOwner(owner);
                stage.initModality(Modality.APPLICATION_MODAL);
            }

            T controlador = loader.getController();
            if (!claseControlador.isInstance(controlador)) {
                System.err.println("El controlador no es del tipo esperado " +
                        claseControlador.getSimpleName());
                return null;
            }
            return new ResultadoFXML<>(controlador, stage);
        } catch (IOException e) {
            createAlert("Error al abrir" + titulo,
                    "Volverá a la ventana anterior");
            System.err.println("Error al abrir " + titulo + e.getMessage());
            return null;
        }   
    }

    public static void crearEscenarioSimple(String URL, String tituloEscenario) {
        try {
            Stage nuevoEscenario = new Stage();
            Parent vista = FXMLLoader.load(UNIAIRLINES.class.getResource(URL));
            Scene nuevaEscena = new Scene(vista);
            nuevoEscenario.setScene(nuevaEscena);
            nuevoEscenario.setTitle(tituloEscenario);
            nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenario.showAndWait();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}