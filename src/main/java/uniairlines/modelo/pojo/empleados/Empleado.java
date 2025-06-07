/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo.empleados;

/**
 *
 * @author abelh
 */
public class Empleado {
    private String identificador;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String fechaNacimiento;
    private String genero;
    private Double salario;

    public Empleado() {
    }

    public Empleado(String identificador, String nombre, String direccion, String telefono, String correo, String fechaNacimiento, String genero, Double salario) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.salario = salario;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
    
    public boolean validarCorreo() {
        String[] correoPartes = correo.split("@");
        return correoPartes.length == 2;
    }
    
    public boolean validarFecha() {
        String[] fecha = fechaNacimiento.split("/");
        return fecha[0].length() == 2 &&
                fecha[1].length() == 2 &&
                fecha[2].length() == 4;
    }
    
    public boolean validarDatos() {
        return !identificador.isBlank() &&
                !nombre.isBlank() &&
                !direccion.isBlank() &&
                !telefono.isBlank() &&
                !correo.isBlank() &&
                validarCorreo() &&
                !fechaNacimiento.isBlank() &&
                validarFecha() &&
                !genero.isBlank() &&
                salario != null;
    }
}
