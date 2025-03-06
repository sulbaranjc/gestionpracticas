package com.personal.gestionpracticas.dto;

import lombok.Data;

@Data
public class RegistroUsuarioDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;

    // Enfoque reformulado: preguntas y respuestas separadas (no map)
    private String pregunta1;
    private String respuesta1;

    private String pregunta2;
    private String respuesta2;

    private String pregunta3;
    private String respuesta3;
}
