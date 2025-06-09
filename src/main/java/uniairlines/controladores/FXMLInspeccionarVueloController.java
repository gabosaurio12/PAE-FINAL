package uniairlines.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uniairlines.modelo.pojo.Vuelo;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLInspeccionarVueloController implements Initializable {
    @FXML
    private Label lbTiempoVuelo;
    @FXML
    private Label lbPiloto;
    @FXML
    private Label lbCopiloto;
    @FXML
    private Label lbAsistenteUno;
    @FXML
    private Label lbAsistenteDos;
    @FXML
    private Label lbAsistenteTres;
    @FXML
    private Label lbAsistenteCuatro;
    @FXML
    private Label lbPuertaSalida;
    @FXML
    private Label lbPuertaLlegada;
    @FXML
    private Label lbPrecioTurista;
    @FXML
    private Label lbPrecioNegocios;
    @FXML
    private Label lbPrecioPrimeraClase;

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cargarDatos(Vuelo vuelo) {
        lbTiempoVuelo.setText(String.valueOf(vuelo.getMinutosDeVueloEstimados()));
        lbPiloto.setText(vuelo.getPiloto().getNombre());
        lbCopiloto.setText(vuelo.getCopiloto().getNombre());
        if(vuelo.getAsistentes().size() == 4) {
            lbAsistenteUno.setText(vuelo.getAsistentes().get(0).getNombre());
            lbAsistenteDos.setText(vuelo.getAsistentes().get(1).getNombre());
            lbAsistenteTres.setText(vuelo.getAsistentes().get(2).getNombre());
            lbAsistenteCuatro.setText(vuelo.getAsistentes().get(3).getNombre());
        }
        lbPuertaSalida.setText(String.valueOf(vuelo.getPuertaSalida()));
        lbPuertaLlegada.setText(String.valueOf(vuelo.getPuertaLlegada()));
        lbPrecioTurista.setText(String.valueOf(vuelo.getPrecioTurista()));
        lbPrecioNegocios.setText(String.valueOf(vuelo.getPrecioNegocios()));
        lbPrecioPrimeraClase.setText(String.valueOf(vuelo.getPrecioPrimeraClase()));
    }

    public void clicCerrar(ActionEvent actionEvent) {
        Stage stage = (Stage) lbTiempoVuelo.getScene().getWindow();
        stage.close();
    }
}
