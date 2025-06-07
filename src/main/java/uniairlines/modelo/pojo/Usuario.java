package uniairlines.modelo.pojo;


public class Usuario {
    private String usuario;
    private String contrasena;
    private String tipo; // "admin" o "empleado" o "master"
    private String aerolinea;
    private String nombre;

    public Usuario(String usuario, String contrasena, String tipo, String aerolinea, String nombre) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.tipo = tipo;
        this.aerolinea = aerolinea;
        this.nombre = nombre;
    }

    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getTipo() { return tipo; }
    public String getAerolinea() { return aerolinea; }
    public String getNombre() { return nombre; }

    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setAerolinea(String aerolinea) { this.aerolinea = aerolinea; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
