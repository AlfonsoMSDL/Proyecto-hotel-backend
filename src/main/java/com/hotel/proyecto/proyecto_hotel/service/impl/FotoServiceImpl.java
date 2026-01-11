package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.mapper.FotoMapper;
import com.hotel.proyecto.proyecto_hotel.model.Foto;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import com.hotel.proyecto.proyecto_hotel.repository.FotoRepository;
import com.hotel.proyecto.proyecto_hotel.service.FotoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class FotoServiceImpl implements FotoService {
    private FotoRepository fotoRepository;
    private FotoMapper fotoMapper;
    private ManejadorImagenService manejadorImagen;

    @Override
    public List<GetFoto> findByIdHabitacion(Long idHabitacion) {

        List<Foto> fotos = fotoRepository.findByIdHabitacion(idHabitacion);
        return fotoMapper.toGetFotoList(fotos);
    }


    //Este metodo va a borrar una foto de una habitacion existente
    @Override
    public void eliminarFotoDeHabitacion(Long idFoto) throws IOException {
        //Primero busco la foto que se va a eliminar
        Foto fotoEliminar = fotoRepository.findById(idFoto).orElse(null);

        //Luego debo eliminar la foto de la carpeta uploads
        //Como ya la foto tiene la ruta con la habitacion incluida, no es necesario mandar el id de la habitacion como argumento
        manejadorImagen.borrarImagenDeHabitacion(fotoEliminar);

        //Luego de borrar la imagen de la carpeta, se borra de la bd
        fotoRepository.delete(fotoEliminar);
        log.info("Foto eliminada de la base de datos.");
    }
}
