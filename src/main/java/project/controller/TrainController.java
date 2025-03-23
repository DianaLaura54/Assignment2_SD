package project.controller;

import project.wrapper.TrainWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/train")
public interface TrainController {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewTrain(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<TrainWrapper>> getAllTrains(@RequestParam(required = false) String filterValue);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<TrainWrapper> getById(@PathVariable Integer id);

    @GetMapping(path = "/getByCustomer/{id}")
    ResponseEntity<List<TrainWrapper>> getByCustomer(@PathVariable Integer id);

    @PutMapping(path = "/update")
    ResponseEntity<String> updateTrain(@RequestBody(required = true) Map<String, String> requestMap);

    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteTrain(@PathVariable Integer id);
}
