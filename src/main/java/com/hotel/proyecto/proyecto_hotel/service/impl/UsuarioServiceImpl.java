package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.dto.request.UsuarioSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetUsuario;
import com.hotel.proyecto.proyecto_hotel.exception.UsuarioRegistrarCorreoExistenteExcepcion;
import com.hotel.proyecto.proyecto_hotel.mapper.UsuarioMapper;
import com.hotel.proyecto.proyecto_hotel.model.Usuario;
import com.hotel.proyecto.proyecto_hotel.model.enums.Rol;
import com.hotel.proyecto.proyecto_hotel.repository.UsuarioRepository;
import com.hotel.proyecto.proyecto_hotel.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    @Override
    public GetUsuario save(UsuarioSave usuarioSave) {
        //Primero verifico que el correo no este registrado en la BD
        boolean existeCorreo = usuarioRepository.existsByCorreo(usuarioSave.correo());
        if (existeCorreo) {
            //Lanzo una excepcion que diga que el correo ya existe
            throw new UsuarioRegistrarCorreoExistenteExcepcion("El correo ya está registrado");
        }
        Usuario usuario = usuarioMapper.toUsuarioFromUsuarioSave(usuarioSave);
        usuario.setRol(Rol.CLIENTE);

        return usuarioMapper.toGetUsuario(
                usuarioRepository.save(usuario)
        );


    }

    @Override
    public GetUsuario findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuarioMapper.toGetUsuario(usuario);
    }

    @Override
    public List<GetUsuario> findAll() {
        return usuarioMapper.toGetUsuarioList(usuarioRepository.findAll());
    }

    @Override
    public List<GetUsuario> findByRol(Rol rol) {
        return usuarioMapper.toGetUsuarioList(usuarioRepository.findByRol(rol));
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }


}
