package com.hotel.proyecto.proyecto_hotel.controller;

import com.hotel.proyecto.proyecto_hotel.dto.request.SaveReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetReserva;
import com.hotel.proyecto.proyecto_hotel.exception.ReservaCruzadaException;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import com.hotel.proyecto.proyecto_hotel.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservas")
@AllArgsConstructor
public class ReservaController {
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<Object> crearReserva(@RequestBody SaveReserva reserva){
        try{
            GetReserva reservaGuardada =  reservaService.save(reserva);
            return  ResponseEntity.status(HttpStatus.CREATED).body(reservaGuardada);
        }catch (ReservaCruzadaException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
