package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.util.ArchivoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VueloDAO {
    private static final String BASE_CARPETAS = "Datos/";
    private static final String RUTA_ARCHIVO_VUELOS = "Datos/vuelos.json";

    public VueloDAO() {

    }

    public List<Vuelo> recuperarVuelos() throws ArchivoException {
        File archivoVuelos = new File(RUTA_ARCHIVO_VUELOS);
        if(!archivoVuelos.exists() || archivoVuelos.length() == 0) {
            return new ArrayList<Vuelo>();
        }
        return ArchivoUtil.leerJson(RUTA_ARCHIVO_VUELOS, new TypeToken<List<Vuelo>>() {}.getType());
    }
}
