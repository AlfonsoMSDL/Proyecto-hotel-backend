package com.hotel.proyecto.proyecto_hotel.repository;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.EstadoHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoDeLaHabitacionRepository extends JpaRepository<EstadoDeLaHabitacion, Long> {

    //Esta consulta la voy a usar para traer el estado actual de una habitacion especifica con un estado en especifico
    EstadoDeLaHabitacion findByHabitacionAndEstadoHabitacionAndFechaFinIsNull(Habitacion habitacion, EstadoHabitacion estadoHabitacion);
}
