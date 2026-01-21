package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.EstadoHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import com.hotel.proyecto.proyecto_hotel.repository.EstadoDeLaHabitacionRepository;
import com.hotel.proyecto.proyecto_hotel.service.EstadoDeLaHabitacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstadoDeLaHabitacionServiceImpl implements EstadoDeLaHabitacionService {

    private EstadoDeLaHabitacionRepository estadoDeLaHabitacionRepository;
    @Override
    public EstadoDeLaHabitacion save(EstadoDeLaHabitacion estadoDeLaHabitacion) {
        return estadoDeLaHabitacionRepository.save(estadoDeLaHabitacion);
    }

    @Override
    public EstadoDeLaHabitacion findById(Long id) {
        return estadoDeLaHabitacionRepository.findById(id).orElse(null);
    }

    @Override
    public EstadoDeLaHabitacion findByHabitacionAndEstadoHabitacionAndFechaFinIsNull(Habitacion habitacion, EstadoHabitacion estadoHabitacion) {
        return estadoDeLaHabitacionRepository.findByHabitacionAndEstadoHabitacionAndFechaFinIsNull(habitacion,estadoHabitacion);
    }
}
