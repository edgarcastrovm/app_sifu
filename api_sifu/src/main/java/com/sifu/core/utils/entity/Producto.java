package com.sifu.core.utils.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_producto", schema = "sifubd")
public class Producto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private Double precio;
	private String nombre;
	
	@ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaProd categoriaProd;
	
}
