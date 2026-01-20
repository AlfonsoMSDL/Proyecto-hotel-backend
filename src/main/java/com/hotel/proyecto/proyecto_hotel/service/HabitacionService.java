package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.dto.request.HabitacionSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

public interface HabitacionService {
    GetHabitacion save(HabitacionSave habitacionSave, List<MultipartFile> imagenes);
    Habitacion  findById(Long id);
    void deleteById(Long id);
    boolean existePorId(Long id);
    List<GetHabitacion> findAll();
    List<GetHabitacion> filtrarPorTipoHabitacion(TipoHabitacion tipo);
    List<GetHabitacion> filtrarPorRangoDePrecio(Double precioMinimo, Double precioMaximo);
    List<GetHabitacion> filtrarPorDisponibilidadDeFechas(Timestamp fechaLlegada, Timestamp fechaSalida);
    GetFoto agregarFotoAHabitacion(Long idHabitacion, MultipartFile imagen);
    GetHabitacion updateHabitacion(HabitacionSave habitacionSave, Long idHabitacion);
}
