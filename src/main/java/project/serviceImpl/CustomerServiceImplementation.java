package project.serviceImpl;

import project.JWT.JWTFilter;
import project.constants.UserConstants;
import project.repository.CustomerRepository;
import project.entity.Customer;
import project.service.CustomerService;
import project.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JWTFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCustomer(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isUser()) {
                return UserUtils.getResponseEntity(UserConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
            if (validateCustomerMap(requestMap, false)) {
                customerRepository.save(getCustomerFromMap(requestMap, false));
                return UserUtils.getResponseEntity("Customer added successfully", HttpStatus.OK);
            }
            return UserUtils.getResponseEntity(UserConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Customer>> getAllCustomers(String filterValue) {
        try {
            if (filterValue != null && !filterValue.isEmpty() && filterValue.equalsIgnoreCase("true")) {
                return new ResponseEntity<List<Customer>>(customerRepository.getAllCustomers(), HttpStatus.OK);
            }
            return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Customer>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCustomer(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isUser()) {
                return UserUtils.getResponseEntity(UserConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

            if (validateCustomerMap(requestMap, true)) {
                Optional optional = customerRepository.findById(Integer.parseInt(requestMap.get("id")));
                if (optional.isEmpty()) {
                    return UserUtils.getResponseEntity("Customer id does not exist", HttpStatus.OK);
                }
                customerRepository.save(getCustomerFromMap(requestMap, true));
                return UserUtils.getResponseEntity("Customer updated successfully!", HttpStatus.OK);
            }

            return UserUtils.getResponseEntity(UserConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Customer> getById(Integer id) {
        try {
            return new ResponseEntity<>(customerRepository.getCustomerById(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Customer(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCustomer(Integer id) {
        try {
            if (jwtFilter.isUser()) {
                return UserUtils.getResponseEntity(UserConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
            Optional optional = customerRepository.findById(id);
            if (!optional.isEmpty()) {
                customerRepository.deleteById(id);
                return UserUtils.getResponseEntity("Customer deleted successfully!", HttpStatus.OK);
            } else {
                return UserUtils.getResponseEntity("Customer id doesn't exist.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCustomerMap(Map<String, String> requestMap, boolean validateID) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateID) {
                return true;
            } else {
                return !validateID;
            }
        }
        return false;
    }

    private Customer getCustomerFromMap(Map<String, String> requestMap, boolean isUsedForUpdate) {
        Customer customer = new Customer();
        if (isUsedForUpdate) {
            customer.setId(Integer.parseInt(requestMap.get("id")));
        }
        customer.setName(requestMap.get("name"));


        return customer;
    }
}
