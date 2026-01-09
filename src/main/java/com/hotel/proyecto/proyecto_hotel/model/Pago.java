package com.hotel.proyecto.proyecto_hotel.model;

import com.hotel.proyecto.proyecto_hotel.model.enums.EstadoPago;
import com.hotel.proyecto.proyecto_hotel.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pago implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago")
    private EstadoPago estadoPago;

    private double monto;

    @CreationTimestamp
    @Column(name = "fecha_pago")
    private Timestamp fechaPago;

    @OneToOne()
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;
}
