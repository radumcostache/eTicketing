package model;

import java.util.Vector;

public class Location {
    private int id;

    private String name;
    private String address;

    public Vector<Seat> getSeats() {
        return seats;
    }

    Vector <Seat> seats;

    public Location(int id, String name, String address) {
        this.id = id;
        this.name = name.trim();
        this.address = address.trim();
        this.seats = new Vector<>();
    }

    public void addSeat(Seat seat) {
        Seat newSeat = new Seat(seat);
        seats.add(newSeat);
    }

}
