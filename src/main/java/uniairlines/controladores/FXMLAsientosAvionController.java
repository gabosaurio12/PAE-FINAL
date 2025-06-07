package uniairlines.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import uniairlines.modelo.Asiento;

public class FXMLAsientosAvionController implements Initializable {

    @FXML
    private GridPane gridAsientos;

    @FXML
    private Button btnTurista, btnVIP, btnEjecutivo;

    @FXML
    private TextField tfPrecioTurista, tfPrecioVIP, tfPrecioEjecutivo;

    private List<Asiento> asientos;

    // Clase actual seleccionada para asignar al asiento
    private String claseSeleccionada = "Turista"; // Valor por defecto

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar los botones de clase para cambiar la clase seleccionada
        btnTurista.setOnAction(e -> claseSeleccionada = "Turista");
        btnVIP.setOnAction(e -> claseSeleccionada = "VIP");
        btnEjecutivo.setOnAction(e -> claseSeleccionada = "Ejecutivo");
    }

    // Método para recibir los asientos y mostrarlos
    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
        mostrarAsientos();
    }

    private void mostrarAsientos() {
        gridAsientos.getChildren().clear();

        for (Asiento asiento : asientos) {
            Button btn = new Button(asiento.getFila() + asiento.getColumna());
            btn.setPrefSize(40, 40);

            // Pintar color inicial según estado y clase
            btn.setStyle("-fx-font-size: 10px; -fx-background-color: " + obtenerColor(asiento) + ";");

            // Evento al hacer click en el asiento
            btn.setOnAction(e -> {
                if (!"Ocupado".equalsIgnoreCase(asiento.getEstado())) {
                    asiento.setClase(claseSeleccionada);

                    // Actualizar precio según clase seleccionada y valor en TextField
                    switch (claseSeleccionada) {
                        case "Turista":
                            asiento.setPrecio(parsePrecio(tfPrecioTurista.getText()));
                            break;
                        case "VIP":
                            asiento.setPrecio(parsePrecio(tfPrecioVIP.getText()));
                            break;
                        case "Ejecutivo":
                            asiento.setPrecio(parsePrecio(tfPrecioEjecutivo.getText()));
                            break;
                    }

                    // Actualizar color del botón al nuevo color
                    btn.setStyle("-fx-font-size: 10px; -fx-background-color: " + obtenerColor(asiento) + ";");
                }
            });

            gridAsientos.add(btn, asiento.getColumna().charAt(0) - 'A', asiento.getFila() - 1);
        }
    }

    // Método auxiliar para obtener color según estado y clase
    private String obtenerColor(Asiento asiento) {
        if ("Ocupado".equalsIgnoreCase(asiento.getEstado())) {
            return "red";
        } else if ("Libre".equalsIgnoreCase(asiento.getEstado())) {
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
        } else {
            return "gray";
        }
    }

    // Parsear precio seguro, si no es válido devuelve 0
    private double parsePrecio(String texto) {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Método para obtener la lista actualizada con precios y clases cambiadas
    public List<Asiento> getAsientosActualizados() {
        return asientos;
    }
}
