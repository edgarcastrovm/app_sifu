package com.sifu.core.repo;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.utils.entity.TblUsuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TblUsuarioRepository extends JpaRepository<TblUsuario, Integer> {

    Optional<TblUsuario> findByUsuNombreUsuarioAndUsuPasswordHash(@Size(max = 50) @NotNull String usuNombreUsuario, @Size(max = 255) @NotNull String usuPasswordHash);
}