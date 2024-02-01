package com.example.demo.models;

import com.example.demo.dto.ProductoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

import java.math.BigInteger;

@Data
@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "cantidad")
    private int cantidad;

    public Producto(ProductoDTO productoDTO){
        this.id = productoDTO.getId();
        this.nombre = productoDTO.getNombre();
        this.descripcion = productoDTO.getDescripcion();
        this.precio = productoDTO.getPrecio();
        this.cantidad = productoDTO.getCantidad();
    }


    public ProductoDTO toDTO(){
        return
                ProductoDTO
                        .builder()
                        .id(this.id)
                        .nombre(this.nombre)
                        .descripcion(this.descripcion)
                        .precio(this.precio)
                        .cantidad(this.cantidad)
                        .build();
    }
}
