package com.carrental.carrental.repository;

import com.carrental.carrental.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car,Long> {

    @Query("select c from Car c where c.carStatus=1")
    List<Car> findAllRentedCars();

    @Query("select c from Car c where c.carStatus=0")
    List<Car> findAllCarsToRent();
}
