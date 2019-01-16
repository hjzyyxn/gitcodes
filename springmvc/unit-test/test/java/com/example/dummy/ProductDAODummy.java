package com.example.dummy;

import java.math.BigDecimal;

import com.example.dao.ProductDAO;

public class ProductDAODummy implements ProductDAO {

	@Override
	public BigDecimal calculateDiscount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnSale(int productId) {
		// TODO Auto-generated method stub
		return false;
	}

}
