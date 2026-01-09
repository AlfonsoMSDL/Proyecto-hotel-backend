package com.hotel.proyecto.proyecto_hotel.mapper;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.model.Foto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FotoMapper {

    @Mapping(source = "habitacion.id", target = "idHabitacion")
    GetFoto toGetFoto(Foto foto);

    List<GetFoto> toGetFotoList(List<Foto> fotos);
}

