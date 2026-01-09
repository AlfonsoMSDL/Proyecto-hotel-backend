package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaReserva;
import com.hotel.proyecto.proyecto_hotel.repository.EstadoDeLaReservaRepository;
import com.hotel.proyecto.proyecto_hotel.service.EstadoDeLaReservaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstadoDeLaReservaServiceImpl implements EstadoDeLaReservaService {
    private EstadoDeLaReservaRepository estadoDeLaReservaRepository;
    @Override
    public EstadoDeLaReserva findById(Long id) {
        return estadoDeLaReservaRepository.findById(id).orElse(null);
    }

    @Override
    public EstadoDeLaReserva save(EstadoDeLaReserva estadoReserva) {
        return estadoDeLaReservaRepository.save(estadoReserva);
    }
}
