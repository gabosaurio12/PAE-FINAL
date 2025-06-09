package uniairlines.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import uniairlines.modelo.Asiento;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.util.UtilGeneral;

import java.util.ArrayList;
import java.util.List;

public class FXMLSeleccionarAsientosClienteController {

    @FXML
    private GridPane gridAsientos;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtNombreCliente;

    private List<Asiento> asientos; // Lista con todos los asientos del vuelo

    // Lista para almacenar los asientos seleccionados para la venta
    private List<Asiento> asientosSeleccionados = new ArrayList<>();

    private double total = 0;

    // Nuevos atributos para guardar cliente y vuelo recibidos
    private Cliente cliente;
    private Vuelo vuelo;

    // Método para pasar la lista de asientos desde la ventana anterior
    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
        mostrarAsientos();
    }

    // Nuevo método para asignar el cliente
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            txtNombreCliente.setText(cliente.getNombre() + " " + cliente.getApellidoP() + " " + cliente.getApellidoM());
        }
    }

    // Nuevo método para asignar el vuelo
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
        // Puedes hacer algo con el vuelo si lo necesitas
    }

    private void mostrarAsientos() {
        gridAsientos.getChildren().clear();

        if (asientos == null) return;

        for (Asiento asiento : asientos) {
            Button btn = new Button(asiento.getFila() + asiento.getColumna());
            btn.setPrefSize(40, 40);
            btn.setStyle("-fx-font-size: 10px; -fx-background-color: " + obtenerColor(asiento) + ";");

            btn.setOnAction(e -> {
                if ("Libre".equalsIgnoreCase(asiento.getEstado())) {
                    if (asientosSeleccionados.contains(asiento)) {
                        // Deseleccionar
                        asientosSeleccionados.remove(asiento);
                        btn.setStyle("-fx-font-size: 10px; -fx-background-color: " + obtenerColor(asiento) + ";");
                    } else {
                        // Seleccionar
                        asientosSeleccionados.add(asiento);
                        btn.setStyle("-fx-font-size: 10px; -fx-background-color: #00FF00;"); // verde brillante para seleccionado
                    }
                    actualizarTotal();
                }
            });

            gridAsientos.add(btn, asiento.getColumna().charAt(0) - 'A', asiento.getFila() - 1);
        }
    }

    private String obtenerColor(Asiento asiento) {
        if ("Ocupado".equalsIgnoreCase(asiento.getEstado())) {
            return "darkred";
        } else {
            switch (asiento.getClase()) {
                case "Turista": return "lightgreen";
                case "Ejecutivo": return "lightblue";
                case "VIP": return "yellow";
                default: return "gray";
            }
        }
    }

    private void actualizarTotal() {
        total = 0;
        for (Asiento asiento : asientosSeleccionados) {
            total += asiento.getPrecio();
        }
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    @FXML
    private void initialize() {
        btnConfirmar.setOnAction(e -> {
            if (asientosSeleccionados.isEmpty()) {
                // Mostrar alerta que debe seleccionar al menos un asiento
                UtilGeneral.mostrarAlerta("Sin selección", "Por favor selecciona al menos un asiento para continuar.", javafx.scene.control.Alert.AlertType.WARNING);
                return;
            }
            // Aquí puedes agregar lógica para continuar con la venta, pasar los asientos seleccionados y datos del cliente y vuelo

            // Cerrar ventana
            Stage stage = (Stage) btnConfirmar.getScene().getWindow();
            stage.close();
        });
    }

    public List<Asiento> getAsientosSeleccionados() {
        return asientosSeleccionados;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }
}
