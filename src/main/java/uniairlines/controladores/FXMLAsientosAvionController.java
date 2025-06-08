package uniairlines.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import uniairlines.modelo.Asiento;

public class FXMLAsientosAvionController implements Initializable {

    @FXML
    private GridPane gridAsientos;

    @FXML
    private Button btnTurista, btnVIP, btnEjecutivo, btnGuardar;

    private List<Asiento> asientos;

    private String claseSeleccionada = "Turista"; // Valor por defecto

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnTurista.setOnAction(e -> claseSeleccionada = "Turista");
        btnVIP.setOnAction(e -> claseSeleccionada = "VIP");
        btnEjecutivo.setOnAction(e -> claseSeleccionada = "Ejecutivo");

        btnGuardar.setOnAction(e -> {
            List<Asiento> copia = generarCopiaDeAsientos();

            // Aquí tu lógica de guardado o procesamiento
            // ...

            mostrarAlerta("Avión actualizado correctamente.");

            // Cerrar la ventana actual
            btnGuardar.getScene().getWindow().hide();
        });
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
        mostrarAsientos();
    }

    private void mostrarAsientos() {
        gridAsientos.getChildren().clear();

        for (Asiento asiento : asientos) {
            Button btn = new Button(asiento.getFila() + asiento.getColumna());
            btn.setPrefSize(40, 40);

            btn.setStyle("-fx-font-size: 10px; -fx-background-color: " + obtenerColor(asiento) + ";");

            btn.setOnAction(e -> {
                if (!"Ocupado".equalsIgnoreCase(asiento.getEstado())) {
                    asiento.setClase(claseSeleccionada);
                    btn.setStyle("-fx-font-size: 10px; -fx-background-color: " + obtenerColor(asiento) + ";");
                }
            });

            gridAsientos.add(btn, asiento.getColumna().charAt(0) - 'A', asiento.getFila() - 1);
        }
    }

    private String obtenerColor(Asiento asiento) {
        if ("Ocupado".equalsIgnoreCase(asiento.getEstado())) {
            return "darkred";
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

    public List<Asiento> getAsientosActualizados() {
        return asientos;
    }

    public List<Asiento> generarCopiaDeAsientos() {
        List<Asiento> copia = new ArrayList<>();
        for (Asiento a : asientos) {
            copia.add(new Asiento(
                    a.getFila(),
                    a.getColumna(),
                    a.getClase(),
                    a.getEstado(),
                    a.getPrecio()
            ));
        }
        return copia;
    }
}
