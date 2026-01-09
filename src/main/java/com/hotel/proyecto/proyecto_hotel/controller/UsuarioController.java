package com.hotel.proyecto.proyecto_hotel.controller;

import com.hotel.proyecto.proyecto_hotel.dto.request.UsuarioSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetUsuario;
import com.hotel.proyecto.proyecto_hotel.exception.UsuarioRegistrarCorreoExistenteExcepcion;
import com.hotel.proyecto.proyecto_hotel.model.enums.Rol;
import com.hotel.proyecto.proyecto_hotel.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Este controlador va a manejar la api para la gestion de usuarios (Cliente y Administrador)
@Slf4j
@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private UsuarioService usuarioService;

    @PostMapping
    public Object save(@RequestBody UsuarioSave usuarioSave) {
        try{
            return usuarioService.save(usuarioSave);
        }catch(UsuarioRegistrarCorreoExistenteExcepcion x){
            log.error("Error al insertar el usuario "+x.getMessage());

            return "\"error\":\""+x.getMessage()+"\"";
        }

    }

    @GetMapping
    public List<GetUsuario> findAll() {
        return  usuarioService.findAll();
    }

    @GetMapping("/byRol/{rol}")
    public List<GetUsuario> findByRol(@PathVariable Rol rol) {
        return  usuarioService.findByRol(rol);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        usuarioService.deleteById(id);
    }
}
