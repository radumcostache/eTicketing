package repository;

import model.*;

import java.util.Vector;

public class TicketRepository
{
    private Vector<Ticket> tickets = new Vector<Ticket>();
    private Vector<HistoricalTicket> historicalTickets = new Vector<HistoricalTicket>();

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket lookupTicket(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == ticketId) {
                return ticket;
            }
        }

        return null;
    }

    public void printClientTickets(Client client) {
        for (Ticket ticket : tickets) {
            if (ticket.getClientId() == client.getId())
                System.out.println(ticket.getInfo());
        }
    }

    public void cancelTicket(int ticketId) {
        Ticket ticket = lookupTicket(ticketId);
        if (ticket != null) {
            HistoricalTicket historicalTicket = ticket.cancelTicket();
            historicalTickets.add(historicalTicket);

            tickets.remove(ticket);
        }
    }


}
