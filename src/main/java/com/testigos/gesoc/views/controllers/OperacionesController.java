package com.testigos.gesoc.views.controllers;

import java.util.List;

import com.testigos.gesoc.model.domain.usuarios.Mensaje;
import com.testigos.gesoc.model.domain.usuarios.Usuario;
import com.testigos.gesoc.model.services.MensajeService;
import com.testigos.gesoc.model.services.RegistroService;
import com.testigos.gesoc.model.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operaciones*")
public class OperacionesController {

    @Autowired
    private RegistroService registroService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MensajeService mensajeService;

    @GetMapping
    public String registersView(Model model, Authentication auth) {
        Usuario user = usuarioService.find(auth.getName());
        List<Mensaje> mensajes = mensajeService.getMensajes(user);
        model.addAttribute("user", user);
        model.addAttribute("mensajes", mensajes);
        model.addAttribute("registros", registroService.findAll());
        return "operaciones";
    }
}
