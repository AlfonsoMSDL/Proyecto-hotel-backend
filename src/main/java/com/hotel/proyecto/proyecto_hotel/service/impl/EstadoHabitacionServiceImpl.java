package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.model.EstadoHabitacion;
import com.hotel.proyecto.proyecto_hotel.repository.EstadoHabitacionRepository;
import com.hotel.proyecto.proyecto_hotel.service.EstadoHabitacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstadoHabitacionServiceImpl implements EstadoHabitacionService {

    private EstadoHabitacionRepository estadoHabitacionRepository;

    @Override
    public EstadoHabitacion save(EstadoHabitacion estadoHabitacion) {
        return estadoHabitacionRepository.save(estadoHabitacion);
    }

    @Override
    public EstadoHabitacion findById(Long id) {
        return estadoHabitacionRepository.findById(id).orElse(null);
    }
}
