package uniairlines.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.boleto.Boleto;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BoletoDAO {

    private final String RUTA_BOLETOS = "Datos/boletos.json";
    private final Gson gson = new Gson();

    public List<Boleto> recuperarBoletos() throws ArchivoException {
        try (JsonReader reader = new JsonReader(new FileReader(RUTA_BOLETOS))) {
            Type tipoLista = new TypeToken<List<Boleto>>() {}.getType();
            List<Boleto> boletos = gson.fromJson(reader, tipoLista);
            return boletos != null ? boletos : new ArrayList<>();
        } catch (IOException e) {
            throw new ArchivoException("No se pudo leer el archivo de boletos", e);
        }
    }

    public void guardarBoletos(List<Boleto> boletos) throws ArchivoException {
        try (FileWriter writer = new FileWriter(RUTA_BOLETOS)) {
            gson.toJson(boletos, writer);
        } catch (IOException e) {
            throw new ArchivoException("No se pudo guardar el archivo de boletos", e);
        }
    }

    public void agregarBoleto(Boleto nuevo) throws ArchivoException {
        List<Boleto> boletos = recuperarBoletos();
        boletos.add(nuevo);
        guardarBoletos(boletos);
    }

    public List<Boleto> filtrarPorVuelo(String codigoVuelo) throws ArchivoException {
        List<Boleto> resultado = new ArrayList<>();
        for (Boleto b : recuperarBoletos()) {
            if (b.getVuelo().getCodigoVuelo().equalsIgnoreCase(codigoVuelo)) {
                resultado.add(b);
            }
        }
        return resultado;
    }
}
