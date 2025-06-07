/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo.empleados;

/**
 *
 * @author abelh
 */
public class Piloto extends Empleado {
    private LicenciaAeronautica tipoLicencia;
    private String fechaCertificacion;
    private Integer numHorasVuelo;

    public Piloto() {
    }

    public Piloto(LicenciaAeronautica tipoLicencia, String fechaCertificacion, Integer numHorasVuelo, String identificador, String nombre, String direccion, String telefono, String correo, String fechaNacimiento, String genero, Double salario) {
        super(identificador, nombre, direccion, telefono, correo, fechaNacimiento, genero, salario);
        this.tipoLicencia = tipoLicencia;
        this.fechaCertificacion = fechaCertificacion;
        this.numHorasVuelo = numHorasVuelo;
    }
    
    

    public Piloto(LicenciaAeronautica tipoLicencia, String fechaCertificacion, Integer numHorasVuelo) {
        this.tipoLicencia = tipoLicencia;
        this.fechaCertificacion = fechaCertificacion;
        this.numHorasVuelo = numHorasVuelo;
    }

    public LicenciaAeronautica getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String nombreDeLicencia) {
        this.tipoLicencia = LicenciaAeronautica.valueOf(nombreDeLicencia);
    }

    public String getFechaCertificacion() {
        return fechaCertificacion;
    }

    public void setFechaCertificacion(String fechaCertificacion) {
        this.fechaCertificacion = fechaCertificacion;
    }

    public Integer getNumHorasVuelo() {
        return numHorasVuelo;
    }

    public void setNumHorasVuelo(Integer numHorasVuelo) {
        this.numHorasVuelo = numHorasVuelo;
    }
    
    @Override
    public boolean validarFecha() {
        String[] fecha = fechaCertificacion.split("/");
        return fecha[0].length() == 2 &&
                fecha[1].length() == 2 &&
                fecha[2].length() == 4;
    }
    
    @Override
    public boolean validarDatos() {
        return super.validarDatos() &&
                tipoLicencia != null &&
                !fechaCertificacion.isBlank() &&
                validarFecha() &&
                numHorasVuelo !=  null;
    }
}
