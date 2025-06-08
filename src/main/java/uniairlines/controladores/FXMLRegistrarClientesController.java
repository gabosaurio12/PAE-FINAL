package uniairlines.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniairlines.dao.ClienteDAO;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.util.utilgeneral;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 * 
 * @author snake
 */

public class FXMLRegistrarClientesController implements Initializable {
    ClienteDAO clienteDAO = new ClienteDAO();

    public TextField tfNombre;
    public TextField tfAerolinea;
    public TextField tfApPaterno;
    public TextField tfApMaterno;
    public TextField tfNacionalidad;
    public DatePicker dpFechaNacimiento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }

    @FXML
    public void buttonRegistrar(ActionEvent actionEvent) {
        try {
            String nombre = tfNombre.getText();
            String aerolinea = tfAerolinea.getText();
            String apellidoP = tfApPaterno.getText();
            String apellidoM = tfApMaterno.getText();
            String nacionalidad = tfNacionalidad.getText();
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue();

            if (validarCampos()) {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setApellidoP(apellidoP);
                cliente.setApellidoM(apellidoM);
                cliente.setNacionalidad(nacionalidad);
                cliente.setFechaNacimiento(fechaNacimiento.toString());

                clienteDAO.agregar(cliente, aerolinea);
            }
        } catch (Exception e) {
            utilgeneral.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Ocurrió un error al registrar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonCancelar(ActionEvent actionEvent) {
        ((Stage) tfNombre.getScene().getWindow()).close();
    }

    private boolean validarCampos() {
        if (tfNombre.getText().isEmpty() || tfApPaterno.getText().isEmpty() ||
                tfApMaterno.getText().isEmpty() || tfNacionalidad.getText().isEmpty() ||
                dpFechaNacimiento.getValue() == null) {
            utilgeneral.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }
}