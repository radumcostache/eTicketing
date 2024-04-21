package model;

import java.util.Date;
import java.util.Stack;

public class Event {
    private int id;
    private String name;
    private String description;

    private Location location;
    private Organizer organizer;

    private double seatPrice;

    private Date startDate;
    private Date endDate;

    Stack <Seat> disabledSeats;
    Stack <Seat> exclusiveSeats;
    Stack <Seat> normalSeats;

    public Event(int id, Location location, String name, String description, Organizer organizer,
                 Date startDate, Date endDate, double seatPrice) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.description = description;

        this.startDate = startDate;
        this.endDate = endDate;

        this.disabledSeats = new Stack<Seat>();
        this.exclusiveSeats = new Stack<Seat>();
        this.normalSeats = new Stack<Seat>();

        this.seatPrice = seatPrice;
    }

    public boolean isAvailable (SeatType type) {
        switch (type) {
            case disabled -> {
                return !this.disabledSeats.isEmpty();
            }
            case exclusive -> {
                return (!this.exclusiveSeats.isEmpty()) || (!this.normalSeats.isEmpty());
            }
            case normal -> {
                return !this.normalSeats.isEmpty();
            }
            default -> {
                return false;
            }
        }
    }

    public Seat assignSeat(SeatType type) {
        switch (type) {
            case disabled -> {
                if (this.disabledSeats.isEmpty())
                    return null;
                else
                    return this.disabledSeats.pop();
            }
            case exclusive -> {
                if (this.exclusiveSeats.isEmpty() && this.normalSeats.isEmpty())
                    return null;
                else if (this.exclusiveSeats.isEmpty())
                    return this.normalSeats.pop();
                else
                    return this.exclusiveSeats.pop();
            }
            case normal -> {
                if (this.normalSeats.isEmpty())
                    return null;
                else
                    return this.normalSeats.pop();
            }
        }
        return null;
    }

    public void addSeat(Seat seat) {
        switch (seat.getSeatType()) {
            case disabled -> this.disabledSeats.push(seat);
            case exclusive -> this.exclusiveSeats.push(seat);
            case normal -> this.normalSeats.push(seat);
        }
    }
    public int getId() {
        return id;
    }

    public String getInfo() {
        return "***********************************\nEvent Id: " + this.id +"\nEvent Name: " + this.name + "\nDescription: " +
                this.description + "\nLocation: " + this.location +
                "\nStart Date: " + this.startDate + "\nEnd Date: " + this.endDate;
    }
    public double getSeatPrice() {
        return seatPrice;
    }

}
