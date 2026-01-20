package com.hotel.proyecto.proyecto_hotel.mapper;

import com.hotel.proyecto.proyecto_hotel.dto.request.SaveReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetReserva;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {
    Reserva toReservaFromSaveReserva(SaveReserva reserva);

    @Mapping(source = "habitacion.id", target = "idHabitacion")
    @Mapping(source = "usuario.id", target = "idUsuario")
    GetReserva toGetReserva(Reserva reserva);

    List<GetReserva> toGetReservaList(List<Reserva> reservas);
}
