/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.util;

import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Usuario;

/**
 *
 * @author cuent
 */
public class estructuraUtil {
    
    public static void Arranque() throws ArchivoException{
        File datosDir = new File("Datos");
        if (!datosDir.exists()) {
            datosDir.mkdirs();
        } else {
        }
        
        File usuariosFile = new File("Datos/usuarios.json");
         
              
        if (!usuariosFile.exists()) {
            try {
                List<Usuario> usuarios = new ArrayList<>();

                usuarios.add(new Usuario("master", "master123", "empleado", "Aero1", "Empleado Prueba"));
                String json = new Gson().toJson(usuarios);
                ArchivoUtil.escribirArchivo("Datos/usuarios.json", json);
            } catch (ArchivoException ex) {
                throw new ArchivoException("No se pudo escribir el archivo: " + "hola", ex);
            }
        }
    }
}
