package project.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.JWT.JWTFilter;
import project.constants.UserConstants;
import project.entity.Booking;
import project.entity.Train;
import project.entity.User;
import project.repository.BookingRepository;
import project.repository.TrainRepository;
import project.repository.UserRepository;
import project.service.BookingService;
import project.utils.UserUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class BookingServiceImplementation implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTFilter jwtFilter;

    @Override
    @Transactional
    public ResponseEntity<String> createBooking(Map<String, String> requestMap) {
        try {
            // Get current user email from JWT
            String userEmail = jwtFilter.getCurrentUser();
            if (userEmail == null) {
                return UserUtils.getResponseEntity("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            // Validate request
            if (!validateBookingRequest(requestMap)) {
                return UserUtils.getResponseEntity(UserConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

            // Get user
            User user = userRepository.findUserByEmail(userEmail);
            if (user == null) {
                return UserUtils.getResponseEntity("User not found", HttpStatus.NOT_FOUND);
            }

            // Get train
            Integer trainId = Integer.parseInt(requestMap.get("trainId"));
            Optional<Train> trainOptional = trainRepository.findById(trainId);
            if (trainOptional.isEmpty()) {
                return UserUtils.getResponseEntity("Train not found", HttpStatus.NOT_FOUND);
            }

            Train train = trainOptional.get();
            int seatsToBook = Integer.parseInt(requestMap.get("seatsToBook"));

            // Check if user already has a booking for this train
            Optional<Booking> existingBooking = bookingRepository.findByUserIdAndTrainId(user.getId(), trainId);
            if (existingBooking.isPresent()) {
                return UserUtils.getResponseEntity("You already have a booking for this train", HttpStatus.BAD_REQUEST);
            }

            // Check if enough seats available (using nr_seats)
            if (train.getNr_seats() < seatsToBook) {
                return UserUtils.getResponseEntity(
                        "Not enough seats available. Only " + train.getNr_seats() + " seats left.",
                        HttpStatus.BAD_REQUEST
                );
            }

            // Create booking
            Booking booking = new Booking(user, train, seatsToBook);
            bookingRepository.save(booking);

            // Update available seats (decrease nr_seats)
            train.setNr_seats(train.getNr_seats() - seatsToBook);
            trainRepository.save(train);

            log.info("Booking created successfully for user: {} on train: {}", userEmail, train.getName());
            return UserUtils.getResponseEntity(
                    "Booking confirmed! You have booked " + seatsToBook + " seat(s) on " + train.getName(),
                    HttpStatus.OK
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error creating booking: ", ex);
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Booking>> getMyBookings() {
        try {
            String userEmail = jwtFilter.getCurrentUser();
            if (userEmail == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            User user = userRepository.findUserByEmail(userEmail);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Booking> bookings = bookingRepository.findByUserId(user.getId());
            return new ResponseEntity<>(bookings, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error fetching user bookings: ", ex);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Booking>> getAllBookings() {
        try {
            if (!jwtFilter.isAdmin()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Booking> bookings = bookingRepository.getAllBookings();
            return new ResponseEntity<>(bookings, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error fetching all bookings: ", ex);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Booking> getBookingById(Integer id) {
        try {
            Optional<Booking> booking = bookingRepository.findById(id);
            if (booking.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Check if user owns this booking or is admin
            String userEmail = jwtFilter.getCurrentUser();
            User user = userRepository.findUserByEmail(userEmail);

            if (!jwtFilter.isAdmin() && booking.get().getUser().getId() != user.getId()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(booking.get(), HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error fetching booking: ", ex);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @Transactional
    public ResponseEntity<String> cancelBooking(Integer id) {
        try {
            Optional<Booking> bookingOptional = bookingRepository.findById(id);
            if (bookingOptional.isEmpty()) {
                return UserUtils.getResponseEntity("Booking not found", HttpStatus.NOT_FOUND);
            }

            Booking booking = bookingOptional.get();

            // Check if user owns this booking or is admin
            String userEmail = jwtFilter.getCurrentUser();
            User user = userRepository.findUserByEmail(userEmail);

            if (!jwtFilter.isAdmin() && booking.getUser().getId() != user.getId()) {
                return UserUtils.getResponseEntity(UserConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

            // Check if already cancelled
            if ("cancelled".equals(booking.getStatus())) {
                return UserUtils.getResponseEntity("Booking is already cancelled", HttpStatus.BAD_REQUEST);
            }

            // Cancel booking
            booking.setStatus("cancelled");
            bookingRepository.save(booking);

            // Restore available seats (increase nr_seats)
            Train train = booking.getTrain();
            train.setNr_seats(train.getNr_seats() + booking.getSeatsBooked());
            trainRepository.save(train);

            log.info("Booking {} cancelled successfully", id);
            return UserUtils.getResponseEntity("Booking cancelled successfully", HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error cancelling booking: ", ex);
        }
        return UserUtils.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Booking>> getBookingsByTrainId(Integer trainId) {
        try {
            if (!jwtFilter.isAdmin()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Booking> bookings = bookingRepository.findByTrainId(trainId);
            return new ResponseEntity<>(bookings, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error fetching bookings for train: ", ex);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateBookingRequest(Map<String, String> requestMap) {
        return requestMap.containsKey("trainId") &&
                requestMap.containsKey("seatsToBook") &&
                Integer.parseInt(requestMap.get("seatsToBook")) > 0;
    }
}
