package com.personal.gestionpracticas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

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

    // Cambio clave: Mapeo de preguntas y respuestas en una tabla relacional
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "preguntas_seguridad", joinColumns = @JoinColumn(name = "usuario_id"))
    @MapKeyColumn(name = "pregunta")
    @Column(name = "respuesta")
    private Map<String, String> preguntasRespuestas = new HashMap<>();
}
