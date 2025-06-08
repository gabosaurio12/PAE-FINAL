package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.empleados.Piloto;
import uniairlines.util.ArchivoUtil;

public class PilotoDAO {
    private final String RUTA_BASE = "Datos/";
    
    public List<Piloto> getTodosLosPilotos(String aerolinea) throws ArchivoException {
        String rutaPilotos = RUTA_BASE + aerolinea + "/pilotos.json";
        File archivo = new File(rutaPilotos);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        return ArchivoUtil.leerJson(rutaPilotos, new TypeToken<List<Piloto>>() {}.getType());
    }
    
    public void guardarPiloto(Piloto piloto, String aerolinea) throws ArchivoException {
        if (existe(piloto.getIdentificador(), aerolinea)) {
            throw new ArchivoException("Ya existe un avión con ese Identificador: " + piloto.getIdentificador());
        }
        List<Piloto> pilotos = getTodosLosPilotos(aerolinea);
        pilotos.add(piloto);
        String rutaPilotos = RUTA_BASE + aerolinea + "/pilotos.json";
        ArchivoUtil.escribirJson(rutaPilotos, pilotos);
    }
    
    public Piloto buscarPorId(String aerolinea, String idPiloto) throws ArchivoException {
        List<Piloto> pilotos = getTodosLosPilotos(aerolinea);
        Piloto pilotoBuscado = null;
        for (Piloto piloto : pilotos) {
            if (piloto.getIdentificador().equals(idPiloto)) {
                pilotoBuscado = piloto; 
            }
        }
        return pilotoBuscado;
    }
    
    public void editarPiloto(Piloto pilotoEditado, String aerolinea) throws ArchivoException {
        List<Piloto> pilotos = getTodosLosPilotos(aerolinea);
        List<Piloto> nuevosPilotos = new ArrayList<>();
        for (Piloto piloto : pilotos) {
            if (piloto.getIdentificador().equals(pilotoEditado.getIdentificador())) {
                nuevosPilotos.add(pilotoEditado);
            } else {
                nuevosPilotos.add(piloto);
            }
        }
        String ruta = RUTA_BASE + aerolinea + "/pilotos.json";
        ArchivoUtil.escribirJson(ruta, nuevosPilotos);
    }
    
    public void eliminarPiloto(Piloto pilotoEliminar, String aerolinea) throws ArchivoException {
        List<Piloto> pilotos = getTodosLosPilotos(aerolinea);
        List<Piloto> nuevosPilotos = new ArrayList<>();
        for (Piloto piloto : pilotos) {
            if (!piloto.getIdentificador().equals(pilotoEliminar.getIdentificador())) {
                nuevosPilotos.add(piloto);
            }
        }
        String ruta = RUTA_BASE + aerolinea + "/pilotos.json";
        ArchivoUtil.escribirJson(ruta, nuevosPilotos);
    }
    
    public boolean existe(String idPiloto, String aerolinea) throws ArchivoException {
        return buscarPorId(aerolinea, idPiloto) != null;
    }
}
