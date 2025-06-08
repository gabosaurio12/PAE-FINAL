/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo.empleados;

/**
 *
 * @author abelh
 */
public class AsistenteVuelo extends Empleado {
    private Integer numIdiomas;
    private Integer numHorasAsistidas;

    public AsistenteVuelo() {
    }

    public AsistenteVuelo(Integer numIdiomas, Integer numHorasAsistidas, String identificador, String nombre, String direccion, String telefono, String correo, String fechaNacimiento, String genero, Double salario) {
        super(identificador, nombre, direccion, telefono, correo, fechaNacimiento, genero, salario);
        this.numIdiomas = numIdiomas;
        this.numHorasAsistidas = numHorasAsistidas;
    }

    public Integer getNumIdiomas() {
        return numIdiomas;
    }

    public void setNumIdiomas(Integer numIdiomas) {
        this.numIdiomas = numIdiomas;
    }

    public Integer getNumHorasAsistidas() {
        return numHorasAsistidas;
    }

    public void setNumHorasAsistidas(Integer numHorasAsistidas) {
        this.numHorasAsistidas = numHorasAsistidas;
    }

    public String formatoPDF() {
       return super.formatoPDF() +
               "Número de idiomas: " + numIdiomas + "\n" +
               "Horas de vuelo asistidas: " + numHorasAsistidas + "\n";
    }

    public String[] formatoCSV() {
        String[] csv = super.formatoCSV();

        String[] propios = {
                String.valueOf(numIdiomas),
                String.valueOf(numHorasAsistidas)
        };

        String[] resultado = new String[csv.length + propios.length];
        System.arraycopy(csv, 0, resultado, 0, csv.length);
        System.arraycopy(propios, 0, resultado, csv.length, propios.length);

        return resultado;
    }

    @Override
    public boolean validarDatos() {
        return super.validarDatos() &&
                super.validarFecha() &&
                numIdiomas != null &&
                numHorasAsistidas != null;
    }
}
