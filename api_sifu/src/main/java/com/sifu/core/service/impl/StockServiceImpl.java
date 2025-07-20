package com.sifu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.AgriProdRepository;
import com.sifu.core.repo.ProductoRepository;
import com.sifu.core.repo.StockRepository;
import com.sifu.core.service.StockService;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Producto;
import com.sifu.core.utils.entity.Stock;

@Service
public class StockServiceImpl implements StockService{

	@Autowired
	private StockRepository stockRepository;

	@Override
	public Stock crearStock(Stock stock) {
		// TODO Auto-generated method stub
		
		return stockRepository.save(stock);
	}

	@Override
	public Stock actualizarStock(Integer id, Stock stock) {
		// TODO Auto-generated method stub
		Stock stockExistente = stockRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Stock no encontrado con ID: " + id));
		
		stockExistente.setCantidad(stock.getCantidad());
		stockExistente.setUniMedida(stock.getUniMedida());
		stockExistente.setFechaCreacion(stock.getFechaCreacion());
		
		return stockRepository.save(stock);
	}

	

	@Override
	public Stock findById(Integer id) {
		// TODO Auto-generated method stub
		return stockRepository.findById(id).orElse(null);
	}

	@Override
	public List<Stock> findByAgriProd_Id(Integer agriProdId) {
		// TODO Auto-generated method stub
		return stockRepository.findByAgriProd_Id(agriProdId);
	}
	
	
}
