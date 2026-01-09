package com.hotel.proyecto.proyecto_hotel.repository;

import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    boolean existsByNumero(int numero);
    List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion);
    //Hago una consulta derivada para buscar habitaciones con precio entre un rango
    List<Habitacion> findByPrecioNocheBetween(Double precioMinimo, Double precioMaximo);
}
