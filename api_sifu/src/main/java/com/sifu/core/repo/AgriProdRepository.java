package com.sifu.core.repo;

import com.sifu.core.utils.dto.ProductoDto;
import com.sifu.core.utils.entity.AgriProd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgriProdRepository extends JpaRepository<AgriProd, Integer> {
    List<AgriProd> findByAgricultor_Id(Integer agricultorId);

    List<AgriProd> findByProducto_Id(Integer productoId);
}