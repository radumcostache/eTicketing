package model;

public class Ticket {
    private static int ID_GENERATOR = 0;
    private int id;

    private Event event;
    private Client client;
    private Seat seat;


    public Ticket(Event event, Client client, Seat seat) {
        this.event = event;
        this.client = client;
        this.seat = seat;

        id = ID_GENERATOR++;
    }

    public HistoricalTicket cancelTicket() {
        this.event.addSeat(this.seat);
        return new HistoricalTicket(this.event, this.client, this.seat);
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return client.getId();
    }

    public String getInfo() {
        return "***********************TICKET INFORMATION***************************" +
                "Ticket ID: " + this.id + "\n" +
                "***Event Info:***\n" + event.getInfo() +
                "Seat: " + seat.getSeatNumber() +"\n---------------------------\n" +
                "***Client Info***\n" + client.getInfo() + "\n-------------------------\n";
    }
}
