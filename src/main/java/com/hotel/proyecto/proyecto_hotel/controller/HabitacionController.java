package com.hotel.proyecto.proyecto_hotel.controller;

import com.hotel.proyecto.proyecto_hotel.dto.request.HabitacionSave;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetFoto;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHabitacion;
import com.hotel.proyecto.proyecto_hotel.exception.HabitacionNoEncontradaException;
import com.hotel.proyecto.proyecto_hotel.exception.ListaImagenesVaciaException;
import com.hotel.proyecto.proyecto_hotel.exception.NumeroHabitacionRepetidaException;
import com.hotel.proyecto.proyecto_hotel.exception.TipoImagenIncorrectoException;
import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import com.hotel.proyecto.proyecto_hotel.service.HabitacionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/habitaciones")
@AllArgsConstructor
public class HabitacionController {
    private HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<List<GetHabitacion>> listar() {
        return ResponseEntity.ok(habitacionService.findAll());
    }

    @PostMapping
    public Object guardar(@ModelAttribute HabitacionSave habitacionSave, @RequestParam(required = false) List<MultipartFile> imagenes) {
        try{
            return habitacionService.save(habitacionSave, imagenes);
        }catch (ListaImagenesVaciaException | TipoImagenIncorrectoException | NumeroHabitacionRepetidaException e){
            return "\"error\":\""+e.getMessage()+"\"";
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){

        if(!habitacionService.existePorId(id)){
            log.info("Habitacion con id {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }

        habitacionService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<GetHabitacion>> filtrarPorTipo(@PathVariable TipoHabitacion tipo) {
        List<GetHabitacion> habitaciones = habitacionService.filtrarPorTipoHabitacion(tipo);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<GetHabitacion>> filtrarPorRangoDePrecio(@RequestParam Double precioMinimo,@RequestParam Double precioMaximo) {

        List<GetHabitacion> habitaciones = habitacionService.filtrarPorRangoDePrecio(precioMinimo,precioMaximo);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/disponibilidad_fecha")
    public ResponseEntity<List<GetHabitacion>> filtrarPorDisponibilidadDeFechas(@RequestParam Timestamp fechaLlegada, @RequestParam Timestamp fechaSalida){
        List<GetHabitacion> habitaciones = habitacionService.filtrarPorDisponibilidadDeFechas(fechaLlegada,fechaSalida);

        return ResponseEntity.ok(habitaciones);
    }

    @PostMapping("/{idHabitacion}/fotos")
    public ResponseEntity<Object> agregarFotoAHabitacion(@RequestParam(required = false) MultipartFile imagen, @PathVariable Long idHabitacion) {
        try{
            GetFoto fotoGuardada = habitacionService.agregarFotoAHabitacion(idHabitacion,imagen);
            return ResponseEntity.ok(fotoGuardada);
        }catch (HabitacionNoEncontradaException | TipoImagenIncorrectoException e){
            String error= "\"error\":\""+e.getMessage()+"\"";
            return ResponseEntity.badRequest().body(error);
        }



    }

    @PutMapping("/{id}")
    public ResponseEntity<GetHabitacion> actualizarHabitacion(@RequestBody HabitacionSave habitacionSave,@PathVariable Long id){
        GetHabitacion habitacionActualizada = habitacionService.updateHabitacion(habitacionSave,id);
        return ResponseEntity.ok(habitacionActualizada);
    }


}
