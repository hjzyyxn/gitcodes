package com.example.spy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.example.Car;
import com.example.dao.GarageDAO;
import com.example.service.GarageService;
import com.example.service.GarageServiceImpl;

public class GarageServiceImplTest {
	
	@Test
	public void testRentCar() {
		GarageDAO garageDAO = new GarageDAOSpy();
		GarageService garageService = new GarageServiceImpl(garageDAO);
		Car car1 = garageService.rent();
		Car car2 = garageService.rent();
		Car car3 = garageService.rent();
		Car car4 = garageService.rent();
		
		assertNotNull(car1);
		assertNotNull(car2);
		assertNotNull(car3);
		assertNull(car4);
	}

}
