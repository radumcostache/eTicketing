import model.*;
import service.TicketingService;

import java.io.File;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static String dbPath = System.getProperty("user.dir") + File.separator + "TicketingDB";
    private static String connectionString = "jdbc:derby:" + dbPath + ";create=true";

    private static Connection connection;
    static private TicketingService ticketingService;
    static private Scanner scanner = new Scanner(System.in);

    private static void init() {
        try {
            connection = DriverManager.getConnection(connectionString);
            connection.setAutoCommit(false);
            connection.setSchema("AGENT");

            // DEBUG statement
            //print content of organizers
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORGANIZERS");
//            ResultSet resultSet = statement.executeQuery();

//            ResultSetMetaData rsmd = resultSet.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
//            while (resultSet.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = resultSet.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//                System.out.println("");
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ticketingService = new TicketingService(connection);

        Organizer organizer = new Organizer("BestEvents", "Calea Victoriei");
        Location salaPalatului = new Location("Sala Palatului", "Calea Victoriei");
        Location areneleRomane = new Location("Arenele Romane", "Parcul Carol I");

        int i;
        for (i = 1; i <= 100; ++i) {
            Seat seat = new Seat(i, SeatType.normal);
            salaPalatului.addSeat(seat);
        }
        for (i = 101; i <= 120; ++i) {
            Seat seat = new Seat(i, SeatType.disabled);
            salaPalatului.addSeat(seat);
        }
        for (i = 121; i <= 130; ++i) {
            Seat seat = new Seat(i, SeatType.exclusive);
            salaPalatului.addSeat(seat);
        }

        for (i = 1; i <= 100; ++i) {
            Seat seat = new Seat(i, SeatType.normal);
            areneleRomane.addSeat(seat);
        }
        for (i = 101; i <= 150; ++i) {
            Seat seat = new Seat(i, SeatType.exclusive);
            areneleRomane.addSeat(seat);
        }

        Event concert = new Event(salaPalatului,
                "Concert Metalica", "Concert de Metal organizat in Bucuresti" ,
                organizer,
                new Date(2024, Calendar.APRIL, 25, 20, 0),
                new Date(2024, Calendar.APRIL, 25, 22, 0), 100);
        Event conferintaJava = new Event(areneleRomane,
                "Conferinta JAVA", "Conferinta academica dedicata limbajului Java",
                organizer,
                new Date(2024, Calendar.DECEMBER, 4, 12, 0),
                new Date(2024, Calendar.DECEMBER, 6, 12, 0), 50);
        ticketingService.addEvent(concert);
        ticketingService.addEvent(conferintaJava);
    }
    public static void buyTicket(Client user, Event event) {
        double price = user.getTicketPrice(event.getSeatPrice());

        System.out.println("Ticket Price: " + price);
        System.out.println("Proceed? y/[n]");

        String option = scanner.nextLine().trim();
        if (option.equals("y")) {
            Ticket ticket= ticketingService.generateTicket(event, user);
            ticketingService.printTicketInfo(ticket);
        }
    }

    private static void login(Client user) {
        System.out.println("Logged in as: " + user.getId());
        while (true) {
            System.out.println("ACTOINS:");
            System.out.println("retrieveTickets - Shows info about all the tickets of the user");
            System.out.println("buyTicket - Opens the selling platform");
            System.out.println("exit - get back to the main menu");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "retrieveTickets" -> ticketingService.printClientTickets(user);
                case "buyTicket" -> {
                    System.out.println("Event id: ");
                    int eventId = scanner.nextInt();
                    scanner.nextLine();

                    Event event  = ticketingService.lookupEvent(eventId);
                    if (event != null) {
                        if (event.isAvailable(user.getSeatType())) {
                            buyTicket(user, event);
                        }
                        else {
                            System.out.println("Error: No more seats available!");
                        }
                    }
                    else {
                        System.out.println("Invalid id");
                    }
                }
                case "exit" -> {
                    return;
                }
            }
        }
    }
    public static void main(String[] args) {

        init();

        label:
        while (true) {
            System.out.println("############################TICKETING SERVICE############################\nBy Radu Costache");
            System.out.println("Please selecet one of the following actions:");

            System.out.println("registerClient - If you want to register a new Client");
            System.out.println("registerDisabled - If you want to register a disabled Client");
            System.out.println("login - If you want to start ticketing for a registered/disabled Client");
            System.out.println("loginGuest - If you want to start ticketing for a guest Client");
            System.out.println("retrieveTicket - If you want to retrieve info about a specific ticket");
            System.out.println("showEvents - If you want to see information about the current events");
            System.out.println("cancelTicket - If you have to cancel a ticket and reassing the seat");
            System.out.println("exit - Exit the app");

            System.out.println("Your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "registerClient": {
                    System.out.println("*****REGISTER CLIENT MENU*****");
                    System.out.println("Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.println("Email: ");
                    String email = scanner.nextLine().trim();
                    System.out.println("Phone: ");
                    String phone = scanner.nextLine().trim();

//                    Client newClient = new RegisteredClient(name, phone, email);
                    ticketingService.addClient(name, email, phone, "registered");

                    System.out.println("Successfully added client!!!");

                    break;
                }
                case "registerDisabled": {
                    System.out.println("*****REGISTER DISABLED CLIENT MENU*****");
                    System.out.println("Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.println("Email: ");
                    String email = scanner.nextLine().trim();
                    System.out.println("Phone: ");
                    String phone = scanner.nextLine().trim();

//                    Client newClient = new DisabledClient(name, phone, email);
                    ticketingService.addClient(name, email, phone, "disabled");

                    System.out.println("Successfully added disabled client!!!\n");
                    break;
                }
                case "loginGuest": {
                    System.out.println("LOGGING IN A GUEST USER.\n Please enter name");
                    String name = scanner.nextLine().trim();

//                    Client client = new GuestClient(name, "guest client", "guest client");

                    int id = ticketingService.addClient(name, "guest", "guest", "guest");
                    Client client = ticketingService.lookupClient(id);

                    login(client);
                    break;
                }
                case "login": {
                    System.out.println("LOGGING IN CLIENT. PLEASE ENTER CLIENT id:");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    Client client = ticketingService.lookupClient(id);
                    if (client == null || client.getSeatType() == SeatType.normal) {
                        System.out.println("INVALID CLIENT ID");
                    } else {
                        login(client);
                    }
                    break;
                }
                case "showEvents":
                    ticketingService.printEvents();
                    break;
                case "retrieveTicket": {
                    System.out.println("PLEASE ENTER TICKET id:");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Ticket ticket = ticketingService.lookupTicket(id);

                    if (ticket != null) {
                        ticketingService.printTicketInfo(ticket);
                    } else {
                        System.out.println("ERROR: TICKET NOT FOUND!!!");
                    }
                    break;
                }
                case "cancelTicket": {
                    System.out.println("Ticket id to cancel: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    ticketingService.cancelTicket(id);
                }
                case "exit":
                    break label;
                default:
                    System.out.println("INVALID COMMAND");
                    break;
            }
        }
    }
}