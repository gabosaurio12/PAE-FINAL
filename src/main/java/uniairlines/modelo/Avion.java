package uniairlines.modelo;

import java.util.List;
import java.util.UUID;

public class Avion {
    private String id;
    private String modelo;
    private int capacidadTotal;
    private int filas;
    private int asientosPorFila;
    private int peso;
    private String tipo;
    private List<Asiento> asientos;
    private String estado;  


    public Avion() {
        this.id = UUID.randomUUID().toString();
    }

    public Avion(String modelo,int Peso) {
        this.id = UUID.randomUUID().toString();
        this.modelo = modelo;
        this.capacidadTotal = this.asientosPorFila * this.filas;
        this.filas = 1;
        this.asientosPorFila = 1;
        this.tipo = "avion";
        this.asientos = null; 
        this.estado = "libre";
        this.peso = peso;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getCapacidadTotal() { return capacidadTotal; }
    public void setCapacidadTotal(int capacidadTotal) { this.capacidadTotal = capacidadTotal; }

    public int getFilas() { return filas; }
    public void setFilas(int filas) { this.filas = filas; }

    public int getAsientosPorFila() { return asientosPorFila; }
    public void setAsientosPorFila(int asientosPorFila) { this.asientosPorFila = asientosPorFila; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<Asiento> getAsientos() { return asientos; }
    public void setAsientos(List<Asiento> asientos) { this.asientos = asientos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public double getPeso() {return peso;}
    public void setPeso(int peso) {this.peso = peso;}

    public String formatoPDF() {
        return "ID: " + getId() + "\n" +
                "Modelo: " + getModelo() + "\n" +
                "Capacidad total: " + getCapacidadTotal() + "\n" +
                "Filas: " + getFilas() + "\n" +
                "Asientos por fila: " + getAsientosPorFila() + "\n" +
                "Peso: " + getPeso() + " kg\n" +
                "Tipo: " + getTipo() + "\n" +
                "Estado: " + getEstado() + "\n" +
                "Número de asientos configurados: " +
                (getAsientos() != null ? getAsientos().size() : 0) + "\n";
    }

    public String[] formatoCSV() {
        return new String[] {
                getId(),
                getModelo(),
                String.valueOf(getCapacidadTotal()),
                String.valueOf(getFilas()),
                String.valueOf(getAsientosPorFila()),
                String.valueOf(getPeso()),
                getTipo(),
                getEstado(),
                String.valueOf(getAsientos() != null ? getAsientos().size() : 0)
        };
    }

    public String[] formatoXLSX() {
        return new String[] {
                "ID: " + getId(),
                "Modelo: " + getModelo(),
                "Capacidad total: " + getCapacidadTotal(),
                "Filas: " + getFilas(),
                "Asientos por fila: " + getAsientosPorFila(),
                "Peso: " + getPeso() + " kg",
                "Tipo: " + getTipo(),
                "Estado: " + getEstado(),
                "Número de asientos configurados: " + (getAsientos() != null ? getAsientos().size() : 0)
        };
    }

    @Override
    public String toString() {
        return id;
    }
}
