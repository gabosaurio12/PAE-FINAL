package uniairlines.modelo.pojo.boleto;

import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.boleto.Cliente;  // Importa tu clase Cliente
import uniairlines.modelo.pojo.boleto.ClaseBoleto; // Importa tu enum ClaseBoleto

public class Boleto {
    // Todos los datos del boleto pueden extraerse del vuelo y el cliente asociados
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

    // Sobrecarga para aceptar tanto String como enum directamente
    public void setClase(ClaseBoleto clase) {
        this.clase = clase;
    }

    public void setClase(String clase) {
        this.clase = ClaseBoleto.valueOf(clase);
    }

    /**
     * Método que regresa los datos del boleto como arreglo de Strings para exportar a CSV o XLSX.
     */
    public String[] formatoCSV() {
        return new String[] {
                vuelo != null ? vuelo.getCodigoVuelo() : "",
                cliente != null ? cliente.getNombre() : "",
                clase != null ? clase.name() : "",
                String.valueOf(cantidad)
        };
    }
}
