package com.sifu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.AgriProdRepository;
import com.sifu.core.repo.AgricultorRepository;
import com.sifu.core.repo.ProductoRepository;
import com.sifu.core.repo.StockRepository;
import com.sifu.core.service.AgriProdService;
import com.sifu.core.utils.entity.AgriProd;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Producto;
import com.sifu.core.utils.entity.Stock;

@Service
public class AgriProdServiceImpl implements AgriProdService{

	@Autowired
	private AgriProdRepository agriProdRepository;
	@Autowired
	private AgricultorRepository agricultorRepository;
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private StockRepository stockRepository;
	
	@Override
	public AgriProd crearAgriProd(AgriProd agriProd) {
		// TODO Auto-generated method stub
		
		Agricultor agricultor = agricultorRepository.findById(agriProd.getAgricultor().getId())
	            .orElseThrow(() -> new IllegalArgumentException("El agricultor no existe"));

	    // Verificar que el producto existe
	    Producto producto = productoRepository.findById(agriProd.getProducto().getId())
	            .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));

	    // Validar que ese agricultor NO tenga ya registrado ese producto por su nombre
	    boolean existe = agriProdRepository.existsByAgricultorIdAndProductoNombre(
	            agricultor.getId(),
	            producto.getNombre()
	    );

	    if (existe) {
	        throw new IllegalArgumentException("El agricultor ya tiene un producto con ese nombre");
	    }

	    // Asociar correctamente agricultor y producto
	    agriProd.setAgricultor(agricultor);
	    agriProd.setProducto(producto);

	    // Validar stock
	    Stock stock = agriProd.getStock();
	    if (stock == null) {
	        throw new IllegalArgumentException("Debe proporcionar stock");
	    }

	    // Asociar stock bidireccionalmente
	    stock.setAgriProd(agriProd);
	    agriProd.setStock(stock);

	    // Guardar AgriProd
	    return agriProdRepository.save(agriProd);
	}
	
	@Override
	public AgriProd actualizarAgriProd(Integer id, AgriProd agriProd) {
		
		AgriProd agriProdE = agriProdRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

	    // Actualiza solo precio
		agriProdE.getProducto().setPrecio(agriProd.getProducto().getPrecio());

	    // Actualiza stock
	    Stock stock = agriProd.getStock();
	    
	    stock.setCantidad(agriProd.getStock().getCantidad());

	    return agriProdRepository.save(agriProd);
	}
	
	
	@Override
	public void eliminarAgriProd(Integer id) {
		// TODO Auto-generated method stub
		
		Optional<AgriProd> agriProd = agriProdRepository.findById(id);
	    if (agriProd.isPresent()) {
	        agriProdRepository.deleteById(id);
	    } else {
	        throw new RuntimeException("Producto no encontrado");
	    }
		
	}
	@Override
	public List<AgriProd> findByAgricultor_Id(Integer agricultorId) {
		// TODO Auto-generated method stub
		return agriProdRepository.findByAgricultor_Id(agricultorId);
	}
	@Override
	public AgriProd obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return agriProdRepository.findById(id).orElse(null);
	}

	@Override
	public boolean existsByAgricultorIdAndProductoNombre(Integer agricultorId, String nombreProducto) {
		// TODO Auto-generated method stub
		return agriProdRepository.existsByAgricultorIdAndProductoNombre(agricultorId, nombreProducto);
	}

	@Override
	public boolean existsByAgricultorIdAndProductoNombreExceptId(Integer agricultorId, String nombreProducto,
			Integer excludeId) {
		// TODO Auto-generated method stub
		return agriProdRepository.existsByAgricultorIdAndProductoNombreExceptId(agricultorId, nombreProducto, excludeId);
	}
	

	
	
	
}
