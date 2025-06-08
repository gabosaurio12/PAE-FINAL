package uniairlines.dao;

import com.google.gson.reflect.TypeToken;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.boleto.Cliente;
import uniairlines.util.ArchivoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final String BASE_CARPETAS = "Datos/";

    public List<Cliente> listar(String nombreAerolinea) throws ArchivoException {
        String rutaClientes = BASE_CARPETAS + nombreAerolinea + "/clientes.json";
        File archivo = new File(rutaClientes);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        return ArchivoUtil.leerJson(rutaClientes, new TypeToken<List<Cliente>>() {}.getType());
    }

    public void agregar(Cliente cliente, String nombreAerolinea) throws ArchivoException {
        if (existe(cliente.getNombre(), nombreAerolinea)) {
            throw new ArchivoException("Ya existe un cliente con el nombre: " + cliente.getNombre());
        }
        List<Cliente> lista = listar(nombreAerolinea);
        lista.add(cliente);
        String rutaClientes = BASE_CARPETAS + nombreAerolinea + "/clientes.json";
        ArchivoUtil.escribirJson(rutaClientes, lista);
    }

    public void modificar(Cliente clienteActualizado, String nombreAerolinea) throws ArchivoException {
        List<Cliente> lista = listar(nombreAerolinea);
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Cliente c = lista.get(i);
            if (c.getNombre().equals(clienteActualizado.getNombre())) {
                c.setApellidoP(clienteActualizado.getApellidoP());
                c.setApellidoM(clienteActualizado.getApellidoM());
                c.setNacionalidad(clienteActualizado.getNacionalidad());
                c.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new ArchivoException("Cliente no encontrado: " + clienteActualizado.getNombre());
        }

        String rutaClientes = BASE_CARPETAS + nombreAerolinea + "/clientes.json";
        ArchivoUtil.escribirJson(rutaClientes, lista);
    }

    public void eliminar(String nombreCliente, String nombreAerolinea) throws ArchivoException {
        List<Cliente> lista = listar(nombreAerolinea);
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Cliente c = lista.get(i);
            if (c.getNombre().equals(nombreCliente)) {
                lista.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new ArchivoException("Cliente no encontrado: " + nombreCliente);
        }

        String rutaClientes = BASE_CARPETAS + nombreAerolinea + "/clientes.json";
        ArchivoUtil.escribirJson(rutaClientes, lista);
    }

    public boolean existe(String nombreCliente, String nombreAerolinea) throws ArchivoException {
        List<Cliente> lista = listar(nombreAerolinea);
        for (Cliente c : lista) {
            if (c.getNombre().equals(nombreCliente)) {
                return true;
            }
        }
        return false;
    }
}
