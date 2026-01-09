package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import com.hotel.proyecto.proyecto_hotel.repository.ReservaRepository;
import com.hotel.proyecto.proyecto_hotel.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReservaServiceImpl implements ReservaService {
    private ReservaRepository reservaRepository;

    @Override
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
    public Reserva findById(Long id) {return reservaRepository.findById(id).orElse(null);}
}
