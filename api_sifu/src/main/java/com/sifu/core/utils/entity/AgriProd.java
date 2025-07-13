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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String iamge;

    @OneToMany(mappedBy = "producto")
    private List<DetalleFact> detalleFact;

    @OneToMany(mappedBy = "agriProd")
    private List<DetalleCarrito> detalleCarrito;
    
   
}
