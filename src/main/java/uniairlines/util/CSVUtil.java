package uniairlines.util;

import com.opencsv.CSVWriter;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtil {

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
            UtilGeneral.createAlert("Éxito", "Se creó con éxito el CSV");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Error al crear CSV");

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
            UtilGeneral.createAlert("Éxito", "Se creó con éxito el CSV");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Error al crear CSV");
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
            UtilGeneral.createAlert("Éxito", "Se creó con éxito el CSV");
        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Error al crear CSV");
            System.err.println("Error al crear CSV: " + e.getMessage());
        }
    }

    public void generarCSVAviones(String path, List<Avion> aviones) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] encabezado = {
                    "ID",
                    "Modelo",
                    "Capacidad Total",
                    "Filas",
                    "Asientos por Fila",
                    "Peso (kg)",
                    "Tipo",
                    "Estado",
                    "Número de Asientos"
            };
            writer.writeNext(encabezado);

            for (Avion avion : aviones) {
                String[] avionCSV = avion.formatoCSV();
                writer.writeNext(avionCSV);
            }

            UtilGeneral.createAlert("Éxito", "Se creó con éxito el CSV de aviones");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Error al crear CSV de aviones");
            System.err.println("Error al crear CSV de aviones: " + e.getMessage());
        }
    }
}
