package com.sifu.core.utils.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_catalogo")
public class TblCatalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_catalogo_id_gen")
    @SequenceGenerator(name = "tbl_catalogo_id_gen", sequenceName = "tbl_catalogo_cat_id_seq", allocationSize = 1)
    @Column(name = "cat_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "cat_nombre", nullable = false, length = 100)
    private String catNombre;

    @Column(name = "cat_descripcion", length = Integer.MAX_VALUE)
    private String catDescripcion;

    @Column(name = "cat_padre_id")
    private Integer catPadre;

    @Size(max = 20)
    @ColumnDefault("'Activo'")
    @Column(name = "cat_estado", length = 20)
    private String catEstado;

}