package model;

;
public class Seat {

    private int id;
    private int seatNumber;
    private SeatType seatType;

    public int getSeatNumber() {
        return seatNumber;
    }


    public Seat(int id, int seatNumber, SeatType seatType) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public Seat(Seat other) {
        this.seatNumber = other.getSeatNumber();
        this.seatType = other.getSeatType();
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public int getId() {
        return id;
    }
}
