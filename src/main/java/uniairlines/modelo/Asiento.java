package uniairlines.modelo;

/**
 *
 * @author cuent
 */
public class Asiento {
    private int fila;            // Número de fila
    private String columna;      // Letra de asiento (ej. A, B, C)
    private String clase;        // Turista, Ejecutivo, VIP
    private String estado;       // Libre, Ocupado, Seleccionado
    private double precio;       // Precio del asiento
    private String nombreCliente; // Nombre del cliente asignado al asiento

    public Asiento() {}

    public Asiento(String prueba){
        this.fila = 1;
        this.columna = "A";
        this.estado = "Ejecutivo";
        this.precio = 0;
        this.nombreCliente = null;
    }

    public Asiento(int fila, String columna, String clase, String estado, double precio) {
        this.fila = fila;
        this.columna = columna;
        this.clase = clase;
        this.estado = estado;
        this.precio = precio;
        this.nombreCliente = null;
    }

    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }

    public String getColumna() { return columna; }
    public void setColumna(String columna) { this.columna = columna; }

    public String getClase() { return clase; }
    public void setClase(String clase) { this.clase = clase; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
}
