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
@Table(name = "tbl_usuario", schema = "sifubd")
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Column(unique = true, nullable = false, length = 32)
	private String alias;

    @Column(length = 100)
    private String clave;

    private Boolean activo;
	
	@OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
    

}
