package com.sifu.core.utils.dto.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualizarClienteDto {
	
	 private Integer id;
	 @NotBlank(message = "El nombre es obligatorio")
	 private String nombre;
	 
	 @NotBlank(message = "El apellido es obligatorio")
	 private String apellido;
	 
	 @NotBlank(message = "El correo es obligatorio")
	 @Email(message = "Formato de correo inválido")
	 private String correo;
	 
	 @NotBlank(message = "La cedula es obligatorio")
	 @Size(min = 10, max = 10, message = "La cédula debe tener 10 dígitos")
	 private String cedula;
	 
	 @NotBlank(message = "El celular es obligatorio")
	 @Size(min = 10, max = 10, message = "El celular debe tener 10 dígitos")
	 private String celular;
	 
	 @Column(name = "provincia", nullable = false, length = 60)
	 private String provincia;
	    
	 @Column(name = "canton", nullable = false, length = 60)
	 private String canton;
	 
	 private Boolean entidadSFL;
	 
	 public ActualizarClienteDto(Object entity) {
	        nombre = null;
	        apellido = null;
	        correo = null;
	        cedula = null;
	        celular = null;
	        entidadSFL = true;
	        provincia= null;
	        canton = null;
	    }

}
