package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.model.EstadoReserva;
import com.hotel.proyecto.proyecto_hotel.repository.EstadoReservaRepository;
import com.hotel.proyecto.proyecto_hotel.service.EstadoReservaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstadoReservaServiceImpl implements EstadoReservaService {
    private EstadoReservaRepository estadoReservaRepository;
    @Override
    public EstadoReserva findById(Long id) {
        return estadoReservaRepository.findById(id).orElse(null);
    }

    @Override
    public EstadoReserva save(EstadoReserva estadoReserva) {
        return estadoReservaRepository.save(estadoReserva);
    }

    @Override
    public EstadoReserva findByNombre(String nombre) {
        return estadoReservaRepository.findByNombre(nombre);
    }


}
