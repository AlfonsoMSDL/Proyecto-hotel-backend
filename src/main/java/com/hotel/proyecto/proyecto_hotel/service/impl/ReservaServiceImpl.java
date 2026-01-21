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
import java.time.LocalDateTime;
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
        estadoDeLaReserva.setFechaInicio(Timestamp.valueOf(LocalDateTime.now()));

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
        estadoDeLaHabitacion.setFechaInicio(Timestamp.valueOf(LocalDateTime.now()));

        //Guardo el estado de la habitacion usada en la reservacion
        estadoDeLaHabitacionService.save(estadoDeLaHabitacion);
        log.info("Estado de la habitacion guardado correctamente: "+estadoDeLaHabitacion);

        //Al estado anterior de esa habitacion, se le pone una fecha de fin
        //la cual sera la fecha de inicio del nuevo estado
        //el estado anterior sera de Disponible ya que para que una habitacion
        //se pueda reservar, debe estar disponible
        estadoHabitacion = estadoHabitacionService.findByNombre("Disponible");
        EstadoDeLaHabitacion estadoDeLaHabitacionUpdate = estadoDeLaHabitacionService.findByHabitacionAndEstadoHabitacionAndFechaFinIsNull(habitacion, estadoHabitacion);

        log.info("Antes de actualizar el estado anterior de la habitacion "+estadoDeLaHabitacionUpdate);
        //Luego de tener ese estado de la habitacion, actualizo su fecha de fin
        estadoDeLaHabitacionUpdate.setFechaFin(estadoDeLaHabitacion.getFechaInicio());
        //Luego de actualizar, guardo los cambios
        estadoDeLaHabitacionUpdate =estadoDeLaHabitacionService.save(estadoDeLaHabitacionUpdate);
        log.info("Se actualiza el estado anterior de la habitacion "+estadoDeLaHabitacionUpdate);

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

    @Override
    @Transactional
    public void cancelarReserva(Long idReserva) {
        //Primero busco la reserva que se va a cancelar
        Reserva reserva = reservaRepository.findById(idReserva).orElse(null);
        log.info("Reserva a cancelar \n{}",reserva);
        //Luego busco el estado de Cancelada, para crear el estado de la reserva
        EstadoReserva  estadoReserva = estadoReservaService.findByNombre("Cancelada");
        //Luego creo el estado de la reserva para relacionar la reserva con el nuevo estado
        log.info("Se le asigna el estado de Cancelada a esa reserva");
        EstadoDeLaReserva estadoDeLaReserva = new EstadoDeLaReserva();
        estadoDeLaReserva.setId(new EstadoDeLaReservaId());
        estadoDeLaReserva.setEstadoReserva(estadoReserva);
        estadoDeLaReserva.setReserva(reserva);
        estadoDeLaReserva.setFechaInicio(Timestamp.valueOf(LocalDateTime.now()));
        //Guardo el nuevo estado de la reserva
        estadoDeLaReservaService.save(estadoDeLaReserva);

        //Luego pongo una fecha de fin al estado de la reserva anterior
        //Por logica este estado es el de confirmada, ya que si cancelo una reserva
        //Es porque antes estuvo confirmada
        //Asi que busco el estado de la reserva con estado Confirmada y la reserva que quiero cancelar
        estadoReserva = estadoReservaService.findByNombre("Confirmada");
        EstadoDeLaReserva estadoDeLaReservaUpdate = estadoDeLaReservaService.findByReservaAndEstadoReserva(reserva, estadoReserva);
        //Luego de tener ese estado de la reserva, le asigno una fecha de fin
        //para indicar que ese estado finalizo, la fecha de fin sera la fecha de inicio
        // del nuevo estado de la reserva Cancelada
        estadoDeLaReservaUpdate.setFechaFin(estadoDeLaReserva.getFechaInicio());
        //ahora hago el save pero en realidad es un update
        estadoDeLaReservaService.save(estadoDeLaReservaUpdate);
        log.info("Se modifica el estado anterior de esa reserva, poniendole la fecha de fin");

        //Ahora hago lo mismo para el estado de la habitacion, ya que debe quedar disponible, creo un nuevo estado de la habitacion y le asigno una fecha de fin al estado anterior
        EstadoHabitacion estadoHabitacion = estadoHabitacionService.findByNombre("Disponible");
        Habitacion habitacion = reserva.getHabitacion();


        EstadoDeLaHabitacion estadoDeLaHabitacion  = new EstadoDeLaHabitacion();
        estadoDeLaHabitacion.setEstadoHabitacion(estadoHabitacion);
        estadoDeLaHabitacion.setHabitacion(habitacion);
        estadoDeLaHabitacion.setFechaInicio(Timestamp.valueOf(LocalDateTime.now()));

        estadoDeLaHabitacionService.save(estadoDeLaHabitacion);
        log.info("Se le asigna a la habitacion de la reserva el estado de Disponible");

        //Procedo a ponerle una fecha de fin al estado anterior
        //El estado anterior es Reservada porque para cancelar una reservacion, previamente debe estar reservada
        estadoHabitacion = estadoHabitacionService.findByNombre("Reservada");
        EstadoDeLaHabitacion estadoDeLaHabitacionUpdate = estadoDeLaHabitacionService.findByHabitacionAndEstadoHabitacionAndFechaFinIsNull(habitacion, estadoHabitacion);
        estadoDeLaHabitacionUpdate.setFechaFin(estadoDeLaHabitacion.getFechaInicio());

        estadoDeLaHabitacionService.save(estadoDeLaHabitacionUpdate);
        log.info("Se modifica el estado anterior de esa habitacion, poniendole la fecha de fin");

    }
}
