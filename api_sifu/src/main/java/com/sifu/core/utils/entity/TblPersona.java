package com.sifu.core.utils.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_persona")
public class TblPersona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_persona_id_gen")
    @SequenceGenerator(name = "tbl_persona_id_gen", sequenceName = "tbl_persona_per_id_seq", allocationSize = 1)
    @Column(name = "per_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "per_nombres", nullable = false, length = 100)
    private String perNombres;

    @Size(max = 100)
    @NotNull
    @Column(name = "per_apellidos", nullable = false, length = 100)
    private String perApellidos;

    @Size(max = 20)
    @Column(name = "per_identificacion", length = 20)
    private String perIdentificacion;

    @Size(max = 20)
    @Column(name = "per_telefono", length = 20)
    private String perTelefono;

    @Size(max = 100)
    @Column(name = "per_email", length = 100)
    private String perEmail;

    @Size(max = 255)
    @Column(name = "per_direccion")
    private String perDireccion;

    @Column(name = "per_fecha_nacimiento")
    private LocalDate perFechaNacimiento;

    @Size(max = 10)
    @Column(name = "per_genero", length = 10)
    private String perGenero;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "per_fecha_registro")
    private OffsetDateTime perFechaRegistro;

    @Size(max = 20)
    @ColumnDefault("'activo'")
    @Column(name = "per_estado", length = 20)
    private String perEstado;

}