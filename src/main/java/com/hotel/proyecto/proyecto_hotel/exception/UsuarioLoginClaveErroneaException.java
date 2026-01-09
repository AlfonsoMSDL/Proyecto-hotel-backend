package com.hotel.proyecto.proyecto_hotel.exception;

public class UsuarioLoginClaveErroneaException extends RuntimeException {
    public UsuarioLoginClaveErroneaException(String claveIncorrecta) {
        super(claveIncorrecta);
    }
}
