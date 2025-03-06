package com.personal.gestionpracticas.controller;

import com.personal.gestionpracticas.model.Usuario;
import com.personal.gestionpracticas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class RecuperarPasswordController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/recuperar/pregunta")
    public String mostrarPreguntaSeguridad(@RequestParam("email") String email, Model model) {
        System.out.println("Iniciando proceso para mostrar pregunta de seguridad para el email: " + email);

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            List<String> preguntas = Arrays.asList(
                    usuario.getPregunta1(),
                    usuario.getPregunta2(),
                    usuario.getPregunta3()
            );

            if (preguntas.stream().allMatch(Objects::isNull)) {
                System.out.println("⚠️ El usuario no tiene preguntas configuradas.");
                model.addAttribute("error", "Este usuario no tiene preguntas de seguridad configuradas.");
                return "recuperar/recuperar";
            }

            Random random = new Random();
            String preguntaSeleccionada = preguntas.get(random.nextInt(3));

            while (preguntaSeleccionada == null) {
                preguntaSeleccionada = preguntas.get(random.nextInt(3));
            }

            System.out.println("Pregunta seleccionada: " + preguntaSeleccionada);

            model.addAttribute("email", email);
            model.addAttribute("preguntaSeleccionada", preguntaSeleccionada);

            return "recuperar/preguntaSeguridad";
        } else {
            System.out.println("❌ No se encontró un usuario con el email: " + email);
            model.addAttribute("error", "No se encontró un usuario con ese correo.");
            return "recuperar/recuperar";
        }
    }

    @PostMapping("/recuperar/verificar")
    public String verificarRespuesta(@RequestParam("email") String email,
                                     @RequestParam("pregunta") String pregunta,
                                     @RequestParam("respuesta") String respuesta,
                                     Model model) {

        System.out.println("Iniciando verificación de respuesta.");
        System.out.println("Email: " + email);
        System.out.println("Pregunta: " + pregunta);
        System.out.println("Respuesta ingresada: " + respuesta);

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            String respuestaCorrecta = null;

            if (pregunta.equals(usuario.getPregunta1())) {
                respuestaCorrecta = usuario.getRespuesta1();
            } else if (pregunta.equals(usuario.getPregunta2())) {
                respuestaCorrecta = usuario.getRespuesta2();
            } else if (pregunta.equals(usuario.getPregunta3())) {
                respuestaCorrecta = usuario.getRespuesta3();
            }

            if (respuestaCorrecta != null && passwordEncoder.matches(respuesta, respuestaCorrecta)) {
                System.out.println("✅ Respuesta correcta. Redirigiendo a cambio de contraseña.");
                model.addAttribute("email", email);
                return "recuperar/cambiarPassword";
            } else {
                System.out.println("❌ Respuesta incorrecta.");
                model.addAttribute("email", email);
                model.addAttribute("preguntaSeleccionada", pregunta);
                model.addAttribute("error", "Respuesta incorrecta. Inténtalo nuevamente.");
                return "recuperar/preguntaSeguridad";
            }
        } else {
            System.out.println("❌ No se encontró un usuario con el email: " + email);
            model.addAttribute("error", "No se encontró un usuario con ese correo.");
            return "recuperar/recuperar";
        }
    }

    @GetMapping("/recuperar")
    public String mostrarFormularioRecuperar() {
        return "recuperar/recuperar";
    }

    @PostMapping("/recuperar")
    public String procesarRecuperar(@RequestParam("email") String email, Model model) {
        return "redirect:/recuperar/pregunta?email=" + email;
    }

    @PostMapping("/recuperar/cambiarPassword")
    public String cambiarPassword(@RequestParam("email") String email,
                                  @RequestParam("nuevaPassword") String nuevaPassword,
                                  Model model) {

        System.out.println("Iniciando cambio de contraseña para el email: " + email);

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (!esContraseñaValida(nuevaPassword)) {
                System.out.println("❌ La contraseña no cumple con los requisitos de seguridad.");
                model.addAttribute("error", "La contraseña no cumple con los requisitos de seguridad. Debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial.");
                model.addAttribute("email", email);
                return "recuperar/cambiarPassword";
            }

            usuario.setContraseña(passwordEncoder.encode(nuevaPassword));
            usuarioRepository.save(usuario);

            System.out.println("✅ Contraseña actualizada correctamente para el usuario: " + email);
            model.addAttribute("mensaje", "Contraseña actualizada correctamente.");
            return "redirect:/login";
        } else {
            System.out.println("❌ No se encontró un usuario con el email: " + email);
            model.addAttribute("error", "No se encontró un usuario con ese correo.");
            return "recuperar/recuperar";
        }
    }

    private boolean esContraseñaValida(String contraseña) {
        if (contraseña.length() < 8) return false;
        boolean tieneMayuscula = contraseña.chars().anyMatch(Character::isUpperCase);
        boolean tieneMinuscula = contraseña.chars().anyMatch(Character::isLowerCase);
        boolean tieneNumero = contraseña.chars().anyMatch(Character::isDigit);
        boolean tieneEspecial = contraseña.matches(".*[!@#$%^&*()-+].*");

        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial;
    }
}
