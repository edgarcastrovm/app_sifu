package com.sifu.core.repo;

import com.sifu.core.utils.entity.TblOfertaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TblOfertaProductoRepository extends JpaRepository<TblOfertaProducto, Integer> {
    List<TblOfertaProducto> findAllByPrdRef_Id(Integer prdRefId);
}