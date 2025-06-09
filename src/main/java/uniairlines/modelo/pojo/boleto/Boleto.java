/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo.boleto;

import uniairlines.modelo.pojo.Vuelo;

/**
 *
 * @author abelh
 */

public class Boleto {
    //Todos los datos del boleto pueden extraerse del vuelo y el cliente asociados
    private Vuelo vuelo;
    private Cliente cliente;
    private ClaseBoleto clase;
    private int cantidad;
    public Boleto() {
    }

    public Boleto(Vuelo vuelo, Cliente cliente, ClaseBoleto clase, int cantidad) {
        this.vuelo = vuelo;
        this.cliente = cliente;
        this.clase = clase;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClaseBoleto getClase() {
        return clase;
    }


    public void setClase(String clase) {
        this.clase = ClaseBoleto.valueOf(clase);
    }
}
