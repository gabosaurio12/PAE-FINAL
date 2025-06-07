package uniairlines.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import uniairlines.dao.AvionDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Asiento;
import uniairlines.modelo.Avion;
import uniairlines.util.utilgeneral;

public class FXMLTablaAvionesController implements Initializable {

    private String aerolineaSeleccionada;

    @FXML private TableView<Avion> tablaAviones;
    @FXML private TableColumn<Avion, String> colId;
    @FXML private TableColumn<Avion, String> colModelo;
    @FXML private TableColumn<Avion, Integer> colCapacidad;
    @FXML private TableColumn<Avion, String> colTipo;
    @FXML
    private TableColumn<Avion, String> colEstado;
    @FXML
    private TableColumn<Avion, Double> colPeso;

    private final AvionDAO avionDAO = new AvionDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadTotal"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        tablaAviones.setRowFactory(tv -> {
        
        TableRow<Avion> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                Avion avionSeleccionado = row.getItem();
                mostrarVentanaAsientos(avionSeleccionado);
            }
        });
        return row ;
    });
    }

    public void inicializarConAerolinea(String seleccionada) {
        this.aerolineaSeleccionada = seleccionada;
        cargarAviones();
    }

    private void cargarAviones() {
        try {
            List<Avion> aviones = avionDAO.listar(aerolineaSeleccionada);
            tablaAviones.getItems().setAll(aviones);
        } catch (ArchivoException e) {
            System.out.println("Error al cargar aviones: " + e.getMessage());
        }
    }


    @FXML
    private void agregarAvion() {
        abrirFormularioAvion(null,false);
    }

    @FXML
    private void modificarAvion() throws ArchivoException {
        String id = obtenerIdAvionSeleccionado();
        Avion completo = avionDAO.buscarPorId("asd",id);
        abrirFormularioAvion(completo,true);
        
        
    }


    private void abrirFormularioAvion(Avion avion, boolean esEdicion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioAvion.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(avion == null ? "Agregar Avión" : "Modificar Avión");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana padre


            FXMLFormularioAvionController controlador = loader.getController();
            controlador.setAerolinea(aerolineaSeleccionada);
            if (!esEdicion){
           controlador.setAvion(null,esEdicion);
            }if(esEdicion){
           controlador.setAvion(avion,esEdicion); 
            }
            stage.showAndWait();

            // Recargar tabla después de cerrar el formulario
            cargarAviones();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al abrir formulario: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        utilgeneral.mostrarAlertaSimple(AlertType.ERROR, "?", "llego aca");
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void eliminarAvion(ActionEvent event) {
    
    String id = obtenerIdAvionSeleccionado();
        try {
            avionDAO.eliminar("asd", id);
            cargarAviones();
        } catch (ArchivoException ex) {
            Logger.getLogger(FXMLTablaAvionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String obtenerIdAvionSeleccionado() {
    Avion avionSeleccionado = tablaAviones.getSelectionModel().getSelectedItem();
    if (avionSeleccionado != null) {
        String idAvion = avionSeleccionado.getId(); // O avionSeleccionado.getIdAvion() si ese es el nombre del método
        System.out.println("ID del avión seleccionado: " + idAvion);
         return idAvion;
    } else {
        Alert alerta = new Alert(AlertType.WARNING, "No se ha seleccionado ningún avión.", ButtonType.OK);
        alerta.showAndWait();
        return "hola";
    }
  
}

    private void mostrarVentanaAsientos(Avion avionSeleccionado) {

        try {
            
            Avion hola = avionDAO.buscarPorId(aerolineaSeleccionada, avionSeleccionado.getId());
            List<Asiento> asientos = new ArrayList<>();
            int filas = hola.getFilas();
            int asientosPorFila = hola.getAsientosPorFila(); // fijo como dijiste
            
            for (int fila = 1; fila <= filas; fila++) {
                for (int col = 0; col < asientosPorFila; col++) {
                    String letraColumna = String.valueOf((char) ('A' + col));
                    Asiento asiento = new Asiento(fila, letraColumna, "Turista", "Libre", 0);
                    asientos.add(asiento);
                }
            }
            
            
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLAsientosAvion.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana padre
                
                
                FXMLAsientosAvionController controlador = loader.getController();
                controlador.setAsientos(asientos);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error al abrir formulario: " + e.getMessage());
            }
        } catch (ArchivoException ex) {
            Logger.getLogger(FXMLTablaAvionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
