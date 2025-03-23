package project.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.entity.Customer;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/customer")
public interface CustomerController {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCustomer(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String filterValue);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<Customer> getById(@PathVariable Integer id);

    @PutMapping(path = "/update")
    ResponseEntity<String> updateCustomer(@RequestBody(required = true) Map<String, String> requestMap);

    @DeleteMapping(path="/delete/{id}")
    ResponseEntity<String> deleteCustomer(@PathVariable Integer id);
}
