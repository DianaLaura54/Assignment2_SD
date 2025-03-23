package project.controllerImpl;

import project.constants.UserConstants;
import project.entity.Customer;
import project.controller.CustomerController;
import project.service.CustomerService;
import project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerControllerImplementation implements CustomerController {
    @Autowired
    CustomerService customerService;

    @Override
    public ResponseEntity<String> addNewCustomer(Map<String, String> requestMap) {
        try {
            return customerService.addNewCustomer(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Customer>> getAllCustomers(String filterValue) {
        try {
            return customerService.getAllCustomers(filterValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCustomer(Map<String, String> requestMap) {
        try {
            return customerService.updateCustomer(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCustomer(Integer id) {
        try {
            return customerService.deleteCustomer(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Customer> getById(Integer id) {
        try {
            return customerService.getById(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Customer(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
