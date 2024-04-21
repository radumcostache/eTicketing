package service;

import com.google.zxing.WriterException;
import model.*;
import repository.ClientRepository;
import repository.EventRepository;
import repository.TicketRepository;

import java.io.IOException;
import java.sql.Connection;

public class TicketingService {
    private final ClientRepository clientRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    Connection db;
    private final QrGenerator qr = new QrGenerator();

    public TicketingService(Connection db) {
        this.db = db;
        this.clientRepository = new ClientRepository(db);
        this.eventRepository = new EventRepository(db);
        this.ticketRepository = new TicketRepository(db);
    }

    public int addClient(String name, String email, String phone, String userType) {
        return clientRepository.addClient(name, email, phone, userType);
    }

    public Client lookupClient(int clientId) {
        return clientRepository.lookupClient(clientId);
    }
    public void addEvent(Event event) {
        eventRepository.add(event);
    }

    public void generateQrCode(Ticket ticket) {
        try{
            System.out.println("Qr code file generated at: " + qr.generateQr(ticket));
        }
        catch (IOException | WriterException e) {
            System.out.println("Eroare la generarea codului qr");
        }

    }

    public Ticket generateTicket(Event event, Client client) {
        Seat assignedSeat = event.assignSeat(client.getSeatType());

        if (assignedSeat == null) {
            System.out.println("No more seats available.");
            return null;
        }

        Ticket generatedTicket = new Ticket(event, client, assignedSeat);
        ticketRepository.addTicket(generatedTicket);

        client.buyTicket(event.getSeatPrice());
        generateQrCode(generatedTicket);

        return generatedTicket;
    }
    public Ticket lookupTicket(int ticketId) {
        return ticketRepository.lookupTicket(ticketId);
    }
    public void printTicketInfo(Ticket ticket) {
        System.out.println(ticket.getInfo());
    }
    public void printClientTickets(Client client) {
        ticketRepository.printClientTickets(client);
    }

    public void cancelTicket(int ticketId) {
        ticketRepository.cancelTicket(ticketId);
    }

    public void printEvents() {
        eventRepository.printEvents();
    }

    public Event lookupEvent(int eventId) {
        return eventRepository.lookupEvent(eventId);
    }

}
