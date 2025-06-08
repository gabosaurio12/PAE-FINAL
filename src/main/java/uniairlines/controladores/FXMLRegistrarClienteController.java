package uniairlines.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniairlines.modelo.pojo.boleto.Cliente;

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
    private TextField fechaNacimientoTF;

    private Cliente cliente;

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
            fechaNacimientoTF.setText(cliente.getFechaNacimiento());
        }
    }

    @FXML
    private void guardarCliente() {
        if (camposValidos()) {
            cliente = new Cliente(
                    nombreTF.getText().trim(),
                    apellidoPTF.getText().trim(),
                    apellidoMTF.getText().trim(),
                    nacionalidadTF.getText().trim(),
                    fechaNacimientoTF.getText().trim()
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
                fechaNacimientoTF.getText().trim().isEmpty()) {
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
