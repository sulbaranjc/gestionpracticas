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
            Map<String, String> preguntasSeguridad = usuario.getPreguntasRespuestas();

            System.out.println("Preguntas de seguridad recuperadas: " + preguntasSeguridad);

            if (preguntasSeguridad == null || preguntasSeguridad.isEmpty()) {
                System.out.println("⚠️ El usuario no tiene preguntas configuradas.");
                model.addAttribute("error", "Este usuario no tiene preguntas de seguridad configuradas.");
                return "recuperar/recuperar";
            }

            List<String> preguntas = new ArrayList<>(preguntasSeguridad.keySet());
            Random random = new Random();
            String preguntaSeleccionada = preguntas.get(random.nextInt(preguntas.size()));

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
            Map<String, String> preguntasSeguridad = usuario.getPreguntasRespuestas();

            System.out.println("Preguntas de seguridad del usuario: " + preguntasSeguridad);

            if (preguntasSeguridad != null && preguntasSeguridad.containsKey(pregunta)) {
                String respuestaAlmacenada = preguntasSeguridad.get(pregunta);

                System.out.println("Respuesta almacenada (hasheada): " + respuestaAlmacenada);

                if (respuestaAlmacenada != null && passwordEncoder.matches(respuesta, respuestaAlmacenada)) {
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
                System.out.println("⚠️ No se encontró la pregunta seleccionada en las preguntas guardadas.");
                model.addAttribute("error", "No se encontró la pregunta seleccionada.");
                return "recuperar/recuperar";
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
}
