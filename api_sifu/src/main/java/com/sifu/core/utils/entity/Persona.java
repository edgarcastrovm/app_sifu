package com.sifu.core.utils.entity;

import jakarta.persistence.*;
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

    @Column(name = "cedula", nullable = false, length = 15)
    private String cedula;

    @Column(name = "correo", nullable = false, length = 50)
    private String correo;

    @Column(name = "celular", nullable = false, length = 20)
    private String celular;


    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Cliente cliente;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Agricultor agricultor;

}
