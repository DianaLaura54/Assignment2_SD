package project.repository;

import project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> getAllCustomers();
    Customer getCustomerById(@Param("id") Integer id);
}
