package com.hotel.proyecto.proyecto_hotel.controller;

import com.hotel.proyecto.proyecto_hotel.dto.request.SaveReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHistorialReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetReserva;
import com.hotel.proyecto.proyecto_hotel.exception.FechaLlegadaNoIgualFechaActualException;
import com.hotel.proyecto.proyecto_hotel.exception.ReservaCruzadaException;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import com.hotel.proyecto.proyecto_hotel.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/habitaciones/{idHabitacion}")
    public ResponseEntity<List<GetReserva>> buscarReservasConfirmadasPorHabitacion(@PathVariable Long idHabitacion){
        List<GetReserva> reservas = reservaService.buscarReservasConfirmadasPorHabitacion(idHabitacion);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/mis_reservas")
    public ResponseEntity<List<GetHistorialReserva>> historialReservasPorUsuarioYEstadoReserva(@RequestParam Long idUsuario, @RequestParam String estado){
        List<GetHistorialReserva> historialReservas = reservaService.historialReservasPorUsuarioYEstadoReserva(idUsuario,estado);

        return ResponseEntity.ok(historialReservas);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void >cancelarReserva(@PathVariable Long id){
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/{nombreCompleto}/habitaciones/{numHabitacion}")
    public ResponseEntity<List<GetReserva>> buscarReservasDeClienteDadoNumHabitacion(@PathVariable String nombreCompleto, @PathVariable Integer numHabitacion){

        List<GetReserva> reservas = reservaService.buscarReservasDeClienteDadoNumHabitacion(nombreCompleto, numHabitacion);

        return ResponseEntity.ok(reservas);
    }

    @PostMapping("/marcar-entrada/{idReserva}")
    public ResponseEntity<Object> marcarReserva(@PathVariable Long idReserva){
        try{
            reservaService.marcarEntrada(idReserva);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (FechaLlegadaNoIgualFechaActualException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"error\":\""+e.getMessage()+"\"");
        }

    }
}
