package com.hotel.proyecto.proyecto_hotel.mapper;

import com.hotel.proyecto.proyecto_hotel.dto.request.UsuarioSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetUsuario;
import com.hotel.proyecto.proyecto_hotel.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    GetUsuario toGetUsuario(Usuario usuario);
    UsuarioSave toUsuarioSave(Usuario usuario);
    Usuario toUsuarioFromUsuarioSave(UsuarioSave usuarioSave);
    Usuario toUsuarioFromGetUsuario(GetUsuario usuario);

    List<GetUsuario> toGetUsuarioList(List<Usuario> usuarioList);
}
