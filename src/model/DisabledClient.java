package model;

public class DisabledClient extends Client {
    public DisabledClient(String name, String phone, String email) {
        super(name, phone, email);
    }

    @Override
    public SeatType getSeatType() {
        return SeatType.disabled;
    }

    @Override
    public double getTicketPrice(double basePrice) {
        return basePrice;
    }

    @Override
    public void buyTicket(double basePrice) {
        double spentSum = getTicketPrice(basePrice);

        System.out.println("Bought ticket with " + spentSum);

    }

}
