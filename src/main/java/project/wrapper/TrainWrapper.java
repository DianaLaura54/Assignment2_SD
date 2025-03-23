package project.wrapper;

import project.entity.Train;

import java.sql.Time;

public class TrainWrapper {
    private int id;
    private String name;
    private Time departure_time;
    private Time arrival_time;
    private int price_ticket;
    private int day;
    private int month;
    private int nr_seats;
    private int customerId;
    private String customerName;

    public TrainWrapper()
    {

    }
    public TrainWrapper(int id, String name,Time departure_time, Time arrival_time, int price_ticket, int day, int month, int nr_seats,
                        int customerId, String customerName) {
        this.id = id;
        this.name=name;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price_ticket = price_ticket;
        this.day = day;
        this.month = month;
        this.nr_seats = nr_seats;
        this.customerId = customerId;
        this.customerName = customerName;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


}
