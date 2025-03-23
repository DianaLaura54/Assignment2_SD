package project.controllerImpl;

import project.constants.UserConstants;
import project.controller.TrainController;
import project.service.TrainService;
import project.utils.UserUtils;
import project.wrapper.TrainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class TrainControllerImplementation implements TrainController {
    @Autowired
    TrainService trainService;

    @Override
    public ResponseEntity<String> addNewTrain(Map<String, String> requestMap) {
        try {
            log.info("Inside rest impl train");
            return trainService.addNewTrain(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<TrainWrapper>> getAllTrains(String filterValue) {
        try {
            return trainService.getAllTrains(filterValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<TrainWrapper> getById(Integer id) {
        try {
            return trainService.getById(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new TrainWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<TrainWrapper>> getByCustomer(Integer id) {
        try {
            return trainService.getByCustomerId(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateTrain(Map<String, String> requestMap) {
        try {
            log.info("Inside update train");
            return trainService.updateTrain(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteTrain(Integer id) {
        try {
            return trainService.deleteTrain(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
