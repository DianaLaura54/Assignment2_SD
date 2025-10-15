package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.entity.Booking;
import project.service.BookingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a new booking
    @PostMapping("/book")
    public ResponseEntity<String> bookTrain(@RequestBody Map<String, String> requestMap) {
        try {
            return bookingService.createBooking(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Get current user's bookings
    @GetMapping("/my-bookings")
    public ResponseEntity<List<Booking>> getMyBookings() {
        try {
            return bookingService.getMyBookings();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Get all bookings (admin only)
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        try {
            return bookingService.getAllBookings();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        try {
            return bookingService.getBookingById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Cancel booking
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer id) {
        try {
            return bookingService.cancelBooking(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Get bookings for a specific train (admin only)
    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<Booking>> getBookingsByTrainId(@PathVariable Integer trainId) {
        try {
            return bookingService.getBookingsByTrainId(trainId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
