package uniairlines.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.scene.control.Alert;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Boleto;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;

import java.util.List;

public class PDFUtil {

    public void generarPDFPilotos(String path, List<Piloto> pilotos) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            for (Piloto piloto : pilotos) {
                String parrafo = piloto.formatoPDF();
                document.add(new Paragraph(parrafo));
            }
            document.close();
            UtilGeneral.createAlert("Creción de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            UtilGeneral.createAlert("Error", "Error al crear PDF");
            System.err.println("Error al crear PDF: " + e.getMessage());
        }
    }

    public void generarPDFAsistentesVuelo(String path, List<AsistenteVuelo> asistentes) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            for (AsistenteVuelo asistente : asistentes) {
                String parrafo = asistente.formatoPDF();
                document.add(new Paragraph(parrafo));
            }
            document.close();
            UtilGeneral.createAlert("Creción de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            UtilGeneral.createAlert("Error", "Error al crear PDF");
            System.err.println("Error al crear PDF: " + e.getMessage());
        }
    }

    public void generarPDFClientes(String path, List<Cliente> clientes) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            for (Cliente cliente : clientes) {
                String parrafo = cliente.formatoPDF();
                document.add(new Paragraph(parrafo));
            }
            document.close();
            UtilGeneral.createAlert("Creación de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            UtilGeneral.createAlert("Error", "Error al crear PDF");
            System.err.println("Error al crear PDF: " + e.getMessage());
        }
    }

    public void generarPDFAviones(String path, List<Avion> aviones) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            for (Avion avion : aviones) {
                String parrafo = avion.formatoPDF();
                document.add(new Paragraph(parrafo));
            }
            document.close();
            UtilGeneral.createAlert("Creación de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            UtilGeneral.createAlert("Error", "Error al crear PDF");
            System.err.println("Error al crear PDF: " + e.getMessage());
        }
    }
    public void generarPDFAerolineas(String path, List<Aerolinea> aerolineas) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            for (Aerolinea aerolinea : aerolineas) {
                String parrafo = aerolinea.formatoPDF();  // Aquí se usa el método que formatea la info de Aerolinea
                document.add(new Paragraph(parrafo));
            }

            document.close();
            UtilGeneral.createAlert("Creación de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            UtilGeneral.createAlert("Error", "Error al crear PDF");
            System.err.println("Error al crear PDF: " + e.getMessage());
        }
    }

    public void generarPDFBoletos(String path, List<Boleto> boletos) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            for (Boleto boleto : boletos) {
                String parrafo = String.format(
                        "Cliente: %s %s %s\nCódigo de vuelo: %s\nClase: %s\nCantidad: %d\n\n",
                        boleto.getCliente().getNombre(),
                        boleto.getCliente().getApellidoP(),
                        boleto.getCliente().getApellidoM(),
                        boleto.getVuelo().getCodigoVuelo(),
                        boleto.getClase(),
                        boleto.getCantidad()
                );
                document.add(new Paragraph(parrafo));
            }

            document.close();
            UtilGeneral.createAlert("Creación de PDF", "Se creó con éxito el PDF de boletos");
        } catch (Exception e) {
            UtilGeneral.createAlert("Error", "Error al crear PDF de boletos");
            System.err.println("Error al crear PDF de boletos: " + e.getMessage());
        }
    }

    public void generarPDFVuelos(String path, List<Vuelo> vuelos) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            for (Vuelo vuelo : vuelos) {
                String parrafo = String.format(
                        "Código de Vuelo: %s\nAerolinea: %s\nAvion: %s\nNum. Pasajeros: %s\nDestino: %s\nTiempo de Llegada: %s\nOrigen: %s\nTiempo de Partida: %s\n\n",
                        vuelo.getCodigoVuelo(),
                        vuelo.getAerolinea(),
                        vuelo.getCodigoAvion(),
                        vuelo.getNumPasajeros(),
                        vuelo.getDestino().toString(),
                        vuelo.getFechaLlegada(),
                        vuelo.getSalida().toString(),
                        vuelo.getFechaSalida()
                );
                document.add(new Paragraph(parrafo));
            }
            document.close();
            UtilGeneral.mostrarAlerta("Creación de PDF", "Se creó con éxito el PDF de vuelos", Alert.AlertType.INFORMATION);
        } catch (Exception ioex) {
            UtilGeneral.mostrarAlerta("Error", String.format("%s\n%s", ioex.getMessage(), ioex.getCause()), Alert.AlertType.ERROR);
        }
    }
}


