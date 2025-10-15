package project.service;

import org.springframework.http.ResponseEntity;
import project.entity.Booking;

import java.util.List;
import java.util.Map;

public interface BookingService {


    ResponseEntity<String> createBooking(Map<String, String> requestMap);


    ResponseEntity<List<Booking>> getMyBookings();


    ResponseEntity<List<Booking>> getAllBookings();


    ResponseEntity<Booking> getBookingById(Integer id);


    ResponseEntity<String> cancelBooking(Integer id);


    ResponseEntity<List<Booking>> getBookingsByTrainId(Integer trainId);
}