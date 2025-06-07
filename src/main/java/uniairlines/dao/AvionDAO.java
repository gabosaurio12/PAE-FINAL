package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Asiento;
import uniairlines.modelo.Avion;
import uniairlines.util.ArchivoUtil;

public class AvionDAO {

    private static final String BASE_CARPETAS = "Datos/";

    public List<Avion> listar(String nombreAerolinea) throws ArchivoException {
        String rutaAviones = BASE_CARPETAS + nombreAerolinea + "/aviones.json";
        File archivo = new File(rutaAviones);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        return ArchivoUtil.leerJson(rutaAviones, new TypeToken<List<Avion>>() {}.getType());
    }

    public void agregar(Avion avion, String nombreAerolinea) throws ArchivoException {
        if (existe(avion.getId(), nombreAerolinea)) {
            throw new ArchivoException("Ya existe un avión con el ID: " + avion.getId());
        }
        List<Avion> lista = listar(nombreAerolinea);
        lista.add(avion);
        String rutaAviones = BASE_CARPETAS + nombreAerolinea + "/aviones.json";
        ArchivoUtil.escribirJson(rutaAviones, lista);
    }

    public void modificar(Avion avionActualizado, String nombreAerolinea) throws ArchivoException {
       List<Avion> lista = listar(nombreAerolinea);
       boolean encontrado = false;

       for (int i = 0; i < lista.size(); i++) {
           Avion a = lista.get(i);
           if (a.getId().equals(avionActualizado.getId())) {
               // Guardar los asientos actuales
               List<Asiento> asientosOriginales = a.getAsientos();

               // Actualizar campos del avión
               a.setModelo(avionActualizado.getModelo());
               a.setTipo(avionActualizado.getTipo());
               a.setCapacidadTotal(avionActualizado.getCapacidadTotal());
               a.setEstado(avionActualizado.getEstado());
               a.setPeso((int) avionActualizado.getPeso());
               a.setFilas(avionActualizado.getFilas());
               a.setAsientosPorFila(avionActualizado.getAsientosPorFila());

               // Restaurar los asientos
               a.setAsientos(asientosOriginales);

               encontrado = true;
               break;
           }
       }

       if (!encontrado) {
           throw new ArchivoException("No se encontró el avión para modificar.");
       }

       String rutaAviones = BASE_CARPETAS + nombreAerolinea + "/aviones.json";
       ArchivoUtil.escribirJson(rutaAviones, lista);
    }

    public void modificar(Avion avionActualizado, List<Asiento> asientosNuevos, String nombreAerolinea) throws ArchivoException {
        List<Avion> lista = listar(nombreAerolinea);
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Avion a = lista.get(i);
            if (a.getId().equals(avionActualizado.getId())) {
                // Actualizar todos los campos
                a.setModelo(avionActualizado.getModelo());
                a.setTipo(avionActualizado.getTipo());
                a.setCapacidadTotal(avionActualizado.getCapacidadTotal());
                a.setEstado(avionActualizado.getEstado());
                a.setPeso((int) avionActualizado.getPeso());
                a.setFilas(avionActualizado.getFilas());
                a.setAsientosPorFila(avionActualizado.getAsientosPorFila());

                // Actualizar los asientos con la lista nueva
                a.setAsientos(asientosNuevos);

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new ArchivoException("No se encontró el avión para modificar.");
        }

        String rutaAviones = BASE_CARPETAS + nombreAerolinea + "/aviones.json";
        ArchivoUtil.escribirJson(rutaAviones, lista);
    }


    public void eliminar(String nombreAerolinea, String idAvion) throws ArchivoException {
        List<Avion> lista = listar(nombreAerolinea);
        boolean eliminado = lista.removeIf(a -> a.getId().equals(idAvion));

        if (!eliminado) {
            throw new ArchivoException("No se encontró el avión para eliminar.");
        }

        String rutaAviones = BASE_CARPETAS + nombreAerolinea + "/aviones.json";
        ArchivoUtil.escribirJson(rutaAviones, lista);
    }

    public Avion buscarPorId(String nombreAerolinea, String idAvion) throws ArchivoException {
        List<Avion> lista = listar(nombreAerolinea);
        for (Avion a : lista) {
            if (a.getId().equals(idAvion)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Verifica si un avión con ese ID ya existe en la aerolínea
     */
    public boolean existe(String idAvion, String nombreAerolinea) throws ArchivoException {
        return buscarPorId(nombreAerolinea, idAvion) != null;
    }
}
