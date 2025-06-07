package uniairlines.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import uniairlines.excepcion.ArchivoException;

public class ArchivoUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Leer archivo de texto completo como String
    public static String leerArchivo(String ruta) throws ArchivoException {
        try {
            return new String(Files.readAllBytes(Paths.get(ruta)));
        } catch (IOException e) {
            throw new ArchivoException("No se pudo leer el archivo: " + ruta, e);
        }
    }

    // Escribir texto completo en un archivo
    public static void escribirArchivo(String ruta, String contenido) throws ArchivoException {
        try {
            File archivo = new File(ruta);
            archivo.getParentFile().mkdirs(); // Crea carpetas si no existen
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(contenido);
            }
        } catch (IOException e) {
            throw new ArchivoException("No se pudo escribir el archivo: " + ruta, e);
        }
    }

    // Escribir cualquier objeto como JSON
    public static void escribirJson(String ruta, Object objeto) throws ArchivoException {
        String json = gson.toJson(objeto);
        escribirArchivo(ruta, json);
    }

    // Leer JSON desde un archivo y convertirlo al tipo especificado
    public static <T> T leerJson(String ruta, Type tipo) throws ArchivoException {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists() || archivo.length() == 0) {
                return gson.fromJson("[]", tipo); // Lista vacía si archivo no existe o está vacío
            }
            String contenido = leerArchivo(ruta);
            return gson.fromJson(contenido, tipo);
        } catch (JsonSyntaxException | ArchivoException e) {
            throw new ArchivoException("No se pudo parsear el archivo JSON: " + ruta, e);
        }
    }

    // Crear archivo JSON con contenido inicial "[]", lanzando ArchivoException
    public static void crearArchivoJson(String rutaDirectorio, String nombreArchivo) throws ArchivoException {
        File directorio = new File(rutaDirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs(); // crea carpetas si no existen
        }

        File archivo = new File(directorio, nombreArchivo);
        if (!archivo.exists()) {
            try (FileWriter writer = new FileWriter(archivo)) {
                writer.write("[]"); // escribe una lista vacía JSON por defecto
            } catch (IOException e) {
                throw new ArchivoException("No se pudo crear el archivo JSON: " + archivo.getAbsolutePath(), e);
            }
        }
    }
}

