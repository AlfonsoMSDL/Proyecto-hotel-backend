package com.hotel.proyecto.proyecto_hotel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expone la carpeta física `uploads/` para acceso público
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/app/uploads/"); //Esta carpeta esta dentro del contenedor de docker asi que uso la ruta absoluta

        //Recordar que todo el proyecto esta dentro de un contenedor docker
    }
}
