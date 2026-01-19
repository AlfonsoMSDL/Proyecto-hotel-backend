package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.model.EstadoHabitacion;

public interface EstadoHabitacionService {
    EstadoHabitacion save(EstadoHabitacion estadoHabitacion);
    EstadoHabitacion findById(Long id);
    EstadoHabitacion  findByNombre(String nombre);
}
