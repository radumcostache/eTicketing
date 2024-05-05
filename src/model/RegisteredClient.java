package model;

public class RegisteredClient extends Client {

    private int nrPoints = 0;
    public RegisteredClient(int id, String name, String phone, String email, int nrPoints) {
        super(id, name, phone, email);
        this.nrPoints = nrPoints;
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
        double SPENT_POINTS_DOLLAR = 1;
        double spentSum = getTicketPrice(basePrice) - SPENT_POINTS_DOLLAR * nrPoints;

        System.out.println("Bought ticket with " + spentSum + " Dolars, and " + nrPoints + " points");
        double EARNED_POINTS_DOLLAR = 0.2;
        nrPoints = (int) (EARNED_POINTS_DOLLAR * spentSum);

    }
    @Override
    public int getNrPoints() {
        return nrPoints;
    }
}
