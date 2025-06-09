package uniairlines.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import uniairlines.modelo.Asiento;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Boleto;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.modelo.pojo.boleto.ClaseBoleto;
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

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
        mostrarAsientos();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            txtNombreCliente.setText(cliente.getNombre() + " " + cliente.getApellidoP() + " " + cliente.getApellidoM());
        }
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
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
                case "Turista":
                    return "lightgreen";
                case "Ejecutivo":
                    return "lightblue";
                case "VIP":
                    return "yellow";
                default:
                    return "gray";
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
        // Aquí no asignamos btnConfirmar.setOnAction anónimo,
        // porque vamos a usar el método confirmarVenta directamente ligado en el FXML.
    }

    @FXML
    private void confirmarVenta(ActionEvent event) {
        if (asientosSeleccionados.isEmpty()) {
            UtilGeneral.mostrarAlerta("Sin selección",
                    "Por favor selecciona al menos un asiento para continuar.",
                    Alert.AlertType.WARNING);
            return;
        }

        // Crear boletos para cada asiento seleccionado
        List<Boleto> boletosVenta = new ArrayList<>();
        for (Asiento asiento : asientosSeleccionados) {
            ClaseBoleto claseBoleto;
            switch (asiento.getClase()) {
                case "Turista":
                    claseBoleto = ClaseBoleto.TURISTA;
                    break;
                case "Ejecutivo":
                    claseBoleto = ClaseBoleto.EJECUTIVO;
                    break;
                case "VIP":
                    claseBoleto = ClaseBoleto.VIP;
                    break;
                default:
                    claseBoleto = ClaseBoleto.TURISTA; // Valor por defecto
            }

            Boleto boleto = new Boleto(vuelo, cliente, claseBoleto);
            // Aquí podrías agregar datos del asiento si tu POJO lo permite
            boletosVenta.add(boleto);

            // Actualizar estado del asiento a ocupado
            asiento.setEstado("Ocupado");
        }

        // Guardar boletosVenta en tu persistencia (archivo JSON, base de datos, etc)
        // Aquí debes llamar a tu método DAO o servicio para guardar la venta:
        // Ejemplo:
        // VentaDAO.guardarVenta(vuelo, cliente, boletosVenta);

        UtilGeneral.mostrarAlerta("Venta Confirmada",
                "La venta de boletos se realizó correctamente.",
                Alert.AlertType.INFORMATION);

        // Cerrar ventana después de la venta
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.close();
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
