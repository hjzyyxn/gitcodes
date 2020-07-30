package com.example.service;

import java.math.BigDecimal;

import com.example.dao.ProductDAO;

public class ProductServiceImpl implements ProductService {
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl(ProductDAO productDAOArg) {
		if (productDAOArg == null) {
			throw new NullPointerException("ProductDAO cannot be null.");
		}
		this.productDAO = productDAOArg;
	}

	@Override
	public BigDecimal calculateDiscount() {
		// TODO Auto-generated method stub
		return productDAO.calculateDiscount();
	}

	@Override
	public boolean isOnSale(int productId) {
		// TODO Auto-generated method stub
		return productDAO.isOnSale(productId);
	}

}
