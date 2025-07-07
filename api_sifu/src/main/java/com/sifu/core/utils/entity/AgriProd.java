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
@Table(name = "tbl_agriProd", schema = "sifubd")
public class AgriProd {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "agricultor_id")
    private Agricultor agricultor;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToOne(mappedBy = "producto")
    private Stock stock;

    @OneToMany(mappedBy = "producto")
    private List<DetalleFact> detalleFact;

    @OneToMany(mappedBy = "producto")
    private List<DetalleCarrito> detalleCarrito;
    
   
}
