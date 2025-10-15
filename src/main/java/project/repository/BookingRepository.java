package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Find all bookings for a specific user
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status = 'confirmed' ORDER BY b.bookingDate DESC")
    List<Booking> findByUserId(@Param("userId") Integer userId);

    // Find all bookings for a specific train
    @Query("SELECT b FROM Booking b WHERE b.train.id = :trainId AND b.status = 'confirmed'")
    List<Booking> findByTrainId(@Param("trainId") Integer trainId);

    // Check if user already has a booking for this train
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.train.id = :trainId AND b.status = 'confirmed'")
    Optional<Booking> findByUserIdAndTrainId(@Param("userId") Integer userId, @Param("trainId") Integer trainId);

    // Get all bookings (for admin)
    @Query("SELECT b FROM Booking b ORDER BY b.bookingDate DESC")
    List<Booking> getAllBookings();

    // Count total booked seats for a train
    @Query("SELECT COALESCE(SUM(b.seatsBooked), 0) FROM Booking b WHERE b.train.id = :trainId AND b.status = 'confirmed'")
    Integer countBookedSeatsByTrainId(@Param("trainId") Integer trainId);
}