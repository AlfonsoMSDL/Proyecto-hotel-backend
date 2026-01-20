package com.hotel.proyecto.proyecto_hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
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

    @Query("""
        SELECT r FROM Reserva r
            JOIN r.estadosDeLaReserva edlr
            JOIN edlr.estadoReserva er
        WHERE er.nombre = 'Completada'
          AND edlr.fechaFin IS NULL
          AND r.habitacion.id = :idHabitacion
    """)
    List<Reserva> findReservasCompletadasActualesPorHabitacion(
            @Param("idHabitacion") Long idHabitacion
    );

    /*Mostrar el historial de reservas de un usuario filtrado por un estado específico*/
    @Query("""
        SELECT r FROM Reserva r
            JOIN r.estadosDeLaReserva edlr
            JOIN edlr.estadoReserva er
        WHERE r.usuario.id = :idUsuario
          AND er.nombre = :estado
          AND edlr.fechaFin IS NULL
        ORDER BY r.fechaLlegada DESC
    """)
    List<Reserva> findHistorialReservasPorUsuarioYEstado(
            @Param("idUsuario") Long idUsuario,
            @Param("estado") String estado
    );


}
