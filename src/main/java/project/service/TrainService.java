package project.service;

import project.wrapper.TrainWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TrainService {
    ResponseEntity<String> addNewTrain(Map<String, String> requestMap);
    ResponseEntity<List<TrainWrapper>> getAllTrains(String filterValue);
    ResponseEntity<String> updateTrain(Map<String, String> requestMap);
    ResponseEntity<String> deleteTrain(Integer id);
    ResponseEntity<TrainWrapper> getById(Integer id);
    ResponseEntity<List<TrainWrapper>> getByCustomerId(Integer id);
}
