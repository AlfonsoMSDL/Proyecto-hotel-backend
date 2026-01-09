package com.hotel.proyecto.proyecto_hotel.dto.request;

import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;

public record HabitacionSave(

        Integer numero,
        TipoHabitacion tipoHabitacion,
        Double precioNoche,
        Integer capacidad,
        String descripcion
) {
}
