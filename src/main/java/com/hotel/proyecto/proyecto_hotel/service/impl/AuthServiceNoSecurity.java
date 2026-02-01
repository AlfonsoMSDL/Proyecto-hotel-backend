package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetUsuario;
import com.hotel.proyecto.proyecto_hotel.exception.UsuarioLoginClaveErroneaException;
import com.hotel.proyecto.proyecto_hotel.exception.UsuarioLoginNoEncontradoException;
import com.hotel.proyecto.proyecto_hotel.mapper.UsuarioMapper;
import com.hotel.proyecto.proyecto_hotel.model.Usuario;
import com.hotel.proyecto.proyecto_hotel.repository.UsuarioRepository;
import com.hotel.proyecto.proyecto_hotel.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceNoSecurity implements AuthService {
    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    @Override
    public GetUsuario login(String correo, String clave){
        //Buscar usuario por correo
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(correo);
        if(usuarioOptional.isEmpty()) {
            throw new UsuarioLoginNoEncontradoException("El usuario no existe");
        }
        //En caso de que exista, comparo la clave con la que mandaron
        Usuario usuario = usuarioOptional.get();
        if(!usuario.getClave().equals(clave)) {
            throw new UsuarioLoginClaveErroneaException("Clave incorrecta");
        }

        return usuarioMapper.toGetUsuario(usuario);
    }
}
