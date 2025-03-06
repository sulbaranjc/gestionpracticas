package com.personal.gestionpracticas.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String mostrarHome(Model model, @AuthenticationPrincipal UserDetails usuarioAutenticado) {
        if (usuarioAutenticado == null) {
            // Si no hay sesión activa, redirigimos al login
            return "redirect:/auth/login";
        }

        // Pasamos el usuario al modelo para que Thymeleaf lo pueda usar
        model.addAttribute("usuario", usuarioAutenticado);

        return "home/index";  // apunta a templates/home/index.html
    }
}
