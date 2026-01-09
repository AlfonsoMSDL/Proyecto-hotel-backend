package com.hotel.proyecto.proyecto_hotel.service.impl;

import com.hotel.proyecto.proyecto_hotel.exception.ListaImagenesVaciaException;
import com.hotel.proyecto.proyecto_hotel.exception.TipoImagenIncorrectoException;
import com.hotel.proyecto.proyecto_hotel.model.Foto;
import com.hotel.proyecto.proyecto_hotel.model.Habitacion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class ManejadorImagenService {
    private final String UPLOAD_DIR = "uploads/habitaciones/";


    public List<Foto> guardarImagenesEnCarpeta(List<MultipartFile> imagenes, Habitacion habitacion) throws ListaImagenesVaciaException, TipoImagenIncorrectoException, IOException {

        //Creo la lista de fotos para ir agregando los datos mas adelante
        List<Foto> datosFotos = new ArrayList<>();
        //Luego creo una lista con las imagenes que se guardan en disco
        List<Path> archivosGuardados = new ArrayList<>();

        //Primero compruebo que haya al menos un archivo en la lista
        if(imagenes == null || imagenes.isEmpty()){
            log.info("Debe subir al menos una imagen");
            throw new ListaImagenesVaciaException("Debe subir al menos una imagen");
        }

        //Obtengo la ruta para guardar las imagenes
        //Ej: uploads/habitaciones/2/  donde 2 es la carpeta para las fotos de una habitacion con id 2

        Path rutaHabitacion = Paths.get(UPLOAD_DIR + habitacion.getId());

        //Si la ruta no exite la creo
        if (!Files.exists(rutaHabitacion)) {
            //Esto me puede mandar una excepcion al haber un error creando la carpeta
            //Esa excepcion la propago. IOException
            log.info("La carpeta para las fotos de la habitacion no existe, asi que se crea");
            Files.createDirectories(rutaHabitacion);
        }

        for(MultipartFile imagen : imagenes){
            //Compruebo que el archivo actual sea una imagen
            if(imagen == null || !imagen.getContentType().startsWith("image/")){
                //BORRAR ARCHIVOS YA CREADOS
                for (Path p : archivosGuardados) {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException ex) {
                        // loggear, NO relanzar
                    }
                }
                log.info("El archivo no es una imagen. Se borran los archivos previamente creados");
                //Al final se borra la carpeta que se creo para esa habitacion ya que va a quedar vacia sin fotos
                Files.deleteIfExists(rutaHabitacion);

                throw new TipoImagenIncorrectoException("El archivo no es una imagen");
            }

            //Luego le cambio el nombre a la imagen
            String extension = FilenameUtils.getExtension(imagen.getOriginalFilename());
            String nuevoNombre = UUID.randomUUID() + "." + extension;

            //Luego pego la ruta de la habitacion con el nuevo nombre de la imagen
            //Puede quedar asi: uploads/habitaciones/2/8f3a1c5e-91f2-4b2c-b7a2-7e1e9c.jpg
            Path rutaFinal = rutaHabitacion.resolve(nuevoNombre);


            //Ahora tomo la imagen y la guardo en disco en la ruta final con el nuevo nombre
            imagen.transferTo(rutaFinal);
            log.info("Imagen guardada en la ruta: "+rutaFinal.toString());
            //La agrego a la lista de imagenes guardadas
            archivosGuardados.add(rutaFinal);


            datosFotos.add(new Foto(nuevoNombre,rutaHabitacion.toString(),habitacion));

        }
        return datosFotos;

    }

    public void borrarImagenesPorHabitacion(Habitacion habitacion){

        //Uso la ruta de las habitaciones mas su id para eliminar todas
        //las fotos de esa carpeta, incluida la carpeta

        Path rutaHabitacion = Paths.get(UPLOAD_DIR + habitacion.getId());

        //uso la clase Files para eliminar primero las fotos y despues
        //la carpeta contenedora
        try {
            Files.walk(rutaHabitacion)
                    .sorted(Comparator.reverseOrder()) //Ordeno las rutas de abajo a arriba
                    .forEach(ruta ->{
                        try {
                            //Elimino el archivo uno por uno
                            Files.delete(ruta);
                            log.info("Borrando "+ruta);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            log.info("Fotos de la habitacion con id "+habitacion.getId()+" borradas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
