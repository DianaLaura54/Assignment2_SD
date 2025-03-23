package project.service;

import project.entity.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    ResponseEntity<String> addNewCustomer(Map<String, String> requestMap);
    ResponseEntity<List<Customer>> getAllCustomers(String filterValue);
    ResponseEntity<String> updateCustomer(Map<String, String> requestMap);
    ResponseEntity<Customer> getById(Integer id);
    ResponseEntity<String> deleteCustomer(Integer id);
}
