package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaHabitacion;

public interface EstadoDeLaHabitacionService {
    EstadoDeLaHabitacion save(EstadoDeLaHabitacion estadoDeLaHabitacion);
    EstadoDeLaHabitacion  findById(Long id);
}
