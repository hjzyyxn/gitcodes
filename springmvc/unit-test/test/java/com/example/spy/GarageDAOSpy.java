package com.example.spy;

import com.example.Car;
import com.example.dao.GarageDAO;

public class GarageDAOSpy implements GarageDAO {
	
	private int carCount = 3;

	@Override
	public Car rent() {
		// TODO Auto-generated method stub
		if (carCount == 0) {
			return null;
		} else {
			carCount--;
			return new Car();
		}
	}

}
