package com.sifu.core.repo;

import com.sifu.core.utils.entity.Producto;
import com.sifu.core.utils.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    boolean getProductosByNombre(String nombre);

    Producto findByNombre(String nombre);
}