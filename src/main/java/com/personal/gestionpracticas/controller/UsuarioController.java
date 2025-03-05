package com.personal.gestionpracticas.controller;

import com.personal.gestionpracticas.dto.RegistroUsuarioDTO;
import com.personal.gestionpracticas.model.Usuario;
import com.personal.gestionpracticas.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("registroUsuarioDTO", new RegistroUsuarioDTO());
        return "auth/registro";
    }

    // Procesar registro
    @PostMapping("/registro")
    public String procesarRegistro(HttpServletRequest request, Model model) {
        RegistroUsuarioDTO dto = new RegistroUsuarioDTO();
        dto.setNombre(request.getParameter("nombre"));
        dto.setApellido(request.getParameter("apellido"));
        dto.setEmail(request.getParameter("email"));
        dto.setContraseña(request.getParameter("contraseña"));

        // Recoger preguntas y respuestas del formulario
        Map<String, String> preguntasRespuestas = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (key.startsWith("preguntasRespuestas[")) {
                String pregunta = key.substring(21, key.length() - 1);
                preguntasRespuestas.put(pregunta, values[0]);
            }
        });

        dto.setPreguntasRespuestas(preguntasRespuestas);

        String resultado = usuarioService.registrarUsuario(dto);

        if (resultado.equals("Usuario registrado correctamente.")) {
            model.addAttribute("mensajeExito", resultado);
            return "auth/login";
        } else {
            model.addAttribute("registroUsuarioDTO", dto);
            model.addAttribute("error", resultado);
            return "auth/registro";
        }
    }

    // Listar usuarios (solo ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/usuario/lista")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuario/listar";  // templates/usuario/listar.html
    }

    // Cambiar rol de usuario (solo ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/usuario/cambiar-rol")
    public String cambiarRolUsuario(@RequestParam("id") Long id) {
        usuarioService.cambiarRol(id);
        return "redirect:/usuario/lista";
    }
}
