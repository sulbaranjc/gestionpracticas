package com.personal.gestionpracticas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/auth/login")
    public String mostrarFormularioLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }

        if (logout != null) {
            model.addAttribute("mensaje", "Sesión cerrada correctamente.");
        }

        return "auth/login";  // Asegúrate que login.html esté en /templates/auth/login.html
    }

    @GetMapping("/auth/registro")
    public String mostrarFormularioRegistro() {
        return "auth/registro";  // Asegúrate que registro.html esté en /templates/auth/registro.html
    }

    @GetMapping("/auth/recuperar")
    public String mostrarFormularioRecuperar() {
        return "recuperar/recuperar";  // Asegúrate que recuperar.html esté en /templates/recuperar/recuperar.html
    }

    @GetMapping("/auth/pregunta-seguridad")
    public String mostrarFormularioPreguntaSeguridad() {
        return "recuperar/preguntaSeguridad";  // Asegúrate que preguntaSeguridad.html esté en /templates/recuperar/preguntaSeguridad.html
    }

    @GetMapping("/auth/cambiar-password")
    public String mostrarFormularioCambiarPassword() {
        return "recuperar/cambiarPassword";  // Asegúrate que cambiarPassword.html esté en /templates/recuperar/cambiarPassword.html
    }
}
