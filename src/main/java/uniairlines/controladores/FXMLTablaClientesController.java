package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uniairlines.dao.ClienteDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.util.CSVUtil;
import uniairlines.util.PDFUtil;
import uniairlines.util.XLSXUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FXMLTablaClientesController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> nombre;
    @FXML private TableColumn<Cliente, String> apellidoP;
    @FXML private TableColumn<Cliente, String> apellidoM;
    @FXML private TableColumn<Cliente, String> nacionalidad;
    @FXML private TableColumn<Cliente, String> fechaNacimiento;

    private ObservableList<Cliente> listaClientes;
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoP"));
        apellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoM"));
        nacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        fechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        cargarClientes();
    }

    private void cargarClientes() {
        try {
            listaClientes = FXCollections.observableArrayList(clienteDAO.listar());
            tablaClientes.setItems(listaClientes);
        } catch (ArchivoException e) {
            mostrarAlerta("Error", "No se pudieron cargar los clientes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void agregarCliente() {
        Cliente nuevo = mostrarVentanaCliente(null);
        if (nuevo != null) {
            try {
                clienteDAO.guardar(nuevo);
                listaClientes.add(nuevo);
            } catch (ArchivoException e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void modificarCliente() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Atención", "Debes seleccionar un cliente para modificar.", Alert.AlertType.WARNING);
            return;
        }

        Cliente modificado = mostrarVentanaCliente(seleccionado);
        if (modificado != null) {
            try {
                clienteDAO.actualizar(modificado, seleccionado.getNombre(), seleccionado.getApellidoP());
                cargarClientes();
            } catch (ArchivoException e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void eliminarCliente() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Atención", "Selecciona un cliente para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de eliminar a " + seleccionado.getNombre() + "?");
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                clienteDAO.eliminar(seleccionado);
                listaClientes.remove(seleccionado);
            } catch (ArchivoException e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private Cliente mostrarVentanaCliente(Cliente clienteExistente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLRegistrarCliente.fxml"));
            Parent root = loader.load();

            FXMLRegistrarClienteController controlador = loader.getController();
            controlador.inicializarCliente(clienteExistente);

            Stage stage = new Stage();
            stage.setTitle(clienteExistente == null ? "Registrar Cliente" : "Modificar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            return controlador.getCliente();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    public void exportarPDF(ActionEvent actionEvent) {
        try {
            List<Cliente> clientes = clienteDAO.listar();
            PDFUtil pdfUtil = new PDFUtil();
            String path = "Documentos/Clientes/";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            path = path.concat("Clientes.pdf");
            pdfUtil.generarPDFClientes(path, clientes);
            mostrarAlerta("Éxito", "PDF generado correctamente en " + path, Alert.AlertType.INFORMATION);
        } catch (ArchivoException e) {
            mostrarAlerta("Error", "Error al exportar PDF: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void exportarCSV(ActionEvent actionEvent) {
        try {
            List<Cliente> clientes = clienteDAO.listar();
            CSVUtil csvUtil = new CSVUtil();
            String path = "Documentos/Clientes/";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            path = path.concat("Clientes.csv");
            csvUtil.generarCSVClientes(path, clientes);
            mostrarAlerta("Éxito", "CSV generado correctamente en " + path, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al exportar CSV: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void exportarXLSX(ActionEvent actionEvent) {
        try {
            List<Cliente> clientes = clienteDAO.listar();
            XLSXUtil xlsxUtil = new XLSXUtil();
            String path = "Documentos/Clientes/";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
            path = path.concat("Clientes.xlsx");
            xlsxUtil.generarXLSXClientes(path, clientes);
            mostrarAlerta("Éxito", "XLSX generado correctamente en " + path, Alert.AlertType.INFORMATION);
        } catch (ArchivoException e) {
            mostrarAlerta("Error", "Error al exportar XLSX: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
