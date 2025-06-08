package uniairlines.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniairlines.modelo.pojo.boleto.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FXMLRegistrarClienteController {

    @FXML
    private TextField nombreTF;
    @FXML
    private TextField apellidoPTF;
    @FXML
    private TextField apellidoMTF;
    @FXML
    private TextField nacionalidadTF;
    @FXML
    private DatePicker fechaNacimientoDP;

    private Cliente cliente;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void inicializarCliente(Cliente clienteExistente) {
        if (clienteExistente != null) {
            this.cliente = new Cliente(
                    clienteExistente.getNombre(),
                    clienteExistente.getApellidoP(),
                    clienteExistente.getApellidoM(),
                    clienteExistente.getNacionalidad(),
                    clienteExistente.getFechaNacimiento()
            );

            nombreTF.setText(cliente.getNombre());
            apellidoPTF.setText(cliente.getApellidoP());
            apellidoMTF.setText(cliente.getApellidoM());
            nacionalidadTF.setText(cliente.getNacionalidad());

            try {
                LocalDate fecha = LocalDate.parse(cliente.getFechaNacimiento(), formatter);
                fechaNacimientoDP.setValue(fecha);
            } catch (DateTimeParseException e) {
                fechaNacimientoDP.setValue(null);
            }
        }
    }

    @FXML
    private void guardarCliente() {
        if (camposValidos()) {
            String fechaFormateada = fechaNacimientoDP.getValue().format(formatter);

            cliente = new Cliente(
                    nombreTF.getText().trim(),
                    apellidoPTF.getText().trim(),
                    apellidoMTF.getText().trim(),
                    nacionalidadTF.getText().trim(),
                    fechaFormateada
            );
            cerrarVentana();
        }
    }

    @FXML
    private void cancelar() {
        cliente = null;
        cerrarVentana();
    }

    private boolean camposValidos() {
        if (nombreTF.getText().trim().isEmpty() ||
                apellidoPTF.getText().trim().isEmpty() ||
                apellidoMTF.getText().trim().isEmpty() ||
                nacionalidadTF.getText().trim().isEmpty() ||
                fechaNacimientoDP.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Por favor, llena todos los campos.");
            return false;
        }
        return true;
    }

    public Cliente getCliente() {
        return cliente;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) nombreTF.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
