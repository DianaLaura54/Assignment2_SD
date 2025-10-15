package project.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "booking", schema = "cfr")
public class Booking implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Column(name = "seats_booked", nullable = false)
    private int seatsBooked;

    @Column(name = "booking_date", nullable = false, updatable = false)
    private Timestamp bookingDate;

    @Column(name = "status", nullable = false)
    private String status; // 'confirmed', 'cancelled'

    public Booking() {
        this.bookingDate = new Timestamp(System.currentTimeMillis());
        this.status = "confirmed";
    }

    public Booking(User user, Train train, int seatsBooked) {
        this.user = user;
        this.train = train;
        this.seatsBooked = seatsBooked;
        this.bookingDate = new Timestamp(System.currentTimeMillis());
        this.status = "confirmed";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        if (bookingDate == null) {
            bookingDate = new Timestamp(System.currentTimeMillis());
        }
        if (status == null) {
            status = "confirmed";
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", trainId=" + (train != null ? train.getId() : null) +
                ", seatsBooked=" + seatsBooked +
                ", bookingDate=" + bookingDate +
                ", status='" + status + '\'' +
                '}';
    }
}