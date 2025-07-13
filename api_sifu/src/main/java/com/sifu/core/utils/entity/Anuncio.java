package com.sifu.core.utils.entity;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_anuncio", schema = "sifubd")
public class Anuncio {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@NotBlank(message = "La descripción es obligatoria")
	private String descripcion;
	
	@NotBlank(message = "El estado es obligatorio")
	private String estado;
	
	 @CreationTimestamp
	 @Column(name = "fecha_creacion", updatable = false)
	 private LocalDateTime fechaCreacion;
	
	@ManyToOne
    @JoinColumn(name = "agricultor_id")
    private Agricultor agricultor;
}
