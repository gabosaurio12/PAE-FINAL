/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo.boleto;

/**
 *
 * @author abelh
 */
public class Cliente {
    private String nombre;
    private String aerolinea;
    private String apellidoP;
    private String apellidoM;
    private String nacionalidad;
    private String fechaNacimiento;

    public Cliente() {
    }

    public Cliente(String nombre, String aerolinea, String apellidoP, String apellidoM, String nacionalidad, String fechaNacimiento) {
        this.nombre = nombre;
        this.aerolinea = aerolinea;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAerolinea() { return aerolinea; }

    public void setAerolinea(String aerolinea) { this.aerolinea = aerolinea; }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
}
