package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.dto.request.HabitacionSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHabitacion;
import com.hotel.proyecto.proyecto_hotel.exception.ListaImagenesVaciaException;
import com.hotel.proyecto.proyecto_hotel.exception.NumeroHabitacionRepetidaException;
import com.hotel.proyecto.proyecto_hotel.exception.HabitacionNoEncontradaException;
import com.hotel.proyecto.proyecto_hotel.exception.TipoImagenIncorrectoException;
import com.hotel.proyecto.proyecto_hotel.mapper.FotoMapper;
import com.hotel.proyecto.proyecto_hotel.mapper.HabitacionMapper;
import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.EstadoHabitacion;
import com.hotel.proyecto.proyecto_hotel.model.Foto;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import com.hotel.proyecto.proyecto_hotel.repository.FotoRepository;
import com.hotel.proyecto.proyecto_hotel.repository.HabitacionRepository;
import com.hotel.proyecto.proyecto_hotel.service.EstadoDeLaHabitacionService;
import com.hotel.proyecto.proyecto_hotel.service.EstadoHabitacionService;
import com.hotel.proyecto.proyecto_hotel.service.HabitacionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {
    private final HabitacionMapper habitacionMapper;
    private final HabitacionRepository habitacionRepository;
    private final ManejadorImagenService manejadorImagen;
    private final FotoMapper fotoMapper;
    private final FotoRepository fotoRepository;
    private final EstadoHabitacionService estadoHabitacionService;
    private final EstadoDeLaHabitacionService estadoDeLaHabitacionService;

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

            //Ahora que la habitacion se guardo, debo asignar un estado de habitacion "Disponible"
            //Traigo el estado de habitacion Disponible
            EstadoHabitacion estadoHabitacion = estadoHabitacionService.findByNombre("Disponible");

            //Luego relaciono ese estado con la habitacion
            EstadoDeLaHabitacion estadoDeLaHabitacion = new EstadoDeLaHabitacion();
            estadoDeLaHabitacion.setEstadoHabitacion(estadoHabitacion);
            estadoDeLaHabitacion.setHabitacion(habitacionGuardada);

            estadoDeLaHabitacionService.save(estadoDeLaHabitacion);
            log.info("Se le asigno a la habitacion un estado de Disponible.");


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
        log.info("Habitacion con id {} eliminada.", habitacion.getId());

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

    @Override
    public List<GetHabitacion> filtrarPorDisponibilidadDeFechas(Timestamp fechaLlegada, Timestamp fechaSalida) {
        List<Habitacion> habitaciones = habitacionRepository.findHabitacionesDisponiblesEnRango(fechaLlegada,fechaSalida);
        return habitacionMapper.toGetHabitacionList(habitaciones);
    }

    @Override
    @Transactional
    public GetFoto agregarFotoAHabitacion(Long idHabitacion, MultipartFile imagen) throws HabitacionNoEncontradaException,TipoImagenIncorrectoException {
        //Busco la habitacion a la que le voy a agregar la foto
        Habitacion  habitacion = habitacionRepository.findById(idHabitacion).orElse(null);

        if (habitacion == null) {
            log.info("Habitacion no encontrada.");
            throw new HabitacionNoEncontradaException("Habitacion no encontrada");
        }


        //Luego llamo al metodo que guarda la imagen en la carpeta de la habitacion
        Foto fotoGuardar = manejadorImagen.guardarImagenEnCarpeta(imagen,habitacion);

        //Guardo la foto en la bd, esta ya tiene agregada como atributo la habitacion dueña de la foto
        //Por lo tanto spring data se va a encargar de enlazar en a bd la foto con la habitacion son su llave foranea
        Foto fotoGuardada = fotoRepository.save(fotoGuardar);
        log.info("La foto se ha guardado correctamente en la bd");


        //Mapeo la imagen a un dto
        return fotoMapper.toGetFoto(fotoGuardada);
    }

    @Transactional
    @Override
    public GetHabitacion updateHabitacion(HabitacionSave habitacionSave, Long idHabitacion) throws HabitacionNoEncontradaException, NumeroHabitacionRepetidaException {
        //Primero busco la habitacion
        Habitacion  habitacion = habitacionRepository.findById(idHabitacion).orElse(null);

        if (habitacion == null) {
            log.info("Habitacion no encontrada.");
            throw new HabitacionNoEncontradaException("Habitacion no encontrada");
        }
        log.info("Datos actuales de la habitacion "+habitacion);
        //Le asigno los nuevos datos a la habitacion
        asignarDatosActualizados(habitacion,habitacionSave);

        //Actualizo la habitacion con los nuevos datos
        habitacion = habitacionRepository.save(habitacion);
        log.info("La habitacion se ha actualizado correctamente");
        log.info("Nuevos datos de la habitacion "+habitacion);


        return habitacionMapper.toGetHabitacion(habitacion);
    }

    private void asignarDatosActualizados(Habitacion habitacion, HabitacionSave habitacionSave) throws NumeroHabitacionRepetidaException {
        //Primero pregunto si el nuevo numero de la habitacion ya esta en uso por otra habitacion
        if(habitacionRepository.existsByNumero(habitacionSave.numero())){
            //Si ya existe lanzo una excepcion
            log.info("Este numero de habitacion ya está en uso");
            throw new NumeroHabitacionRepetidaException("Este numero de habitacion ya está en uso");
        }
        habitacion.setNumero(habitacionSave.numero());
        habitacion.setPrecioNoche(habitacionSave.precioNoche());
        habitacion.setTipoHabitacion(habitacionSave.tipoHabitacion());
        habitacion.setCapacidad(habitacionSave.capacidad());
        habitacion.setDescripcion(habitacionSave.descripcion());

    }


}
