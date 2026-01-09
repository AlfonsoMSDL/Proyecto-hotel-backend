package com.hotel.proyecto.proyecto_hotel.mapper;

import com.hotel.proyecto.proyecto_hotel.dto.request.HabitacionSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {
    Habitacion toHabitacionFromHabitacionSave (HabitacionSave habitacionSave);
    GetHabitacion toGetHabitacion(Habitacion habitacion);
    List<GetHabitacion> toGetHabitacionList(List<Habitacion> habitaciones);
}
