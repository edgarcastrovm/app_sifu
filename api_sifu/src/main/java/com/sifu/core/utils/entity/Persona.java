package com.sifu.core.utils.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_persona", schema = "sifubd")
public class Persona {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String nombre;
    private String apellido;
    private Integer cedula;
    private String correo;
    private Integer celular;
    
    
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Cliente cliente;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Agricultor agricultor;
    
}
