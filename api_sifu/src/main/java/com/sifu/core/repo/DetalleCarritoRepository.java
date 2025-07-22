package com.sifu.core.repo;

import com.sifu.core.utils.entity.Carrito;
import com.sifu.core.utils.entity.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Integer> {
    List<DetalleCarrito> getDetalleCarritoByCarrito(Carrito carrito);

    List<DetalleCarrito> getDetalleCarritoByEstadoAndCarrito(Integer estado, Carrito carrito);
}