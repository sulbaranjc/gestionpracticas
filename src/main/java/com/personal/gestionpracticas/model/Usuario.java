package com.personal.gestionpracticas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String contraseña;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public enum Rol {
        ADMIN, USER
    }

    // Nuevos campos para preguntas y respuestas
    @Column(nullable = false)
    private String pregunta1;

    @Column(nullable = false)
    private String pregunta2;

    @Column(nullable = false)
    private String pregunta3;

    @Column(nullable = false)
    private String respuesta1;

    @Column(nullable = false)
    private String respuesta2;

    @Column(nullable = false)
    private String respuesta3;
}
