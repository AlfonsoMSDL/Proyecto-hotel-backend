package com.hotel.proyecto.proyecto_hotel.controller;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetUsuario;
import com.hotel.proyecto.proyecto_hotel.exception.UsuarioLoginClaveErroneaException;
import com.hotel.proyecto.proyecto_hotel.exception.UsuarioLoginNoEncontradoException;
import com.hotel.proyecto.proyecto_hotel.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping
    public ResponseEntity<GetUsuario> login(@RequestParam String correo, @RequestParam String clave){
        GetUsuario  getUsuario = authService.login(correo, clave);
        return ResponseEntity.ok().body(getUsuario);
    }
}
