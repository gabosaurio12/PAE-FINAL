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

    public String formatoPDF() {
        return super.formatoPDF() +
                "Tipo de licencia: " + tipoLicencia + "\n" +
                "Fecha de certificación: " + fechaCertificacion + "\n" +
                "Horas de vuelo: " + numHorasVuelo + "\n";
    }

    @Override
    public String[] formatoCSV() {
        String[] csv = super.formatoCSV();

        String[] propios = {
                String.valueOf(tipoLicencia),
                fechaCertificacion,
                String.valueOf(numHorasVuelo)
        };

        String[] resultado = new String[csv.length + propios.length];
        System.arraycopy(csv, 0, resultado, 0, csv.length);
        System.arraycopy(propios, 0, resultado, csv.length, propios.length);

        return resultado;
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

    @Override
    public String toString() {
        return getNombre();
    }
}
