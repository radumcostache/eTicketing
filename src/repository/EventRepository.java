package repository;

import model.*;

import java.sql.*;
import java.util.Vector;

public class EventRepository {

//    Vector <Event> events = new Vector <Event> ();
    Connection db;
    public EventRepository(Connection db) {
        this.db = db;
    }

    private Event reconstructEvent(ResultSet rs) {
        Location location = null;
        Organizer organizer = null;
        Event event = null;

        try {
            int eventId = rs.getInt("eventId");
            String eventName = rs.getString("eventName");
            String description = rs.getString("eventDescription");
            int locationId = rs.getInt("locationId");
            String locationName = rs.getString("locationName");
            String locationAddress = rs.getString("locationAddress");
            int organizerId = rs.getInt("organizerId");
            String organizerName = rs.getString("organizerName");
            String organizerAddress = rs.getString("organizerAddress");
            Date startDate = rs.getDate("startDate");
            Date endtDate = rs.getDate("endDate");
            double seatPrice = rs.getDouble("seatPrice");

            location = new Location(locationId, locationName, locationAddress);
            organizer = new Organizer(organizerId, organizerName, organizerAddress);

            event = new Event(eventId, location, eventName, description, organizer, startDate, endtDate, seatPrice);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

    public void printEvents() {
        System.out.println("******************************LIST OF EVENTS******************************");
         // get all events from the database into a Vector of events
        Vector<Event> events = new Vector<>();
        try {
            PreparedStatement stmt = db.prepareStatement("""
                    SELECT e.id AS eventId,\
                           e.name AS eventName,\
                           description AS eventDescription,\
                           locationId,\
                           l.name AS locationName,\
                           l.address AS locationAddress,\
                           organizerId,\
                           o.name AS organizerName,\
                           o.address AS organizerAddress,\
                           startDate,\
                           endDate,\
                           seatPrice
                    FROM events e JOIN locations l ON (e.locationId = l.id)
                                  JOIN organizers o ON (e.organizerId = o.Id)""");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = reconstructEvent(rs);
                events.add(event);
            }
            for (Event event : events) {
                System.out.println(event.getInfo());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addFreeSeats(Event event) {
        int eventId = event.getId();
        try {
            String sql = String.format("SELECT id, seatNumber, seatType\n" +
                    "FROM seats\n" +
                    "WHERE \n" +
                    "    eventId IS NOT NULL AND eventId = %s AND " +
                    "    id NOT IN (SELECT seatId FROM tickets)", eventId);
            PreparedStatement stmt = db.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int seatId = rs.getInt("id");
                int seatNumber = rs.getInt("seatNumber");
                String seatTypeString = rs.getString("seatType");
                SeatType seatType = null;
                seatType = switch (seatTypeString) {
                    case "exclusive" -> SeatType.exclusive;
                    case "normal" -> SeatType.normal;
                    default -> SeatType.disabled;
                };

                Seat seat = new Seat(seatId, seatNumber, seatType);
                event.addSeat(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Event lookupEvent(int eventId) {
        Event event = null;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT e.id AS eventId," +
                    "       e.name AS eventName," +
                    "       description AS eventDescription," +
                    "       locationId," +
                    "       l.name AS locationName," +
                    "       l.address AS locationAddress," +
                    "       organizerId," +
                    "       o.name AS organizerName," +
                    "       o.address AS organizerAddress," +
                    "       startDate," +
                    "       endDate," +
                    "       seatPrice " +
                    "FROM events e JOIN locations l ON (e.locationId = l.id)" +
                    "              JOIN organizers o ON (e.organizerId = o.Id)" +
                    "WHERE e.Id = " + eventId );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                event = reconstructEvent(rs);
                addFreeSeats(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }
}
