package uniairlines.util;

import com.opencsv.CSVWriter;
import javafx.scene.control.Alert;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Boleto;
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
    public void generarCSVAerolineas(String path, List<Aerolinea> aerolineas) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] encabezado = {
                    "Nombre",
                    "IATA",
                    "ICAO",
                    "Callsign",
                    "Nacionalidad",
                    "Dirección",
                    "Sitio Oficial",
                    "Nombre de Contacto",
                    "Teléfono de Contacto"
            };
            writer.writeNext(encabezado);

            for (Aerolinea aerolinea : aerolineas) {
                String[] aerolineaCSV = aerolinea.formatoCSV();
                writer.writeNext(aerolineaCSV);
            }

            UtilGeneral.createAlert("Éxito", "Se creó con éxito el CSV de aerolíneas");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Error al crear CSV de aerolíneas");
            System.err.println("Error al crear CSV de aerolíneas: " + e.getMessage());
        }
    }
    public void generarCSVBoletos(String path, List<Boleto> boletos) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] encabezado = {
                    "Cliente",
                    "Apellido Paterno",
                    "Apellido Materno",
                    "Código Vuelo",
                    "Clase",
                    "Cantidad"
            };
            writer.writeNext(encabezado);

            for (Boleto boleto : boletos) {
                String[] datos = {
                        boleto.getCliente().getNombre(),
                        boleto.getCliente().getApellidoP(),
                        boleto.getCliente().getApellidoM(),
                        boleto.getVuelo().getCodigoVuelo(),
                        String.valueOf(boleto.getClase()),
                        String.valueOf(boleto.getCantidad())
                };
                writer.writeNext(datos);
            }

            UtilGeneral.createAlert("Éxito", "Se creó con éxito el CSV de boletos");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Error al crear CSV de boletos");
            System.err.println("Error al crear CSV de boletos: " + e.getMessage());
        }
    }

    public void generarCSVVuelos(String path, List<Vuelo> vuelos) {
        try(CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] encabezado = {
                    "Codigo de Vuelo",
                    "Aerolinea",
                    "Avion",
                    "Num. Pasajeros",
                    "Destino",
                    "Tiempo de Llegada",
                    "Origen",
                    "Tiempo de Partida"
            };
            writer.writeNext(encabezado);
            for (Vuelo vuelo : vuelos) {
                String[] datos = {
                        vuelo.getCodigoVuelo(),
                        vuelo.getAerolinea(),
                        vuelo.getCodigoAvion(),
                        String.valueOf(vuelo.getNumPasajeros()),
                        vuelo.getDestino().toString(),
                        vuelo.getFechaLlegada(),
                        vuelo.getSalida().toString(),
                        vuelo.getFechaSalida()
                };
                writer.writeNext(datos);
            }
            UtilGeneral.mostrarAlerta("Exito", "Archivo CSV generado exitosamente", Alert.AlertType.INFORMATION);
        } catch (IOException ioex) {
            UtilGeneral.mostrarAlerta("Error", String.format("%s \n %s", ioex.getMessage(), ioex.getCause()), Alert.AlertType.ERROR);
        }
    }
}
