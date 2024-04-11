package model;

public class RegisteredClient extends Client {
    private static double EARNED_POINTS_DOLLAR = 0.2;
    private static double SPENT_POINTS_DOLLAR = 1;

    private int nrPoints = 0;
    public RegisteredClient(String name, String phone, String email) {
        super(name, phone, email);
        nrPoints = 0;
    }

    @Override
    public SeatType getSeatType() {
        return SeatType.exclusive;
    }

    public double getTicketPrice(double basePrice) {
        return 95.0 / 100.0 * basePrice;
    }

    @Override
    public void buyTicket(double basePrice) {
        double spentSum = getTicketPrice(basePrice) - SPENT_POINTS_DOLLAR * nrPoints;

        System.out.println("Bought ticket with " + spentSum + " Dolars, and " + nrPoints + " points");
        nrPoints = (int) (EARNED_POINTS_DOLLAR * spentSum);

    }
}
