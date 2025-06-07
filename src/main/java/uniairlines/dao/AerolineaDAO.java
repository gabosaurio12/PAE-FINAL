package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static uniairlines.dao.UsuarioDAO.actualizarNombreAerolineaEnUsuarios;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.Usuario;
import uniairlines.util.ArchivoUtil;

public class AerolineaDAO {

    private static final String RUTA_GENERAL = "Datos/aerolineas.json";
    private static final String BASE_CARPETAS = "Datos/";
    private static final String RUTA_USUARIOS = "Datos/usuarios.json";

  public List<Aerolinea> listar() throws ArchivoException {
    File archivo = new File(RUTA_GENERAL);
    if (!archivo.exists() || archivo.length() == 0) {
        return new ArrayList<>();
    }
    return ArchivoUtil.leerJson(RUTA_GENERAL, new TypeToken<List<Aerolinea>>() {}.getType());
}


    public void guardar(Aerolinea aerolinea) throws ArchivoException {
        List<Aerolinea> lista = listar();

        for (Aerolinea a : lista) {
            if (a.getIATA().equalsIgnoreCase(aerolinea.getIATA())) {
                throw new ArchivoException("Ya existe una aerolínea con el IATA: " + aerolinea.getIATA());
            }
        }

        lista.add(aerolinea);
        ArchivoUtil.escribirJson(RUTA_GENERAL, lista);

        crearCarpetaAerolinea(aerolinea.getNombre());
    }

   public void actualizar(Aerolinea aerolineaActualizada) throws ArchivoException {
    List<Aerolinea> lista = listar();
    boolean encontrado = false;
    String nombreAntiguo = null;

    for (int i = 0; i < lista.size(); i++) {
        Aerolinea a = lista.get(i);
        if (a.getIATA().equalsIgnoreCase(aerolineaActualizada.getIATA())) {
            nombreAntiguo = a.getNombre();
            lista.set(i, aerolineaActualizada);
            encontrado = true;
            break;
        }
    }

    if (!encontrado) {
        throw new ArchivoException("No se encontró la aerolínea para actualizar.");
    }

    // Guardar la lista de aerolíneas actualizada
    ArchivoUtil.escribirJson(RUTA_GENERAL, lista);

    // Renombrar carpeta si el nombre cambió
    if (nombreAntiguo != null && !nombreAntiguo.equals(aerolineaActualizada.getNombre())) {
        renombrarCarpetaAerolinea(nombreAntiguo, aerolineaActualizada.getNombre());
        
        // >>> NUEVO: Actualizar usuarios que usan esta aerolínea
        actualizarNombreAerolineaEnUsuarios(nombreAntiguo, aerolineaActualizada.getNombre());
    } else {
        crearCarpetaAerolinea(aerolineaActualizada.getNombre());
    }
}


    public void eliminar(Aerolinea aerolinea) throws ArchivoException {
    List<Aerolinea> lista = listar();
    boolean eliminado = lista.removeIf(a -> a.getIATA().equalsIgnoreCase(aerolinea.getIATA()));

    if (!eliminado) {
        throw new ArchivoException("No se encontró la aerolínea para eliminar.");
    }

    ArchivoUtil.escribirJson(RUTA_GENERAL, lista);
    eliminarCarpetaAerolinea(aerolinea.getNombre());
 
    
}


    private void crearCarpetaAerolinea(String nombreAerolinea) throws ArchivoException {
    String ruta = BASE_CARPETAS + nombreAerolinea;
    File carpeta = new File(ruta);
    if (!carpeta.exists()) {
        carpeta.mkdirs();
    }

    ArchivoUtil.crearArchivoJson(ruta, "vuelos.json");
    ArchivoUtil.crearArchivoJson(ruta, "empleados.json");           // Contiene administrativos
    ArchivoUtil.crearArchivoJson(ruta, "pilotos.json");             // Contiene solo pilotos
    ArchivoUtil.crearArchivoJson(ruta, "asistentes_vuelo.json");    // Contiene solo asistentes de vuelo
    ArchivoUtil.crearArchivoJson(ruta, "clientes.json");
    ArchivoUtil.crearArchivoJson(ruta, "aviones.json");
    ArchivoUtil.crearArchivoJson(ruta, "boletos.json");
    ArchivoUtil.crearArchivoJson(ruta, "aeropuertos.json");
}

private void renombrarCarpetaAerolinea(String nombreAntiguo, String nombreNuevo) throws ArchivoException {
    File carpetaAntigua = new File(BASE_CARPETAS + nombreAntiguo);
    File carpetaNueva = new File(BASE_CARPETAS + nombreNuevo);

    if (carpetaAntigua.exists() && !carpetaNueva.exists()) {
        boolean renombrado = carpetaAntigua.renameTo(carpetaNueva);
        if (!renombrado) {
            throw new ArchivoException("No se pudo renombrar la carpeta de la aerolínea de '" 
                                        + nombreAntiguo + "' a '" + nombreNuevo + "'");
        }
    }
}

    public static void eliminarCarpetaAerolinea(String nombreAerolinea) {
        File carpeta = new File(BASE_CARPETAS + nombreAerolinea);
        if (carpeta.exists()) {
            eliminarRecursivamente(carpeta);
        }
    }

    private static void eliminarRecursivamente(File archivo) {
        if (archivo.isDirectory()) {
            for (File f : archivo.listFiles()) {
                eliminarRecursivamente(f);
            }
        }
        archivo.delete();
    }
    


}

