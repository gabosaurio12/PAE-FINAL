package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.util.ArchivoUtil;

public class AsistenteVueloDAO {
    private final String RUTA_BASE = "Datos/";
    
    public List<AsistenteVuelo> getTodosLosAsistentesVuelo(String aerolinea) throws ArchivoException {
        String rutaAsistentes = RUTA_BASE + aerolinea + "/asistentes_vuelo.json";
        File archivo = new File(rutaAsistentes);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        List<AsistenteVuelo> pilotos = ArchivoUtil.leerJson(rutaAsistentes, new TypeToken<List<AsistenteVuelo>>() {}.getType());
        return pilotos;
    }
    
    public void guardarAsistenteVuelo(AsistenteVuelo asistente, String aerolinea) throws ArchivoException {
        if (existe(asistente.getIdentificador(), aerolinea)) {
            throw new ArchivoException("Ya existe un avión con ese Identificador: " + asistente.getIdentificador());
        }
        List<AsistenteVuelo> asistentes = getTodosLosAsistentesVuelo(aerolinea);
        asistentes.add(asistente);
        String rutaAsistentes = RUTA_BASE + aerolinea + "/asistentes_vuelo.json";
        ArchivoUtil.escribirJson(rutaAsistentes, asistentes);
    }
    
    public AsistenteVuelo buscarPorId(String aerolinea, String idAsistente) throws ArchivoException {
        List<AsistenteVuelo> asistentes = getTodosLosAsistentesVuelo(aerolinea);
        AsistenteVuelo asistenteBuscado = null;
        for (AsistenteVuelo asistente : asistentes) {
            if (asistente.getIdentificador().equals(idAsistente)) {
                asistenteBuscado = asistente; 
            }
        }
        return asistenteBuscado;
    }
    
    public void editarAsistenteVuelo(AsistenteVuelo asistenteEditado, String aerolinea) throws ArchivoException {
        List<AsistenteVuelo> asistentes = getTodosLosAsistentesVuelo(aerolinea);
        List<AsistenteVuelo> nuevosAsistentes = new ArrayList<>();
        for (AsistenteVuelo asistente : asistentes) {
            if (asistente.getIdentificador().equals(asistenteEditado.getIdentificador())) {
                nuevosAsistentes.add(asistenteEditado);
            } else {
                nuevosAsistentes.add(asistente);
            }
        }
        String ruta = RUTA_BASE + aerolinea + "/asistentes_vuelo.json";
        ArchivoUtil.escribirJson(ruta, nuevosAsistentes);
    }
    
    public void eliminarAsistenteVuelo(AsistenteVuelo asistenteEliminar, String aerolinea) throws ArchivoException {
        List<AsistenteVuelo> asistentes = getTodosLosAsistentesVuelo(aerolinea);
        List<AsistenteVuelo> nuevosAsistentes = new ArrayList<>();
        for (AsistenteVuelo asistente : asistentes) {
            if (!asistente.getIdentificador().equals(asistenteEliminar.getIdentificador())) {
                nuevosAsistentes.add(asistente);
            }
        }
        String ruta = RUTA_BASE + aerolinea + "/asistentes_vuelo.json";
        ArchivoUtil.escribirJson(ruta, nuevosAsistentes);
    }
    
    public boolean existe(String idAsistente, String aerolinea) throws ArchivoException {
        return buscarPorId(aerolinea, idAsistente) != null;
    }
}
