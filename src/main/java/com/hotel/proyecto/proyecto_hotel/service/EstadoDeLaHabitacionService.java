package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.EstadoHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;

public interface EstadoDeLaHabitacionService {
    EstadoDeLaHabitacion save(EstadoDeLaHabitacion estadoDeLaHabitacion);
    EstadoDeLaHabitacion  findById(Long id);

    EstadoDeLaHabitacion findByHabitacionAndEstadoHabitacionAndFechaFinIsNull(Habitacion habitacion, EstadoHabitacion estadoHabitacion);
}
