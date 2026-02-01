package com.hotel.proyecto.proyecto_hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsuarioLoginClaveErroneaException.class,UsuarioLoginNoEncontradoException.class})
    ResponseEntity<?> errorCredencialesIncorrectas(Exception ex) {
        Map<String,String> errors = new HashMap<>();
        errors.put("mensaje", "Credenciales incorrectas");
        errors.put("error", ex.getMessage());
        errors.put("codigo", HttpStatus.UNAUTHORIZED.toString());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(UsuarioRegistrarCorreoExistenteExcepcion.class)
    ResponseEntity<?> errorCorreoExistente(Exception ex) {
        Map<String,String> errors = new HashMap<>();
        errors.put("mensaje", "Error al insertar el usuario");
        errors.put("error", ex.getMessage());
        errors.put("codigo", HttpStatus.CONFLICT.toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler({ListaImagenesVaciaException.class,TipoImagenIncorrectoException.class})
    ResponseEntity<?> errorEnImagenes(Exception ex) {
        Map<String,String> errors = new HashMap<>();
        errors.put("mensaje", "Error en el manejo de imagenes");
        errors.put("error", ex.getMessage());
        errors.put("codigo", HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

    @ExceptionHandler(NumeroHabitacionRepetidaException.class)
    ResponseEntity<?> errorEnElNumeroDeLaHabitacion(Exception ex) {
        Map<String,String> errors = new HashMap<>();
        errors.put("mensaje", "Error en el numero de la habitacion");
        errors.put("error", ex.getMessage());
        errors.put("codigo", HttpStatus.CONFLICT.toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(HabitacionNoEncontradaException.class)
    ResponseEntity<?> errorHabitacionNoEncontrada(Exception ex) {
        Map<String,String> errors = new HashMap<>();
        errors.put("mensaje", "Error al buscar la habitacion");
        errors.put("error", ex.getMessage());
        errors.put("codigo", HttpStatus.NOT_FOUND.toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(ReservaCruzadaException.class)
    ResponseEntity<?> errorReservaCruzada(Exception ex) {
        Map<String,String> errors = new HashMap<>();
        errors.put("mensaje", "Error al crear la reserva");
        errors.put("error", ex.getMessage());
        errors.put("codigo", HttpStatus.CONFLICT.toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(FechaLlegadaNoIgualFechaActualException.class)
    ResponseEntity<?> errorEnFechasEntrada(Exception ex) {
        Map<String,String> errors = new HashMap<>();
        errors.put("mensaje", "Error al marcar la entrada");
        errors.put("error", ex.getMessage());
        errors.put("codigo", HttpStatus.CONFLICT.toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

}
