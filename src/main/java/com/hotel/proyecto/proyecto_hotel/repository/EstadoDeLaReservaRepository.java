package com.hotel.proyecto.proyecto_hotel.repository;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoDeLaReservaRepository extends JpaRepository<EstadoDeLaReserva, Long> {
}
