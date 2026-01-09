package com.hotel.proyecto.proyecto_hotel.controller;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.service.FotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fotos")
@AllArgsConstructor
public class FotoController {
    private FotoService fotoService;

    @GetMapping("/habitacion/{idHabitacion}")
    public ResponseEntity<List<GetFoto>> getFotosByIdHabitacion(@PathVariable Long idHabitacion){
        List<GetFoto> fotos =  fotoService.findByIdHabitacion(idHabitacion);
        return ResponseEntity.ok(fotos);

    }

}
