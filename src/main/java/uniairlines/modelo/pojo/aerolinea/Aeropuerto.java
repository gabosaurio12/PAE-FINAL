/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo.aerolinea;

/**
 *
 * @author abelh
 */
public class Aeropuerto {
    private String pais;
    private String ciudad;
    private Integer numPuertas; //Necesario para determinar la puerta del boleto

    public Aeropuerto() {
    }

    public Aeropuerto(String pais, String ciudad, Integer numPuertas) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.numPuertas = numPuertas;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getNumPuertas() {
        return numPuertas;
    }

    public void setNumPuertas(Integer numPuertas) {
        this.numPuertas = numPuertas;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", ciudad, pais);
    }
}