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

        return "auth/login";  // La plantilla login.html debe estar en templates/auth/login.html
    }
}
