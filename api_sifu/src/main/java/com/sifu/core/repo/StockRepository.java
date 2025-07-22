package com.sifu.core.repo;

import com.sifu.core.utils.entity.Stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
	
	List<Stock> findByAgriProd_Id(Integer agriProdId);
}