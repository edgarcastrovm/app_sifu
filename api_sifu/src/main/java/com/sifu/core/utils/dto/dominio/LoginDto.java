package com.sifu.core.utils.dto.dominio;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sifu.core.utils.entity.TblUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {
    private Integer id;
    private String email;
    private String password;
    private String rol;
    private String token;

    public LoginDto(TblUsuario entity) {
        id = entity.getId();
        email = entity.getUsuNombreUsuario();
        password = entity.getUsuPasswordHash();
        rol = entity.getUsuRol();
    }


}
