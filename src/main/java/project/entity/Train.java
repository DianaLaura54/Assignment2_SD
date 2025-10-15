package project.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "train", schema = "cfr")
public class Train implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_fk", nullable = false)
    private Customer customer;

    @Column(name = "departure_time")
    private Time departure_time;

    @Column(name = "arrival_time")
    private Time arrival_time;

    @Column(name = "price_ticket")
    private int price_ticket;

    @Column(name = "day")
    private int day;

    @Column(name = "month")
    private int month;

    @Column(name = "nr_seats")  // Available seats (decreases on booking)
    private int nr_seats;

    public Train() {
    }

    public Train(int id, String name, Time departure_time, Time arrival_time,
                 int price_ticket, int day, int month, int nr_seats, Customer customer) {
        this.id = id;
        this.name = name;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price_ticket = price_ticket;
        this.day = day;
        this.month = month;
        this.nr_seats = nr_seats;
        this.customer = customer;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Time getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Time departure_time) {
        this.departure_time = departure_time;
    }

    public Time getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Time arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getPrice_ticket() {
        return price_ticket;
    }

    public void setPrice_ticket(int price_ticket) {
        this.price_ticket = price_ticket;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getNr_seats() {
        return nr_seats;
    }

    public void setNr_seats(int nr_seats) {
        this.nr_seats = nr_seats;
    }

    // Helper method to book seats
    public boolean bookSeats(int seatsToBook) {
        if (nr_seats >= seatsToBook && seatsToBook > 0) {
            nr_seats -= seatsToBook;
            return true;
        }
        return false;
    }

    // Helper method to cancel booking and restore seats
    public void cancelSeats(int seatsToCancel) {
        nr_seats += seatsToCancel;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departure_time=" + departure_time +
                ", arrival_time=" + arrival_time +
                ", price_ticket=" + price_ticket +
                ", day=" + day +
                ", month=" + month +
                ", nr_seats=" + nr_seats +
                ", customer=" + (customer != null ? customer.getName() : null) +
                '}';
    }
}
