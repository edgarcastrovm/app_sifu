package com.sifu.core.utils.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_oferta_producto")
public class TblOfertaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_oferta_producto_id_gen")
    @SequenceGenerator(name = "tbl_oferta_producto_id_gen", sequenceName = "tbl_oferta_producto_ofe_id_seq", allocationSize = 1)
    @Column(name = "ofe_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pro_ref_id", nullable = false)
    private TblProductor proRef;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "prd_ref_id", nullable = false)
    private TblProducto prdRef;

    @Size(max = 20)
    @NotNull
    @Column(name = "ofe_unidad_medida", nullable = false, length = 20)
    private String ofeUnidadMedida;

    @NotNull
    @Column(name = "ofe_precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal ofePrecio;

    @NotNull
    @Column(name = "ofe_cantidad_disponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal ofeCantidadDisponible;

    @Size(max = 255)
    @Column(name = "ofe_url_foto")
    private String ofeUrlFoto;

    @Size(max = 20)
    @ColumnDefault("'activo'")
    @Column(name = "ofe_estado", length = 20)
    private String ofeEstado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "ofe_fecha_creacion")
    private OffsetDateTime ofeFechaCreacion;

    @Column(name = "ofe_ultima_actualizacion")
    private OffsetDateTime ofeUltimaActualizacion;

}