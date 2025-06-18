package com.sifu.core.utils.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_producto")
public class TblProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_producto_id_gen")
    @SequenceGenerator(name = "tbl_producto_id_gen", sequenceName = "tbl_producto_prd_id_seq", allocationSize = 1)
    @Column(name = "prd_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "prd_nombre", nullable = false, length = 100)
    private String prdNombre;

    @Column(name = "prd_descripcion", length = Integer.MAX_VALUE)
    private String prdDescripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "cat_ref_id", nullable = false)
    private TblCatalogo catRef;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "prd_fecha_creacion")
    private OffsetDateTime prdFechaCreacion;

}