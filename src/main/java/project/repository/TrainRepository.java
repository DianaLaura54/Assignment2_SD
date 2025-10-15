package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.entity.Train;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Integer> {

    // Returns all trains
    @Query("SELECT t FROM Train t JOIN FETCH t.customer")
    List<Train> getAllTrains();

    // Find by customer
    @Query("SELECT t FROM Train t WHERE t.customer.id = :customerId")
    List<Train> getTrainsByCustomerId(@Param("customerId") Integer customerId);

    // Find by ID
    @Query("SELECT t FROM Train t WHERE t.id = :id")
    Train getTrainById(@Param("id") Integer id);
}






