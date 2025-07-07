package com.sifu.core.utils.entity;


import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_agricultor", schema = "sifubd")
public class Agricultor {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	 @OneToOne
	 @JoinColumn(name = "persona_id")
	 private Persona persona;
	
	 @OneToMany(mappedBy = "agricultor")
	 private List<Anuncio> anuncios;
	 
	 @OneToMany(mappedBy = "agricultor")
	 private List<AgriProd> productos;
}
