package uniairlines.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
}
