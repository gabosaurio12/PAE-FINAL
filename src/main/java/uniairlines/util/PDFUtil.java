package uniairlines.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;

import java.util.List;

public class PDFUtil {
    private final utilgeneral util = new utilgeneral();

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
            util.createAlert("Creción de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            util.createAlert("Error", "Error al crear PDF");
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
            util.createAlert("Creción de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            util.createAlert("Error", "Error al crear PDF");
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
            util.createAlert("Creación de PDF", "Se creó con éxito el PDF");
        } catch (Exception e) {
            util.createAlert("Error", "Error al crear PDF");
            System.err.println("Error al crear PDF: " + e.getMessage());
        }
    }
}
