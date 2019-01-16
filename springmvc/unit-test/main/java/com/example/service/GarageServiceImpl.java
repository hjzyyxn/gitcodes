package com.example.service;

import com.example.Car;
import com.example.dao.GarageDAO;

public class GarageServiceImpl implements GarageService {
	
	private GarageDAO garageDAO;
	public GarageServiceImpl(GarageDAO garageDAOArg) {
		this.garageDAO = garageDAOArg;
	}

	@Override
	public Car rent() {
		// TODO Auto-generated method stub
		return garageDAO.rent();
	}

}
