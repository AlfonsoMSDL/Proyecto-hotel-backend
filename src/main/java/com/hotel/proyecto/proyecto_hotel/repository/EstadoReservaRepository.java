package com.hotel.proyecto.proyecto_hotel.repository;

import com.hotel.proyecto.proyecto_hotel.model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Long> {
    EstadoReserva findByNombre(String nombre);
}
