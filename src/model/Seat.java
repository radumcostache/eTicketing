package model;

;
public class Seat {


    private int seatNumber;
    private SeatType seatType;

    public int getSeatNumber() {
        return seatNumber;
    }


    public Seat(int seatNumber, SeatType seatType) {
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
}
