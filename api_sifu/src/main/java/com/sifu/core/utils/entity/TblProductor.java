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
@Table(name = "tbl_productor")
public class TblProductor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_productor_id_gen")
    @SequenceGenerator(name = "tbl_productor_id_gen", sequenceName = "tbl_productor_pro_id_seq", allocationSize = 1)
    @Column(name = "pro_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "per_ref_id", nullable = false)
    private TblPersona perRef;

    @Size(max = 100)
    @NotNull
    @Column(name = "pro_nombre_contacto", nullable = false, length = 100)
    private String proNombreContacto;

    @Size(max = 100)
    @NotNull
    @Column(name = "pro_municipio", nullable = false, length = 100)
    private String proMunicipio;

    @Size(max = 100)
    @Column(name = "pro_parroquia", length = 100)
    private String proParroquia;

    @Size(max = 50)
    @NotNull
    @Column(name = "pro_tipo_produccion", nullable = false, length = 50)
    private String proTipoProduccion;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "pro_fecha_registro")
    private OffsetDateTime proFechaRegistro;

}