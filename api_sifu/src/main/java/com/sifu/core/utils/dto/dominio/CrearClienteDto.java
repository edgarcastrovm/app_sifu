package com.sifu.core.utils.dto.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearClienteDto {
	
	private String nombre;
	 private String apellido;
	 private String correo;
	 private String cedula;
	 private String celular;
	 private String alias;
	 private String clave;
	 private Boolean activo;
	 private Integer rol;
	 private Boolean entidadSFL;
	 
	 
	 
	 public CrearClienteDto(Object entity) {
	        nombre = null;
	        apellido = null;
	        correo = null;
	        cedula = null;
	        celular = null;
	        alias =  null;
	        clave = null;
	        activo = true;    
	        rol = 2;
	        entidadSFL = true;
	    }

}
