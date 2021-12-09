package com.carrental.carrental.controller;

import com.carrental.carrental.model.Car;
import com.carrental.carrental.repository.CarRepository;
import com.carrental.carrental.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CarController {

    private final CarRepository carRepository;
    private final CarService carService;

    public CarController(CarRepository carRepository, CarService carService) {
        this.carRepository = carRepository;
        this.carService = carService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(carRepository.findAll());
    }
    @GetMapping("/findAllCarsToRent")
    public ResponseEntity<?> findAllCarsToRent(){
        return ResponseEntity.ok(carRepository.findAllCarsToRent());
    }
    @GetMapping("/findAllRentedCars")
    public ResponseEntity<?> findAllRentedCars(){
        return ResponseEntity.ok(carRepository.findAllRentedCars());
    }

    @PostMapping("/addNewCar")
    public ResponseEntity<?> addNewCar(@RequestBody Car car){
        return carService.saveCar(car);
    }
    @DeleteMapping("/deleteCar/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable Long carId){
        Optional<Car> car=carRepository.findById(carId);
        return carService.deleteCar(car.orElse(null));
    }
    @PutMapping("/updateCar")
    public ResponseEntity<?> updateCar(@RequestBody Car car){
        return carService.updateCar(car);
    }
    @PutMapping("/rentCar/{carId}")
    public ResponseEntity<?> rentCar(@PathVariable Long carId){
        Optional<Car> car=carRepository.findById(carId);
        return carService.rentCar(car.orElse(null));
    }
    @PutMapping("/returnCar/{carId}")
    public ResponseEntity<?> returnCar(@PathVariable Long carId){
        Optional<Car> car=carRepository.findById(carId);
        return carService.returnCar(car.orElse(null));
    }


}
