package model;


import java.util.Vector;

public abstract class Client {
    public static int ID_GENERATOR = 0;

    public int getId() {
        return id;
    }

    private int id;

    private String name;
    private String phone;
    private String email;

    private Vector<Ticket> tickets = new Vector<>();

    protected Client(String name, String phone, String email) {
        this.id = ID_GENERATOR;
        ID_GENERATOR++;

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

}
