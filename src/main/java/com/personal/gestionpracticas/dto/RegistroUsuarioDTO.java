package com.personal.gestionpracticas.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class RegistroUsuarioDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;

    // Cambio clave: Mapa de preguntas y respuestas
    private Map<String, String> preguntasRespuestas = new LinkedHashMap<>();
}
