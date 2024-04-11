package model;

import java.util.Vector;

public class Location {

    private String name;
    private String address;

    public Vector<Seat> getSeats() {
        return seats;
    }

    Vector <Seat> seats;

    public Location(String name, String address) {

        this.name = name.trim();
        this.address = address.trim();
        this.seats = new Vector<>();
    }

    public void addSeat(Seat seat) {
        Seat newSeat = new Seat(seat);
        seats.add(newSeat);
    }

}
