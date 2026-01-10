package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;

import java.io.IOException;
import java.util.List;

public interface FotoService {
    List<GetFoto>  findByIdHabitacion(Long idHabitacion);
    void eliminarFotoDeHabitacion(Long idFoto) throws IOException;

}
