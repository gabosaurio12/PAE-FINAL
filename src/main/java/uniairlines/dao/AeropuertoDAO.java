package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Aerolinea;
import uniairlines.modelo.pojo.aerolinea.Aeropuerto;
import uniairlines.util.ArchivoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AeropuertoDAO {
    private static final String BASE_CARPETAS = "Datos/";


    public List<Aeropuerto> listar(String nombreAerolinea) throws ArchivoException {
        String ruta_aviones = String.format("%s%s/%s", BASE_CARPETAS, nombreAerolinea, "aeropuertos.json");
        File archivo = new File(ruta_aviones);
        if(!archivo.exists() || archivo.length() == 0){
            return new ArrayList<Aeropuerto>();
        }
        return ArchivoUtil.leerJson(ruta_aviones, new TypeToken<List<Aeropuerto>>() {}.getType());
    }
}
