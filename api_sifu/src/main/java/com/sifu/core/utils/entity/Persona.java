package com.sifu.core.utils.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 60)
    private String apellido;

    @Pattern(regexp = "\\d{10}", message = "El número de celular debe tener exactamente 10 dígitos")
    @Column(name = "cedula", nullable = false, length = 10)
    private String cedula;

    @Column(name = "correo", nullable = false, length = 50)
    private String correo;

    @Column(name = "celular", nullable = false, length = 20)
    private String celular;


    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonIgnore
    private Usuario usuario;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonIgnore
    private Cliente cliente;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonIgnore
    private Agricultor agricultor;

}
