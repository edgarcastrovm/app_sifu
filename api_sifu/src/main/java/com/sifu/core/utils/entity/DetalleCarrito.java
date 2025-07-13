package com.sifu.core.utils.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_detCar", schema = "sifubd")
public class DetalleCarrito {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private Integer cantidad;
	private double subtotal;
	
	@ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "agri_prod_id")
    private AgriProd agriProd;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
