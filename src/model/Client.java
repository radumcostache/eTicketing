package model;


import java.util.Vector;

public abstract class Client {
    public int getId() {
        return id;
    }

    private int id;

    private String name;
    private String phone;
    private String email;

    protected Client(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    

    public String getInfo() {
        return "Name: " + name + "\nPhone: " + phone + "\nEmail: " + email;
    }

    public abstract SeatType getSeatType();
    public abstract double getTicketPrice(double basePrice);
    public abstract void buyTicket(double basePrice);
    public int getNrPoints() {
        return 0;
    }
}
