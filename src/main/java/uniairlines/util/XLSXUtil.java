package uniairlines.util;

import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Avion;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Boleto;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XLSXUtil {
    public void generarXLSXPilotos(String path, List<Piloto> pilotos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos");

            Row filaEncabezado = sheet.createRow(0);
            String[] encabezados = {"ID", "Nombre", "Dirección", "Teléfono",
                    "Correo", "Fecha de nacimiento", "Género", "Salario",
                    "Tipo de Licencia", "Fecha de certificación", "Horas de vuelo"};

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                CellStyle estilo = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                estilo.setFont(font);
                celda.setCellStyle(estilo);
            }

            int filaIndex = 1;
            for (Piloto piloto : pilotos) {
                Row fila = sheet.createRow(filaIndex++);
                String[] datos = piloto.formatoCSV();
                for (int i = 0; i < datos.length; i++) {
                    fila.createCell(i).setCellValue(datos[i]);
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }
            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }
            UtilGeneral.createAlert("Éxito", "Se creó con éxito el XLSX");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Hubo un error al crear el XLSX");
            System.err.println("Error al crear XLSX: " + e.getMessage());
        }
    }

    public void generarXLSXAsistentesVuelo(String path, List<AsistenteVuelo> asistentes) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos");

            Row filaEncabezado = sheet.createRow(0);
            String[] encabezados = {"ID", "Nombre", "Dirección", "Teléfono",
                    "Correo", "Fecha de nacimiento", "Género", "Salario",
                    "Número de idiomas", "Horas de vuelo asistidas"};

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                CellStyle estilo = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                estilo.setFont(font);
                celda.setCellStyle(estilo);
            }

            int filaIndex = 1;
            for (AsistenteVuelo asistente : asistentes) {
                Row fila = sheet.createRow(filaIndex++);
                String[] datos = asistente.formatoCSV();
                for (int i = 0; i < datos.length; i++) {
                    fila.createCell(i).setCellValue(datos[i]);
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }
            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }
            UtilGeneral.createAlert("Éxito", "Se creó con éxito el XLSX");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Hubo un error al crear el XLSX");
            System.err.println("Error al crear XLSX: " + e.getMessage());
        }
    }

    public void generarXLSXClientes(String path, List<Cliente> clientes) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos");

            Row filaEncabezado = sheet.createRow(0);
            String[] encabezados = {"Nombre", "Dirección", "Teléfono",
                    "Correo", "Fecha de nacimiento"};

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                CellStyle estilo = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                estilo.setFont(font);
                celda.setCellStyle(estilo);
            }

            int filaIndex = 1;
            for (Cliente cliente : clientes) {
                Row fila = sheet.createRow(filaIndex++);
                String[] datos = cliente.formatoCSV();
                for (int i = 0; i < datos.length; i++) {
                    fila.createCell(i).setCellValue(datos[i]);
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }
            UtilGeneral.createAlert("Éxito", "Se creó con éxito el XLSX");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Hubo un error al crear el XLSX");
            System.err.println("Error al crear XLSX: " + e.getMessage());
        }
    }


    public void generarXLSXAviones(String path, List<Avion> aviones) throws ArchivoException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos");

            Row filaEncabezado = sheet.createRow(0);
            String[] encabezados = {
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

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                CellStyle estilo = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                estilo.setFont(font);
                celda.setCellStyle(estilo);
            }

            int filaIndex = 1;
            for (Avion avion : aviones) {
                Row fila = sheet.createRow(filaIndex++);
                String[] datos = avion.formatoCSV();
                for (int i = 0; i < datos.length; i++) {
                    fila.createCell(i).setCellValue(datos[i]);
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            UtilGeneral.createAlert("Éxito", "Se creó con éxito el XLSX de aviones");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Hubo un error al crear el XLSX de aviones");
            System.err.println("Error al crear XLSX de aviones: " + e.getMessage());
        }
    }

    public void generarXLSXAerolineas(String path, List<Aerolinea> aerolineas) throws ArchivoException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos");

            Row filaEncabezado = sheet.createRow(0);
            String[] encabezados = {
                    "Nombre",
                    "IATA",
                    "ICAO",
                    "Callsign",
                    "Nacionalidad",
                    "Dirección",
                    "Sitio Oficial",
                    "Contacto Nombre",
                    "Contacto Teléfono"
            };

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                CellStyle estilo = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                estilo.setFont(font);
                celda.setCellStyle(estilo);
            }

            int filaIndex = 1;
            for (Aerolinea aerolinea : aerolineas) {
                Row fila = sheet.createRow(filaIndex++);
                String[] datos = aerolinea.formatoXLSX();
                for (int i = 0; i < datos.length; i++) {
                    // Removemos las etiquetas (ej. "Nombre: ") para solo dejar el valor en cada celda
                    String valor = datos[i].contains(": ") ? datos[i].split(": ", 2)[1] : datos[i];
                    fila.createCell(i).setCellValue(valor);
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            UtilGeneral.createAlert("Éxito", "Se creó con éxito el XLSX de aerolíneas");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Hubo un error al crear el XLSX de aerolíneas");
            System.err.println("Error al crear XLSX de aerolíneas: " + e.getMessage());
        }
    }

    public void generarXLSXBoletos(String path, List<Boleto> boletos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Boletos");

            Row filaEncabezado = sheet.createRow(0);
            String[] encabezados = {
                    "Código Vuelo",
                    "Asiento",
                    "Nombre Cliente",
                    "Clase",
                    "Precio"
                    // agrega aquí más columnas si tus boletos tienen más datos
            };

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                CellStyle estilo = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                estilo.setFont(font);
                celda.setCellStyle(estilo);
            }

            int filaIndex = 1;
            for (Boleto boleto : boletos) {
                Row fila = sheet.createRow(filaIndex++);
                String[] datos = boleto.formatoCSV();
                for (int i = 0; i < datos.length; i++) {
                    fila.createCell(i).setCellValue(datos[i]);
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            UtilGeneral.createAlert("Éxito", "Se creó con éxito el XLSX de boletos");

        } catch (IOException e) {
            UtilGeneral.createAlert("Error", "Hubo un error al crear el XLSX de boletos");
            System.err.println("Error al crear XLSX de boletos: " + e.getMessage());
        }
    }

    public void generarXLSXVuelos(String path, List<Vuelo> vuelos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vuelos");

            Row filaEncabezado = sheet.createRow(0);
            String[] encabezados = {
                    "Código Vuelo",
                    "Aerolinea",
                    "Avión",
                    "Num Pasajeros",
                    "Destino",
                    "Tiempo de Llegada",
                    "Origen",
                    "Tiempo de Salida"
            };

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                CellStyle estilo = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                estilo.setFont(font);
                celda.setCellStyle(estilo);
            }

            int filaIndex = 1;
            for (Vuelo vuelo : vuelos) {
                Row fila = sheet.createRow(filaIndex++);
                String[] datos = vuelo.formatoXLSX();
                for (int i = 0; i < datos.length; i++) {
                    fila.createCell(i).setCellValue(datos[i]);
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            UtilGeneral.mostrarAlerta("Éxito", "Se creo el archivo Excel exitosamente", Alert.AlertType.INFORMATION);
        } catch (IOException ioex) {
            UtilGeneral.mostrarAlerta("Error", String.format("%s\n%s", ioex.getMessage(), ioex.getCause()), Alert.AlertType.ERROR);
        }
    }

}
