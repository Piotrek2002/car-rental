package com.carrental.carrental.controller;

import com.carrental.carrental.model.Car;
import com.carrental.carrental.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean_database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CarControllerTest {

    @LocalServerPort
    private int serverPort;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CarRepository carRepository;



    @Test
    void findAll() throws URISyntaxException {

        RequestEntity<Void> request = RequestEntity
                .get(createServerAddress("/findAll"))
                .build();
        ResponseEntity<List<Car>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
        }

    @Test
    void findAllCarsToRent() throws URISyntaxException {
        RequestEntity<Void> request = RequestEntity
                .get(createServerAddress("/findAllCarsToRent"))
                .build();
        ResponseEntity<List<Car>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, response.getBody().size());
    }


    @Test
    void findAllRentedCars() throws URISyntaxException {
        RequestEntity<Void> request = RequestEntity
                .get(createServerAddress("/findAllRentedCars"))
                .build();
        ResponseEntity<List<Car>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void addNewCar() throws URISyntaxException {
        Car car = new Car("d","d",9,9);
        RequestEntity<Car> request = RequestEntity
                .post(createServerAddress("/addNewCar"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(car);
        ResponseEntity<Car> response = restTemplate.exchange(request, Car.class);
        Car body = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("d", body.getBrand());
        assertEquals("d", body.getModel());
        assertEquals(9, body.getPower());
        assertEquals(9, body.getPrice());


    }

    @Test
    void deleteCar() throws URISyntaxException {
        RequestEntity<Void> request = RequestEntity
                .get(createServerAddress("/findAll"))
                .build();
        carRepository.deleteById(1L);
        ResponseEntity<List<Car>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void updateCar() throws URISyntaxException {
        Car car = new Car(1L,0,"a","d",9,9);
        RequestEntity<Car> request = RequestEntity
                .put(createServerAddress("/updateCar"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(car);
        ResponseEntity<Car> response = restTemplate.exchange(request, Car.class);
        Car body = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, body.getId());
        assertEquals(0, body.getCarStatus());
        assertEquals("a", body.getBrand());
        assertEquals("d", body.getModel());
        assertEquals(9, body.getPower());
        assertEquals(9, body.getPrice());

    }

    @Test
    void rentCar() throws URISyntaxException {
        RequestEntity<Void> request = RequestEntity
                .put(createServerAddress("/rentCar/1"))
                .contentType(MediaType.APPLICATION_JSON).build();
        ResponseEntity<Car> response = restTemplate.exchange(request, Car.class);
        Car body = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, body.getId());
        assertEquals(1, body.getCarStatus());

    }

    @Test
    void returnCar() throws URISyntaxException {
        RequestEntity<Void> request = RequestEntity
                .put(createServerAddress("/returnCar/2"))
                .contentType(MediaType.APPLICATION_JSON).build();
        ResponseEntity<Car> response = restTemplate.exchange(request, Car.class);
        Car body = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, body.getId());
        assertEquals(0, body.getCarStatus());

    }

    private URI createServerAddress(String address) throws URISyntaxException {
        return new URI("http://localhost:" + serverPort + address);
    }


}