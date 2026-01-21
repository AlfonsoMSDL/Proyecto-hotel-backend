package com.hotel.proyecto.proyecto_hotel.repository;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaReserva;
import com.hotel.proyecto.proyecto_hotel.model.EstadoReserva;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoDeLaReservaRepository extends JpaRepository<EstadoDeLaReserva, Long> {
    //Esta consulta la voy a usar para buscar el estado de la reserva anterior a la que voy a insertar
    // para actualizar su fecha de fin
    EstadoDeLaReserva findByReservaAndEstadoReserva(Reserva reserva, EstadoReserva estadoReserva);
}
