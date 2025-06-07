package uniairlines.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Usuario;
import uniairlines.util.ArchivoUtil;

public class UsuarioDAO {

    private static final String RUTA = "Datos/usuarios.json";
    

    public List<Usuario> listar() throws ArchivoException {
        File archivo = new File(RUTA);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        return ArchivoUtil.leerJson(RUTA, new TypeToken<List<Usuario>>() {}.getType());
    }

    public List<Usuario> listarPorAerolinea(String aerolinea) throws ArchivoException {
        return listar().stream()
                .filter(u -> u.getAerolinea().equalsIgnoreCase(aerolinea))
                .collect(Collectors.toList());
    }

    public void guardar(Usuario usuario) throws ArchivoException {
        List<Usuario> lista = listar();

        // Validar usuario único (por ejemplo, por nombre de usuario)
        for (Usuario u : lista) {
            if (u.getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
                throw new ArchivoException("Ya existe un usuario con el nombre: " + usuario.getUsuario());
            }
        }

        lista.add(usuario);
        ArchivoUtil.escribirJson(RUTA, lista);
    }

    public void actualizar(Usuario usuarioActualizado) throws ArchivoException {
        List<Usuario> lista = listar();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Usuario u = lista.get(i);
            if (u.getUsuario().equalsIgnoreCase(usuarioActualizado.getUsuario())) {
                lista.set(i, usuarioActualizado);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new ArchivoException("No se encontró el usuario para actualizar.");
        }

        ArchivoUtil.escribirJson(RUTA, lista);
    }

    public void eliminar(Usuario usuario) throws ArchivoException {
        List<Usuario> lista = listar();
        boolean eliminado = lista.removeIf(u -> u.getUsuario().equalsIgnoreCase(usuario.getUsuario()));

        if (!eliminado) {
            throw new ArchivoException("No se encontró el usuario para eliminar.");
        }

        ArchivoUtil.escribirJson(RUTA, lista);
    }
    
    public void eliminarUsuariosPorAerolinea(String nombreAerolinea) throws ArchivoException {
    List<Usuario> lista = listar();
    boolean eliminado = lista.removeIf(u -> u.getAerolinea() != null &&
                                             u.getAerolinea().equalsIgnoreCase(nombreAerolinea));

    if (!eliminado) {
        throw new ArchivoException("No se encontraron usuarios asociados a la aerolínea: " + nombreAerolinea);
    }

    ArchivoUtil.escribirJson(RUTA, lista);
}
    
      public static Usuario verificarUsuario(String username, String password) {
        try {
            File archivo = new File(RUTA);
            if (!archivo.exists()) return null;

            String contenido = ArchivoUtil.leerArchivo(RUTA);
            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuarios = new Gson().fromJson(contenido, listType);

            for (Usuario u : usuarios) {
                if (u.getUsuario().equals(username) && u.getContrasena().equals(password)) {
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      
      public static void actualizarNombreAerolineaEnUsuarios(String nombreAntiguo, String nombreNuevo) throws ArchivoException {
    String rutaUsuarios = RUTA; // Ajusta esta ruta si tienes una constante

    // Leer lista de usuarios
    List<Usuario> usuarios = ArchivoUtil.leerJson(rutaUsuarios, new TypeToken<List<Usuario>>(){}.getType());

    // Actualizar usuarios relacionados
    for (Usuario u : usuarios) {
        if (u.getAerolinea() != null && u.getAerolinea().equals(nombreAntiguo)) {
            u.setAerolinea(nombreNuevo);
        }
    }

    // Guardar cambios
    ArchivoUtil.escribirJson(rutaUsuarios, usuarios);
}
      
      public boolean tieneUsuarios(String nombreAerolinea) throws ArchivoException {
    List<Usuario> lista = listar();

    for (Usuario u : lista) {
        if (u.getAerolinea() != null && u.getAerolinea().equalsIgnoreCase(nombreAerolinea)) {
            return true;
        }
    }

    return false;
}

}

