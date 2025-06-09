/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo;

import java.util.List;

import uniairlines.modelo.pojo.aerolinea.Aeropuerto;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;

/**
 *
 * @author abelh
 */
public class Vuelo {
    private String codigoVuelo;
    private String aerolinea;
    private String codigoAvion;
    private Integer numPasajeros;
    private Aeropuerto salida;
    private Aeropuerto destino;
    private String fechaSalida;
    private String fechaLlegada;
    private Integer minutosDeVueloEstimados;
    private Integer puertaSalida;
    private Integer puertaLlegada;
    private Double precioTurista;
    private Double precioNegocios;
    private Double precioPrimeraClase;
    private Piloto piloto;
    private Piloto copiloto;
    private List<AsistenteVuelo> asistentes;

    public Vuelo() {
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getCodigoAvion() {
        return codigoAvion;
    }

    public void setCodigoAvion(String codigoAvion) {
        this.codigoAvion = codigoAvion;
    }

    public Integer getNumPasajeros() {
        return numPasajeros;
    }

    public void setNumPasajeros(Integer numPasajeros) {
        this.numPasajeros = numPasajeros;
    }

    public Aeropuerto getSalida() {
        return salida;
    }

    public void setSalida(Aeropuerto salida) {
        this.salida = salida;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public Integer getMinutosDeVueloEstimados() {
        return minutosDeVueloEstimados;
    }

    public void setMinutosDeVueloEstimados(Integer minutosDeVueloEstimados) {
        this.minutosDeVueloEstimados = minutosDeVueloEstimados;
    }

    public Integer getPuertaSalida() {
        return puertaSalida;
    }

    public void setPuertaSalida(Integer puertaSalida) {
        this.puertaSalida = puertaSalida;
    }

    public Integer getPuertaLlegada() {
        return puertaLlegada;
    }

    public void setPuertaLlegada(Integer puertaLlegada) {
        this.puertaLlegada = puertaLlegada;
    }

    public Double getPrecioTurista() {
        return precioTurista;
    }

    public void setPrecioTurista(Double precioTurista) {
        this.precioTurista = precioTurista;
    }

    public Double getPrecioNegocios() {
        return precioNegocios;
    }

    public void setPrecioNegocios(Double precioNegocios) {
        this.precioNegocios = precioNegocios;
    }

    public Double getPrecioPrimeraClase() {
        return precioPrimeraClase;
    }

    public void setPrecioPrimeraClase(Double precioPrimeraClase) {
        this.precioPrimeraClase = precioPrimeraClase;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Piloto getCopiloto() {
        return copiloto;
    }

    public void setCopiloto(Piloto copiloto) {
        this.copiloto = copiloto;
    }

    public List<AsistenteVuelo> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<AsistenteVuelo> asistentes) {
        this.asistentes = asistentes;
    }

    public String[] formatoXLSX() {
        return new String[] {
                this.getCodigoVuelo(),
                this.getAerolinea(),
                this.getCodigoAvion(),
                String.valueOf(this.getNumPasajeros()),
                this.getDestino().toString(),
                this.getFechaLlegada(),
                this.getSalida().toString(),
                this.getFechaSalida(),
        };
    }
}
