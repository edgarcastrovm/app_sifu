package com.sifu.core.repo;

import com.sifu.core.utils.dto.ProductoDto;
import com.sifu.core.utils.entity.AgriProd;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgriProdRepository extends JpaRepository<AgriProd, Integer> {
    List<AgriProd> findByAgricultor_Id(Integer agricultorId);

    List<AgriProd> findByProducto_Id(Integer productoId);

    List<AgriProd> findByAgricultor_Id(Integer agricultorId, Limit limit);

    Optional<AgriProd> findTopByAgricultor_Id(Integer agricultorId);

    Optional<AgriProd> findTopByIdAndAgricultor_Id(Integer id, Integer agricultorId);

    Optional<AgriProd> findTopByProducto_IdAndAgricultor_Id(Integer productoId, Integer agricultorId);
}