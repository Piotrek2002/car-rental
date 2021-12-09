package com.carrental.carrental.service;

import com.carrental.carrental.model.Car;
import org.springframework.http.ResponseEntity;

public interface CarService {
    ResponseEntity<?> saveCar(Car car);
    ResponseEntity<?> deleteCar(Car car);
    ResponseEntity<?> updateCar(Car car);
    ResponseEntity<?> rentCar(Car car);
    ResponseEntity<?> returnCar(Car car);
}
