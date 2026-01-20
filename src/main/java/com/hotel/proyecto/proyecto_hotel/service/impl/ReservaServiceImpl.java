package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.dto.request.SaveReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHistorialReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetReserva;
import com.hotel.proyecto.proyecto_hotel.exception.ReservaCruzadaException;
import com.hotel.proyecto.proyecto_hotel.mapper.ReservaMapper;
import com.hotel.proyecto.proyecto_hotel.model.*;
import com.hotel.proyecto.proyecto_hotel.repository.HabitacionRepository;
import com.hotel.proyecto.proyecto_hotel.repository.ReservaRepository;
import com.hotel.proyecto.proyecto_hotel.repository.UsuarioRepository;
import com.hotel.proyecto.proyecto_hotel.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ReservaServiceImpl implements ReservaService {
    private ReservaRepository reservaRepository;
    private ReservaMapper reservaMapper;
    private UsuarioRepository  usuarioRepository;
    private HabitacionRepository habitacionRepository;
    private EstadoReservaService  estadoReservaService;
    private EstadoDeLaReservaService estadoDeLaReservaService;
    private EstadoHabitacionService  estadoHabitacionService;
    private EstadoDeLaHabitacionService estadoDeLaHabitacionService;

    @Transactional
    @Override
    public GetReserva save(SaveReserva reserva) throws ReservaCruzadaException{
        //Primero mapeo el SaveReserva a una Reserva
        Reserva reservaGuardar = reservaMapper.toReservaFromSaveReserva(reserva);
        //Busco el usuario para agregarselo a la reserva
        Usuario usuario = usuarioRepository.findById(reserva.idUsuario()).orElse(null);
        log.info("Usuario que va a reservar: "+usuario);
        //Le agrego el usuario a la reserva
        reservaGuardar.setUsuario(usuario);
        //Hago lo mismo para la habitacion
        Habitacion habitacion = habitacionRepository.findById(reserva.idHabitacion()).orElse(null);
        log.info("Habitacion que va a reservar: "+habitacion);
        reservaGuardar.setHabitacion(habitacion);

        //Hasta este punto la reserva esta lista para ser guardada en la bd
        //Ahora hay que comprobar que las fechas de entrada y salidas esten disponibles para reservar
        Optional<Reserva> reservaCruzada = reservaRepository.encontrarReservaCruzada(
                reservaGuardar.getHabitacion().getId(),
                reservaGuardar.getFechaLlegada(),
                reservaGuardar.getFechaSalida()
        );

        //Si la reserva cruzada tiene un objeto significa que la reserva que trato de
        // guardar, se cruza con otra reserva anterior
        if(reservaCruzada.isPresent()) {
            log.info("Esta reserva se cruza con otra que fue hecha previamente por otro cliente");
            throw new ReservaCruzadaException("Esta reserva se cruza con otra que fue hecha previamente por otro cliente");
        }
        //Si no se cruza con ninguna, procedemos a guardar la reserva en la bd
        Reserva reservaGuardada = reservaRepository.save(reservaGuardar);
        log.info("Reserva guardada correctamente "+reservaGuardada);

        //Traigo el estado de reserva Confirmada, para agregar la resera a estados de la reserva
        EstadoReserva estadoReserva = estadoReservaService.findByNombre("Confirmada");

        //Teniendo la resera creada y el estado que se le va a poner a la reserva
        // Procedemos a insertar un estado de la reserva, que relacione el estado
        // con la reserva
        EstadoDeLaReserva estadoDeLaReserva = new EstadoDeLaReserva();
        estadoDeLaReserva.setId(new EstadoDeLaReservaId());
        estadoDeLaReserva.setEstadoReserva(estadoReserva);
        estadoDeLaReserva.setReserva(reservaGuardar);

        //Guardo el estado de la reserva creada
        estadoDeLaReservaService.save(estadoDeLaReserva);
        log.info("Estado de la reserva guardada correctamente "+estadoDeLaReserva);

        //Al crear la reserva correctamente, se debe asignar un estado a la habitacion de
        // Reservada
        EstadoHabitacion  estadoHabitacion = estadoHabitacionService.findByNombre("Reservada");

        //Ahora relaciono el estado con la habitacion
        EstadoDeLaHabitacion estadoDeLaHabitacion = new EstadoDeLaHabitacion();
        estadoDeLaHabitacion.setHabitacion(habitacion);
        estadoDeLaHabitacion.setEstadoHabitacion(estadoHabitacion);

        //Guardo el estado de la habitacion usada en la reservacion
        estadoDeLaHabitacionService.save(estadoDeLaHabitacion);
        log.info("Estado de la habitacion guardado correctamente: "+estadoDeLaHabitacion);

        return reservaMapper.toGetReserva(reservaGuardada);
    }
    public Reserva findById(Long id) {return reservaRepository.findById(id).orElse(null);}

    //Este metodo lo voy a usar para filtrar por las reservas completadas de una habitacion
    @Override
    public List<GetReserva> buscarReservasConfirmadasPorHabitacion(Long idHabitacion) {
        List<Reserva> reservas = reservaRepository.findReservasConfirmadasActualesPorHabitacion(idHabitacion);
        return reservaMapper.toGetReservaList(reservas);
    }

    @Override
    @Transactional
    public List<GetHistorialReserva> historialReservasPorUsuarioYEstadoReserva(Long idUsuario, String estado) {
        List<Reserva> reservas = reservaRepository.findHistorialReservasPorUsuarioYEstado(idUsuario,estado);

        return reservas.stream()
                .map(r ->
                    new GetHistorialReserva(
                        r.getId(),
                        r.getHabitacion().getNumero(),
                        r.getFechaLlegada(),
                        r.getFechaSalida(),
                        estado)
                )
                .toList();
    }
}
