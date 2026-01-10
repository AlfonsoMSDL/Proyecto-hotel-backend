package com.hotel.proyecto.proyecto_hotel.controller;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.service.FotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoto(@PathVariable Long id){

        try {
            fotoService.eliminarFotoDeHabitacion(id);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();

        }
    }

}
