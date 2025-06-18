package com.sifu.core.repo;

import com.sifu.core.utils.entity.TblProducto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TblProductoRepository extends JpaRepository<TblProducto, Integer> {
    List<TblProducto> findAllByCatRef_CatNombre(@Size(max = 100) @NotNull String catRefCatNombre);
}