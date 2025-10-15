package project.serviceImpl;

import org.springframework.transaction.annotation.Transactional;
import project.JWT.JWTFilter;
import project.constants.UserConstants;
import project.entity.Customer;
import project.entity.Train;
import project.repository.CustomerRepository;
import project.repository.TrainRepository;
import project.service.TrainService;
import project.utils.UserUtils;
import project.wrapper.TrainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TrainServiceImplementation implements TrainService {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JWTFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewTrain(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isUser()) {
                return UserUtils.getResponseEntity(UserConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

            if (validateTrainMap(requestMap, false)) {
                trainRepository.save(getTrainFromMap(requestMap, false));
                return UserUtils.getResponseEntity("Train added successfully", HttpStatus.OK);
            }
            return UserUtils.getResponseEntity(UserConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @Transactional  // Add this annotation
    public ResponseEntity<List<TrainWrapper>> getAllTrains(String filterValue) {
        try {
            List<Train> trains = trainRepository.getAllTrains();
            List<TrainWrapper> wrappers = new ArrayList<>();

            for (Train train : trains) {
                wrappers.add(convertToWrapper(train));
            }

            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateTrain(Map<String, String> requestMap) {
        try {
            log.info("=== UPDATE TRAIN DEBUG ===");
            log.info("isUser check: " + jwtFilter.isUser());
            log.info("isAdmin check: " + jwtFilter.isAdmin());
            log.info("Current user: " + jwtFilter.getCurrentUser());

            if (jwtFilter.isUser()) {
                log.error("BLOCKED: User role detected");
                return UserUtils.getResponseEntity(UserConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

            log.info("ALLOWED: Proceeding with update");

            if (validateTrainMap(requestMap, true)) {
                Optional<Train> optional = trainRepository.findById(Integer.parseInt(requestMap.get("id")));
                if (optional.isEmpty()) {
                    return UserUtils.getResponseEntity("Train id does not exist", HttpStatus.OK);
                }
                trainRepository.save(getTrainFromMap(requestMap, true));
                return UserUtils.getResponseEntity("Train updated successfully!", HttpStatus.OK);
            }
            return UserUtils.getResponseEntity(UserConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteTrain(Integer id) {
        try {
            log.info("=== UPDATE TRAIN DEBUG ===");
            log.info("isUser check: " + jwtFilter.isUser());
            log.info("isAdmin check: " + jwtFilter.isAdmin());
            log.info("Current user: " + jwtFilter.getCurrentUser());

            if (jwtFilter.isUser()) {
                log.error("BLOCKED: User role detected");
                return UserUtils.getResponseEntity(UserConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

            log.info("ALLOWED: Proceeding with update");
            Optional<Train> optional = trainRepository.findById(id);
            if (optional.isPresent()) {
                trainRepository.deleteById(id);
                return UserUtils.getResponseEntity("Train deleted successfully!", HttpStatus.OK);
            } else {
                return UserUtils.getResponseEntity("Train id doesn't exist.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @Transactional
    public ResponseEntity<TrainWrapper> getById(Integer id) {
        try {
            Train train = trainRepository.getTrainById(id);
            if (train != null) {
                return new ResponseEntity<>(convertToWrapper(train), HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new TrainWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @Transactional

    public ResponseEntity<List<TrainWrapper>> getByCustomerId(Integer id) {
        try {
            List<Train> trains = trainRepository.getTrainsByCustomerId(id);
            List<TrainWrapper> wrappers = new ArrayList<>();

            for (Train train : trains) {
                wrappers.add(convertToWrapper(train));
            }

            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to validate train data
    private boolean validateTrainMap(Map<String, String> requestMap, boolean validateID) {
        if (requestMap.containsKey("name") &&
                requestMap.containsKey("departure_time") &&
                requestMap.containsKey("arrival_time") &&
                requestMap.containsKey("price_ticket") &&
                requestMap.containsKey("day") &&
                requestMap.containsKey("month") &&
                requestMap.containsKey("nr_seats") &&
                requestMap.containsKey("customer_fk")) {

            if (requestMap.containsKey("id") && validateID) {
                return true;
            } else {
                return !validateID;
            }
        }
        return false;
    }

    // Helper method to convert Map to Train entity
    private Train getTrainFromMap(Map<String, String> requestMap, boolean isUsedForUpdate) {
        Train train = new Train();

        if (isUsedForUpdate) {
            train.setId(Integer.parseInt(requestMap.get("id")));
        }

        train.setName(requestMap.get("name"));
        train.setDeparture_time(Time.valueOf(requestMap.get("departure_time")));
        train.setArrival_time(Time.valueOf(requestMap.get("arrival_time")));
        train.setPrice_ticket(Integer.parseInt(requestMap.get("price_ticket")));
        train.setDay(Integer.parseInt(requestMap.get("day")));
        train.setMonth(Integer.parseInt(requestMap.get("month")));
        train.setNr_seats(Integer.parseInt(requestMap.get("nr_seats")));

        // Set the customer relationship
        Integer customerId = Integer.parseInt(requestMap.get("customer_fk"));
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            train.setCustomer(customer.get());
        }

        return train;
    }

    // Helper method to convert Train entity to TrainWrapper
    private TrainWrapper convertToWrapper(Train train) {
        return new TrainWrapper(
                train.getId(),
                train.getName(),
                train.getDeparture_time(),
                train.getArrival_time(),
                train.getPrice_ticket(),
                train.getDay(),
                train.getMonth(),
                train.getNr_seats(),
                train.getCustomer().getId(),
                train.getCustomer().getName()
        );
    }
}