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
        model.addAttribute("usuario", usuarioAutenticado);
        return "home/index";  // apunta a templates/home/index.html
    }
}
