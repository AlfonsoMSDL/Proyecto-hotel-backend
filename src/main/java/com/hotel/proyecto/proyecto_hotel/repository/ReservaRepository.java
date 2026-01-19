package com.hotel.proyecto.proyecto_hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /*Creando consulta para comprobar si las fechas de una reserva de una habitacion
    se cruzan con las fechas de otra reserva ya existente de la misma habitacion*/
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.habitacion.id = :idHabitacion " +
            "AND r.fechaLlegada  < :fechaSalida " +
            "AND r.fechaSalida > :fechaLlegada")
    Optional<Reserva> encontrarReservaCruzada(Long idHabitacion, Timestamp fechaLlegada, Timestamp fechaSalida);
}
