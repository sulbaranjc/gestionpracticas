package com.personal.gestionpracticas.service;

import com.personal.gestionpracticas.dto.RegistroUsuarioDTO;
import com.personal.gestionpracticas.model.Usuario;
import com.personal.gestionpracticas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public String registrarUsuario(RegistroUsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "El email ya está registrado.";
        }

        if (!esContraseñaValida(dto.getContraseña())) {
            return "La contraseña no cumple con los requisitos de seguridad.";
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .contraseña(passwordEncoder.encode(dto.getContraseña()))
                .pregunta1(dto.getPregunta1())
                .pregunta2(dto.getPregunta2())
                .pregunta3(dto.getPregunta3())
                .respuesta1(passwordEncoder.encode(dto.getRespuesta1().trim().toLowerCase()))
                .respuesta2(passwordEncoder.encode(dto.getRespuesta2().trim().toLowerCase()))
                .respuesta3(passwordEncoder.encode(dto.getRespuesta3().trim().toLowerCase()))
                .rol(Usuario.Rol.USER)
                .build();

        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente.";
    }

    private boolean esContraseñaValida(String contraseña) {
        if (contraseña.length() < 8) return false;
        boolean tieneMayuscula = contraseña.chars().anyMatch(Character::isUpperCase);
        boolean tieneMinuscula = contraseña.chars().anyMatch(Character::isLowerCase);
        boolean tieneNumero = contraseña.chars().anyMatch(Character::isDigit);
        boolean tieneEspecial = contraseña.matches(".*[!@#$%^&*()-+].*");

        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial;
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public void cambiarRol(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            if (usuario.getRol() == Usuario.Rol.USER) {
                usuario.setRol(Usuario.Rol.ADMIN);
            } else {
                usuario.setRol(Usuario.Rol.USER);
            }
            usuarioRepository.save(usuario);
        });
    }
}
