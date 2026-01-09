package com.hotel.proyecto.proyecto_hotel.model;

import com.hotel.proyecto.proyecto_hotel.model.enums.TipoHabitacion;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "habitaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Habitacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_habitacion")
    private TipoHabitacion tipoHabitacion;

    @Column(name = "precio_noche")
    private double precioNoche;
    private int capacidad;
    private String descripcion;

    @ToString.Exclude
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL)
    private List<Foto> fotos;

    @ToString.Exclude
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    public void addFoto(Foto foto) {
        fotos.add(foto);
        foto.setHabitacion(this);
    }




}
