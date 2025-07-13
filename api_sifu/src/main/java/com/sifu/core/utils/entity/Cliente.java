package com.sifu.core.utils.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_cliente", schema = "sifubd")
public class Cliente {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private Boolean entidadSFL;
	
	@OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

	@OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Factura> factura;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Carrito> carrito;
}
