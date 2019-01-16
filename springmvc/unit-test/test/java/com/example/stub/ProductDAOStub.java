package com.example.stub;

import java.math.BigDecimal;

import com.example.dao.ProductDAO;

public class ProductDAOStub implements ProductDAO {

	@Override
	public BigDecimal calculateDiscount() {
		// TODO Auto-generated method stub
		return new BigDecimal(14);
	}

	@Override
	public boolean isOnSale(int productId) {
		// TODO Auto-generated method stub
		return false;
	}

}
