package com.hotel.proyecto.proyecto_hotel;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetUsuario;
import com.hotel.proyecto.proyecto_hotel.mapper.UsuarioMapper;
import com.hotel.proyecto.proyecto_hotel.model.*;
import com.hotel.proyecto.proyecto_hotel.model.enums.Rol;
import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import com.hotel.proyecto.proyecto_hotel.repository.UsuarioRepository;
import com.hotel.proyecto.proyecto_hotel.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;
import java.sql.Timestamp;
@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class ProyectoHotelApplication implements CommandLineRunner {


    private UsuarioService usuarioService;
    private HabitacionService habitacionService;
    private ReservaService reservaService;
    private EstadoReservaService estadoReservaService;
    private EstadoDeLaReservaService estadoDeLaReservaService;
    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private EstadoHabitacionService estadoHabitacionService;
    private EstadoDeLaHabitacionService estadoDeLaHabitacionService;



    public static void main(String[] args) {
        SpringApplication.run(ProyectoHotelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    public void guardarUsuario(){
        log.info("Iniciando proyecto hotel");
        log.info("Guardando un usuario");
        Usuario usuario = new Usuario();
        usuario.setNombre("Alfonso");
        usuario.setApellido("Salazar");
        usuario.setCorreo("alfonso@gmail.com");
        usuario.setClave("1234");
        usuario.setCelular("123456");
        usuario.setRol(Rol.CLIENTE);

        GetUsuario save = usuarioService.save(
                usuarioMapper.toUsuarioSave(usuario)
        );

        log.info("Se ha guardado el usuario, get Usuario: \n");
        log.info(save.toString());
    }
    public void guardarHabitacion(){
        log.info("Iniciando proyecto hotel");
        log.info("Guardando un habitacion");
        Habitacion habitacion = new Habitacion();
        habitacion.setTipoHabitacion(TipoHabitacion.INDIVIDUAL);
        habitacion.setCapacidad(4);
        habitacion.setNumero(102);
        habitacion.setDescripcion("Habitacion con aire y televisor ademas de baño interno");
        habitacion.setPrecioNoche(40000.34);

//        Habitacion hab = habitacionService.save(habitacion);

//        log.info("Se ha guardado el habitacion: \n" + hab);



    }
    public void guardarReserva(){
        log.info("Iniciando proyecto hotel");
        log.info("Guardando una reserva");
        Habitacion habitacion = habitacionService.findById(1l);
        log.info("Habitacion para la reserva: \n"+habitacion);
        Usuario usuario = usuarioRepository.findById(1l).get();
        log.info("Usuario para la reserva: \n"+usuario);

        Reserva reserva = new Reserva();
        reserva.setFechaLlegada(Timestamp.valueOf("2025-12-22 00:00:00"));
        reserva.setFechaSalida(Timestamp.valueOf("2025-12-25 00:00:00"));
        reserva.setHabitacion(habitacion);
        reserva.setUsuario(usuario);

        reservaService.save(reserva);

        log.info("Se ha guardado el reserva: \n" + reserva);


    }
    public void guardarEstadoReserva(){
        log.info("Iniciando proyecto hotel");
        log.info("Guardando un estado reserva");
        EstadoReserva estadoReserva = new EstadoReserva();
//        estadoReserva.setNombre("Completada");
//        estadoReserva.setDescripcion("Se hizo la reserva correctamente.");
        estadoReserva.setNombre("En tramite");
        estadoReserva.setDescripcion("Se hizo la reserva pero no se ha confirmado el pago");

        estadoReservaService.save(estadoReserva);

        log.info("Se hizo la reserva: \n" + estadoReserva);
    }
    public void guardarEstadoDeLaReserva(){
        log.info("Iniciando proyecto hotel");
        log.info("Guardando un estado de la reserva");
        Reserva reserva = reservaService.findById(1l);
        EstadoReserva estadoReserva = estadoReservaService.findById(1l); //En tramite

        EstadoDeLaReserva estadoDeLaReserva = new  EstadoDeLaReserva();
        estadoDeLaReserva.setId(new EstadoDeLaReservaId());
        estadoDeLaReserva.setEstadoReserva(estadoReserva);
        estadoDeLaReserva.setReserva(reserva);

        estadoDeLaReserva.setFechaInicio(Timestamp.valueOf("2025-12-24 10:00:00"));
        estadoDeLaReserva.setFechaFin(Timestamp.valueOf("2025-12-24 12:00:00"));

        estadoDeLaReservaService.save(estadoDeLaReserva);
        log.info("Estado de la reserva guardado:\n "+estadoDeLaReserva);
    }
    public void guardarEstadoHabitacion(){
        log.info("Iniciando proyecto hotel");
        log.info("Guardando un estado de habitacion habitacion");
        EstadoHabitacion estadoHabitacion = new EstadoHabitacion();
        estadoHabitacion.setNombre("Disponible");
        estadoHabitacion.setDescripcion("Habitacion disponible para reserva.");

        estadoHabitacion = estadoHabitacionService.save(estadoHabitacion);
        log.info("Estado habitacion guardada:\n "+estadoHabitacion);
    }
    public void guardarEstadoDeLaHabitacion(){
        log.info("Iniciando proyecto hotel");
        log.info("Guardando un estado de una habitacion");
        Habitacion  habitacion = habitacionService.findById(1l);
        EstadoHabitacion estadoHabitacion = estadoHabitacionService.findById(1l);
        EstadoDeLaHabitacion estadoDeLaHabitacion = new EstadoDeLaHabitacion();
        estadoDeLaHabitacion.setEstadoHabitacion(estadoHabitacion);
        estadoDeLaHabitacion.setHabitacion(habitacion);
        estadoDeLaHabitacion.setFechaInicio(Date.valueOf("2025-12-27"));

        estadoDeLaHabitacionService.save(estadoDeLaHabitacion);

        log.info("Estado de la habitacion guardado: \n"+estadoDeLaHabitacion);



    }
}
