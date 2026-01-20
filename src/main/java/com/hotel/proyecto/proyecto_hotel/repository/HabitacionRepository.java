package com.hotel.proyecto.proyecto_hotel.repository;

import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    boolean existsByNumero(int numero);
    List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion);
    //Hago una consulta derivada para buscar habitaciones con precio entre un rango
    List<Habitacion> findByPrecioNocheBetween(Double precioMinimo, Double precioMaximo);


    /*Obtener habitaciones que estén en estado “Disponible” y
    que NO tengan reservas en estado “Completada” que se crucen con un rango de fechas*/
    @Query("""
        SELECT DISTINCT h FROM Habitacion h
            JOIN h.estados edlh
            JOIN edlh.estadoHabitacion eh
        WHERE eh.nombre = 'Disponible'
          AND NOT EXISTS (
              SELECT 1
              FROM Reserva r
              JOIN r.estadosDeLaReserva edlr
              JOIN edlr.estadoReserva er
              WHERE r.habitacion = h
                AND er.nombre = 'Completada'
                AND r.fechaLlegada < :fechaSalida
                AND r.fechaSalida  > :fechaLlegada
          )
    """)
    List<Habitacion> findHabitacionesDisponiblesEnRango(
            @Param("fechaLlegada") Timestamp fechaLlegada,
            @Param("fechaSalida") Timestamp fechaSalida
    );

}
