package uniairlines.util;

import com.opencsv.CSVWriter;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtil {
    private final utilgeneral util = new utilgeneral();

    public void generarCSVPilotos(String path, List<Piloto> pilotos) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] encabezado = {"ID", "Nombre", "Dirección", "Teléfono",
                    "Correo", "Fecha de nacimiento", "Género", "Salario",
                    "Tipo de Licencia", "Fecha de certificación", "Horas de vuelo"};
            writer.writeNext(encabezado);
            for (Piloto piloto : pilotos) {
                String[] pilotoCSV = piloto.formatoCSV();
                writer.writeNext(pilotoCSV);
            }
            util.createAlert("Éxito", "Se creó con éxito el CSV");

        } catch (IOException e) {
            util.createAlert("Error", "Error al crear CSV");

            System.err.println("Error al crear CSV: " + e.getMessage());
        }
    }

    public void generarCSVAsistentesVuelo(String path, List<AsistenteVuelo> asistentes) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] encabezado = {"ID", "Nombre", "Dirección", "Teléfono",
                    "Correo", "Fecha de nacimiento", "Género", "Salario",
                    "Número de idiomas", "Horas de vuelo asistidas"};
            writer.writeNext(encabezado);
            for (AsistenteVuelo asistente : asistentes) {
                String[] asistenteCSV = asistente.formatoCSV();
                writer.writeNext(asistenteCSV);
            }
            util.createAlert("Éxito", "Se creó con éxito el CSV");

        } catch (IOException e) {
            util.createAlert("Error", "Error al crear CSV");
            System.err.println("Error al crear CSV: " + e.getMessage());
        }
    }
    public void generarCSVClientes(String path, List<Cliente> clientes) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] encabezado = {"Nombre", "Dirección", "Teléfono", "Correo", "Fecha de nacimiento"};
            writer.writeNext(encabezado);
            for (Cliente cliente : clientes) {
                String[] clienteCSV = cliente.formatoCSV();
                writer.writeNext(clienteCSV);
            }
            util.createAlert("Éxito", "Se creó con éxito el CSV");
        } catch (IOException e) {
            util.createAlert("Error", "Error al crear CSV");
            System.err.println("Error al crear CSV: " + e.getMessage());
        }
    }
}
