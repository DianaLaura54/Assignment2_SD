package project.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;


/*@NamedQuery(name = "Train.getAllTrains", query = "select new project.wrapper.TrainWrapper(t.id, t.name,t.departure_time, t.arrival_time, t.price_ticket, t.day, t.month,t.nr_seats," +
        " t.customer.id, t.customer.name,t.customer.student,t.customer.id_seat) from Train t")

@NamedQuery(name = "Train.getTrainById", query = "select new project.wrapper.TrainWrapper(t.id, t.name,t.departure_time, t.arrival_time, t.price_ticket, t.day, t.month,t.nr_seats," +
        " t.customer.id, t.customer.name,t.customer.student,t.customer.id_seat) from Train t where t.id=:id")

@NamedQuery(name = "Train.getTrainsByCustomerId", query = "select new project.wrapper.TrainWrapper(t.id,t.name, t.departure_time, t.arrival_time, t.price_ticket, t.day, t.month,t.nr_seats," +
        " t.customer.id, t.customer.name,t.customer.student,t.customer.id_seat) from Train t where t.customer.id=:id")
*/
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "train")
public class Train implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
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
    @Column(name = "nr_seats")
    private int nr_seats;

    public Train() {
    }

    public Train(int id, String name,Time departure_time, Time arrival_time, int price_ticket, int day, int month, int nr_seats, Customer customer) {
        this.id = id;
        this.name=name;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price_ticket = price_ticket;
        this.day = day;
        this.month = month;
        this.nr_seats = nr_seats;
        this.customer = customer;
    }

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
}