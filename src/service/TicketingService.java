package service;

import com.google.zxing.WriterException;
import model.*;
import repository.ClientRepository;
import repository.EventRepository;
import repository.TicketRepository;

import java.io.IOException;

public class TicketingService {
    private ClientRepository clientRepository;
    private EventRepository eventRepository;
    private TicketRepository ticketRepository;
    private QrGenerator qr = new QrGenerator("/qrcodes");

    public TicketingService() {
        this.clientRepository = new ClientRepository();
        this.eventRepository = new EventRepository();
        this.ticketRepository = new TicketRepository();
    }

    public void addClient(Client client) {
        clientRepository.addClient(client);
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
