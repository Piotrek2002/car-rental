package com.carrental.carrental.service;

import com.carrental.carrental.model.Car;
import com.carrental.carrental.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public ResponseEntity<?> saveCar(Car car) {
        car.setCarStatus(0);
        return ResponseEntity.ok(carRepository.save(car));
    }

    @Override
    public ResponseEntity<?> deleteCar(Car car) {
        List<Car> cars = (List<Car>) carRepository.findAll();
        if (cars.contains(car)) {
            carRepository.deleteById(car.getId());
            return ResponseEntity.ok("delete");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no record");
        }

    }

    @Override
    public ResponseEntity<?> updateCar(Car car) {
        List<Car> cars = (List<Car>) carRepository.findAll();
        if (checkCarExist(car, cars)) {
            carRepository.save(car);
            return ResponseEntity.ok(car);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no record");
        }
    }

    @Override
    public ResponseEntity<?> rentCar(Car car) {
        List<Car> cars = carRepository.findAllCarsToRent();
        if (car!=null && checkCarExist(car, cars)) {
            car.setCarStatus(1);
            carRepository.save(car);
            return ResponseEntity.ok(car);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no record");
        }
    }

    @Override
    public ResponseEntity<?> returnCar(Car car) {
        List<Car> cars = carRepository.findAllRentedCars();
        if (car!=null && checkCarExist(car, cars)) {
            car.setCarStatus(0);
            carRepository.save(car);
            return ResponseEntity.ok(car);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no record");
        }
    }

    private boolean checkCarExist(Car car, List<Car> cars) {

        return cars.stream().map(Car::getId).collect(Collectors.toList()).contains(car.getId());
    }
}
