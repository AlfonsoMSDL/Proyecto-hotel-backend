package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.dto.request.HabitacionSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHabitacion;
import com.hotel.proyecto.proyecto_hotel.exception.ListaImagenesVaciaException;
import com.hotel.proyecto.proyecto_hotel.exception.NumeroHabitacionRepetidaException;
import com.hotel.proyecto.proyecto_hotel.exception.TipoImagenIncorrectoException;
import com.hotel.proyecto.proyecto_hotel.mapper.HabitacionMapper;
import com.hotel.proyecto.proyecto_hotel.model.Foto;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import com.hotel.proyecto.proyecto_hotel.repository.FotoRepository;
import com.hotel.proyecto.proyecto_hotel.repository.HabitacionRepository;
import com.hotel.proyecto.proyecto_hotel.service.HabitacionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {
    private final HabitacionMapper habitacionMapper;
    private final HabitacionRepository habitacionRepository;
    private final ManejadorImagenService manejadorImagen;
    private final FotoRepository fotoRepository;

    @Override
    @Transactional
    public GetHabitacion save(HabitacionSave habitacionSave, List<MultipartFile> imagenes)
            throws ListaImagenesVaciaException,
            TipoImagenIncorrectoException,
            NumeroHabitacionRepetidaException {

        //Primero pregunto si el numero de la habitacion ya esta en uso por otra habitacion
        if(habitacionRepository.existsByNumero(habitacionSave.numero())){
            //Si ya existe lanzo una excepcion
            log.info("Este numero de habitacion ya está en uso");
            throw new NumeroHabitacionRepetidaException("Este numero de habitacion ya está en uso");
        }

        //Luego convierto el habitacionSave a una entidad Habitacion
        Habitacion  habitacion = habitacionMapper.toHabitacionFromHabitacionSave(habitacionSave);

        //Guardo la habitacion
        Habitacion habitacionGuardada = habitacionRepository.save(habitacion);
        log.info("Se guardo la habitacion sin las imagenes.");

        /*Luego viene todo el proceso de cambiar el nombre, guardar las imagenes en una carpeta
        y crear la lista de fotos para asignaselo a la lista de imagenes de la habitacion*/
        try {
            List<Foto> fotosGuardar = manejadorImagen.guardarImagenesEnCarpeta(imagenes,habitacionGuardada);

            //ahora que tengo las fotos ya con la ruta y nombre de la imagen
            //Procedo a guardarlas en la bd
            habitacion.setFotos(fotosGuardar);
            log.info("Se asignan las fotos a la habitacion.");

            //La guardo de nuevo pero spring ya no va a detectar un INSERT sino un UPDATE
            //con las fotos
            habitacionRepository.save(habitacionGuardada);
            log.info("Se actualizo la habitacion con las imagenes.");


        } catch (IOException e) {
            log.info("Error guardando imágenes");
            throw new IllegalStateException("Error guardando imágenes", e);
            //Aparte de esta excepcion, debo controlar las que yo cree, asi que las voy a propagar
            //Para que el controller las maneje y envie los errores al cliente
        }

        return habitacionMapper.toGetHabitacion(habitacionGuardada);
    }

    @Override
    public Habitacion findById(Long id) {
        return habitacionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        //Primero busco la habitacion en la bd
        Habitacion habitacion = findById(id);

        //Ahora borro las fotos de esa habitacion de la carpeta uploads
        manejadorImagen.borrarImagenesPorHabitacion(habitacion);
        log.info("Se borraron las fotos de la carpeta de esa habitacion");

        //Luego borro la habitacion, al hacerlo tambien se borran las fotos
        //asociadas a esa habitacion en la bd

        habitacionRepository.delete(habitacion);
        log.info("Habitacion con id "+habitacion.getId()+" eliminada.");

    }

    public boolean existePorId(Long id) {
        return habitacionRepository.existsById(id);
    }

    @Override
    public List<GetHabitacion> findAll() {
        List<Habitacion> habitaciones = habitacionRepository.findAll();

        return habitacionMapper.toGetHabitacionList(habitaciones);
    }

    @Override
    public List<GetHabitacion> filtrarPorTipoHabitacion(TipoHabitacion tipo) {

        List<Habitacion> habitaciones = habitacionRepository.findByTipoHabitacion(tipo);

        return habitacionMapper.toGetHabitacionList(habitaciones);
    }

    //Este metodo filtra las habitaciones por su precio, ej:
    //Habitaciones con precio entre 15000 y 20000
    @Override
    public List<GetHabitacion> filtrarPorRangoDePrecio(Double precioMinimo, Double precioMaximo) {

        List<Habitacion> habitaciones = habitacionRepository.findByPrecioNocheBetween(precioMinimo,precioMaximo);

        return habitacionMapper.toGetHabitacionList(habitaciones);
    }


}
