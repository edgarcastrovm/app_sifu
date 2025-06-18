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
@Table(name = "tbl_usuario")
public class TblUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_usuario_id_gen")
    @SequenceGenerator(name = "tbl_usuario_id_gen", sequenceName = "tbl_usuario_usu_id_seq", allocationSize = 1)
    @Column(name = "usu_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "per_ref_id", nullable = false)
    private TblPersona perRef;

    @Size(max = 50)
    @NotNull
    @Column(name = "usu_nombre_usuario", nullable = false, length = 50)
    private String usuNombreUsuario;

    @Size(max = 255)
    @NotNull
    @Column(name = "usu_password_hash", nullable = false)
    private String usuPasswordHash;

    @Size(max = 50)
    @ColumnDefault("'cliente'")
    @Column(name = "usu_rol", length = 50)
    private String usuRol;

    @Column(name = "usu_fecha_ultimo_login")
    private OffsetDateTime usuFechaUltimoLogin;

    @Size(max = 20)
    @ColumnDefault("'activo'")
    @Column(name = "usu_estado", length = 20)
    private String usuEstado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "usu_fecha_creacion")
    private OffsetDateTime usuFechaCreacion;

}