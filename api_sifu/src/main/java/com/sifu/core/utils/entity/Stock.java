package com.sifu.core.utils.entity;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_stock", schema = "sifubd")
public class Stock {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private Integer cantidad;
	private String uniMedida;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fechaCreacion;
	
	@OneToOne
    @JoinColumn(name = "producto_id")
    private AgriProd producto;
	
}
