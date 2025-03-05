package com.personal.gestionpracticas.controller;

import com.personal.gestionpracticas.model.Usuario;
import com.personal.gestionpracticas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUsuarioController {

    private final UsuarioRepository usuarioRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuario/listar";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/usuario/cambiar-rol")
    public String cambiarRol(@RequestParam("id") Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            if (usuario.getRol() == Usuario.Rol.USER) {
                usuario.setRol(Usuario.Rol.ADMIN);
            } else {
                usuario.setRol(Usuario.Rol.USER);
            }
            usuarioRepository.save(usuario);
        }
        return "redirect:/admin/usuarios";
    }
}
