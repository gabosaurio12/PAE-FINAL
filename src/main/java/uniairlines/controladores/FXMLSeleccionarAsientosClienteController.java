package uniairlines.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Boleto;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.modelo.pojo.boleto.ClaseBoleto;
import uniairlines.util.UtilGeneral;
import uniairlines.util.ArchivoUtil;

import java.util.ArrayList;
import java.util.List;

public class FXMLSeleccionarAsientosClienteController {

    @FXML
    private Spinner<Integer> spCantidadBoletos;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Label lblNombreCliente;

    private Cliente cliente;
    private Vuelo vuelo;

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spCantidadBoletos.setValueFactory(valueFactory);
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            lblNombreCliente.setText(cliente.getNombre() + " " + cliente.getApellidoP() + " " + cliente.getApellidoM());
        }
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    @FXML
    private void confirmarVenta(ActionEvent event) {
        int cantidad = spCantidadBoletos.getValue();

        if (cliente == null || vuelo == null) {
            UtilGeneral.mostrarAlerta("Error", "Cliente o vuelo no asignado.", Alert.AlertType.ERROR);
            return;
        }

        // Crear un único objeto con cantidad
        Boleto nuevoBoleto = new Boleto(vuelo, cliente, ClaseBoleto.TURISTA, cantidad);

        try {
            // Obtener lista existente
            List<Boleto> boletosExistentes = ArchivoUtil.cargarBoletos();

            // Verificar si ya existe uno igual
            boolean agregado = false;
            for (Boleto b : boletosExistentes) {
                if (b.getVuelo().getCodigoVuelo().equals(vuelo.getCodigoVuelo())
                        && b.getCliente().equals(cliente)
                        && b.getClase() == ClaseBoleto.TURISTA) {
                    b.setCantidad(b.getCantidad() + cantidad);
                    agregado = true;
                    break;
                }
            }

            // Si no existía, se agrega nuevo
            if (!agregado) {
                boletosExistentes.add(nuevoBoleto);
            }

            // Guardar la lista actualizada
            ArchivoUtil.guardarBoletos(boletosExistentes);
            UtilGeneral.mostrarAlerta("Venta realizada", "Se han generado " + cantidad + " boletos para el cliente.", Alert.AlertType.INFORMATION);
            ((Stage) btnConfirmar.getScene().getWindow()).close();

        } catch (Exception e) {
            UtilGeneral.mostrarAlerta("Error", "No se pudieron guardar los boletos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
