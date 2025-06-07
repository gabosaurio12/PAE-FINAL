/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uniairlines.modelo.pojo.empleados;

import java.time.LocalDateTime;

/**
 *
 * @author abelh
 */
public class Administrador extends Empleado {
    private String departamento;
    private LocalDateTime inicioTurno;
    private LocalDateTime finTurno;

    public Administrador() {
    }

    public Administrador(String departamento, LocalDateTime inicioTurno, LocalDateTime finTurno, String identificador, String nombre, String direccion, String telefono, String correo, String fechaNacimiento, String genero, Double salario) {
        super(identificador, nombre, direccion, telefono, correo, fechaNacimiento, genero, salario);
        this.departamento = departamento;
        this.inicioTurno = inicioTurno;
        this.finTurno = finTurno;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public LocalDateTime getInicioTurno() {
        return inicioTurno;
    }

    public void setInicioTurno(LocalDateTime inicioTurno) {
        this.inicioTurno = inicioTurno;
    }

    public LocalDateTime getFinTurno() {
        return finTurno;
    }

    public void setFinTurno(LocalDateTime finTurno) {
        this.finTurno = finTurno;
    }
}
