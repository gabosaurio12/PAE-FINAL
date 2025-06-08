package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.util.ArchivoUtil;

public class ClienteDAO {

    private static final String RUTA = "Datos/clientes.json";

    public List<Cliente> listar() throws ArchivoException {
        File archivo = new File(RUTA);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        Type tipoLista = new TypeToken<List<Cliente>>() {}.getType();
        return ArchivoUtil.leerJson(RUTA, tipoLista);
    }

    public void guardar(Cliente nuevoCliente) throws ArchivoException {
        List<Cliente> lista = listar();

        // Puedes validar duplicados por nombre + apellidos si deseas
        for (Cliente c : lista) {
            if (c.getNombre().equalsIgnoreCase(nuevoCliente.getNombre())
                    && c.getApellidoP().equalsIgnoreCase(nuevoCliente.getApellidoP())
                    && c.getApellidoM().equalsIgnoreCase(nuevoCliente.getApellidoM())) {
                throw new ArchivoException("El cliente ya existe: " + nuevoCliente.getNombre());
            }
        }

        lista.add(nuevoCliente);
        ArchivoUtil.escribirJson(RUTA, lista);
    }

    public void actualizar(Cliente clienteActualizado, String nombreOriginal, String apellidoPOriginal) throws ArchivoException {
        List<Cliente> lista = listar();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Cliente c = lista.get(i);
            if (c.getNombre().equalsIgnoreCase(nombreOriginal)
                    && c.getApellidoP().equalsIgnoreCase(apellidoPOriginal)) {
                lista.set(i, clienteActualizado);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new ArchivoException("No se encontró el cliente para actualizar.");
        }

        ArchivoUtil.escribirJson(RUTA, lista);
    }

    public void eliminar(Cliente cliente) throws ArchivoException {
        List<Cliente> lista = listar();
        boolean eliminado = lista.removeIf(c ->
                c.getNombre().equalsIgnoreCase(cliente.getNombre())
                        && c.getApellidoP().equalsIgnoreCase(cliente.getApellidoP())
        );

        if (!eliminado) {
            throw new ArchivoException("No se encontró el cliente para eliminar.");
        }

        ArchivoUtil.escribirJson(RUTA, lista);
    }

    public List<Cliente> buscarPorNacionalidad(String nacionalidad) throws ArchivoException {
        return listar().stream()
                .filter(c -> c.getNacionalidad().equalsIgnoreCase(nacionalidad))
                .collect(Collectors.toList());
    }
}

