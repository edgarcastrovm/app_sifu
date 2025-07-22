package com.sifu.core.service;

import java.util.List;

import com.sifu.core.utils.entity.Stock;


public interface StockService {
	
	Stock crearStock(Stock stock) ;
	Stock actualizarStock(Integer id, Stock stock);
	Stock findById(Integer id);
	List<Stock> findByAgriProd_Id(Integer agriProdId);

}
