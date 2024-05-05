package repository;

import model.*;

import java.sql.*;
import java.util.Vector;

public class TicketRepository
{
    private ClientRepository clientRepository;
    private  EventRepository eventRepository;
    Connection db;

    public TicketRepository(Connection db, ClientRepository clientRepository, EventRepository eventRepository) {
        this.db = db;
        this.eventRepository = eventRepository;
        this.clientRepository = clientRepository;
    }

    public Ticket addTicket(Event event, Client client, Seat seat) {
        String sql = String.format("INSERT INTO TICKETS (seatId, clientId, eventId) VALUES (%d, %d, %d)",
                                    seat.getId(), client.getId(), event.getId());

        try {
            PreparedStatement stmt = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                db.commit();
                return new Ticket(id, event, client, seat);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Ticket getTicket(ResultSet rs) throws SQLException {
        int ticketId = rs.getInt("ID");
        int seatId = rs.getInt("SEATID");
        int clientId = rs.getInt("CLIENTID");

        Client client = clientRepository.lookupClient(clientId);
        PreparedStatement seatStmt = db.prepareStatement("SELECT SEATNUMBER, SEATTYPE, EVENTID " +
                "FROM SEATS WHERE id = " + seatId);

        ResultSet rseats = seatStmt.executeQuery();
        if (rseats.next()) {
            int seatNumber = rseats.getInt("SEATNUMBER");
            String seatTypeString = rseats.getString("SEATTYPE");
            int eventId = rseats.getInt("EVENTID");
            Event event = eventRepository.lookupEvent(eventId);

            SeatType seatType = switch (seatTypeString) {
                case "exclusive" -> SeatType.exclusive;
                case "normal" -> SeatType.normal;
                default -> SeatType.disabled;
            };

            Seat seat = new Seat(seatId, seatNumber, seatType);
            return new Ticket(ticketId, event, client, seat);

        }
        return null;
    }

    public Ticket lookupTicket(int ticketId) {
        Ticket ticket = null;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT id, SEATID, CLIENTID" +
                    "FROM TICKETS WHERE id = " + ticketId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ticket = getTicket(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticket;
    }

    public void printClientTickets(Client client) {
        Vector <Ticket> tickets = new Vector<>();
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT id, seatid, clientid FROM tickets" +
                    " WHERE CLIENTID = " + client.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tickets.add(getTicket(rs));
            }

            for (Ticket ticket: tickets) {
                System.out.println(ticket.getInfo());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void cancelTicket(int ticketId) {
        Ticket ticket = lookupTicket(ticketId);
        if (ticket != null) {
            HistoricalTicket historicalTicket = ticket.cancelTicket();

            try {
                PreparedStatement historicalStmt = db.prepareStatement("INSERT INTO HISTORICAL_TICKETS " +
                        "SELECT t.id, t.seatid, t.clientid, t.eventid FROM tickets t WHERE t.id = " + ticket.getId());
                PreparedStatement deleteStmt = db.prepareStatement("DELETE FROM TICKETS WHERE id = " + ticketId);

                historicalStmt.executeUpdate();
                deleteStmt.executeUpdate();
                db.commit();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            System.out.println("Ticket not found");
        }
    }


}
