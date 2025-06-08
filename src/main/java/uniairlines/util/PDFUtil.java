package uniairlines.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Avion;
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
}


