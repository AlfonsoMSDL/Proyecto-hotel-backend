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

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class FotoServiceImpl implements FotoService {
    private FotoRepository fotoRepository;
    private FotoMapper fotoMapper;

    @Override
    public List<GetFoto> findByIdHabitacion(Long idHabitacion) {

        List<Foto> fotos = fotoRepository.findByIdHabitacion(idHabitacion);



        return fotoMapper.toGetFotoList(fotos);
    }
}
