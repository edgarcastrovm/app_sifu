package com.sifu.core.repo;

import com.sifu.core.utils.entity.AgriProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgriProdRepository extends JpaRepository<AgriProd, Integer> {
	
	@Query("SELECT a FROM AgriProd a WHERE a.agricultor.id = :agricultorId")
    List<AgriProd> findByAgricultor_Id(Integer agricultorId);

    List<AgriProd> findByProducto_Id(Integer productoId);
    
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AgriProd a " +
            "WHERE a.agricultor.id = :agricultorId AND a.producto.nombre = :nombreProducto AND a.id <> :excludeId")
     boolean existsByAgricultorIdAndProductoNombreExceptId(@Param("agricultorId") Integer agricultorId,
                                                          @Param("nombreProducto") String nombreProducto,
                                                          @Param("excludeId") Integer excludeId);

     // Para creación sin excluir id
     @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AgriProd a " +
            "WHERE a.agricultor.id = :agricultorId AND a.producto.nombre = :nombreProducto")
     boolean existsByAgricultorIdAndProductoNombre(@Param("agricultorId") Integer agricultorId,
                                                   @Param("nombreProducto") String nombreProducto);
}