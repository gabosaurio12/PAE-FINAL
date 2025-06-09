/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo;



/**
 *
 * @author cuent
 */
public class Aerolinea {
    private String IATA;
    private String ICAO;
    private String nombre;
    private String callsign;
    private String nacionalidad;
    private String direccion;
    private String sitioOficial;
    private Contacto contacto;

    public Aerolinea() {}

    public Aerolinea(String IATA, String ICAO, String nombre, String callsign, String nacionalidad, String direccion, String sitioOficial, Contacto contacto) {
        this.IATA = IATA;
        this.ICAO = ICAO;
        this.nombre = nombre;
        this.callsign = callsign;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.sitioOficial = sitioOficial;
        this.contacto = contacto;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSitioOficial() {
        return sitioOficial;
    }

    public void setSitioOficial(String sitioOficial) {
        this.sitioOficial = sitioOficial;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }


    public String formatoPDF() {
        return String.format(
                "Nombre: %s\nIATA: %s\nICAO: %s\nCallsign: %s\nNacionalidad: %s\nDirección: %s\nSitio Oficial: %s\nContacto: %s - %s\n",
                getNombre(), getIATA(), getICAO(), getCallsign(), getNacionalidad(),
                getDireccion(), getSitioOficial(),
                getContacto().getNombre(), getContacto().getTelefono()
        );
    }

    public String[] formatoCSV() {
        return new String[] {
                getNombre(),
                getIATA(),
                getICAO(),
                getCallsign(),
                getNacionalidad(),
                getDireccion(),
                getSitioOficial(),
                getContacto().getNombre(),
                getContacto().getTelefono()
        };
    }

    public String[] formatoXLSX() {
        return new String[] {
                "Nombre: " + getNombre(),
                "IATA: " + getIATA(),
                "ICAO: " + getICAO(),
                "Callsign: " + getCallsign(),
                "Nacionalidad: " + getNacionalidad(),
                "Dirección: " + getDireccion(),
                "Sitio Oficial: " + getSitioOficial(),
                "Contacto Nombre: " + getContacto().getNombre(),
                "Contacto Teléfono: " + getContacto().getTelefono()
        };
    }

    @Override
    public String toString() {
        return nombre;
    }
}
