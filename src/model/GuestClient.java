package model;

public class GuestClient extends Client {

    public GuestClient(String name, String phone, String email) {
        super(name, phone, email);
    }

    @Override
    public SeatType getSeatType() {
        return SeatType.normal;
    }

    @Override
    public double getTicketPrice(double basePrice) {
        return basePrice * 0.5;
    }

    @Override
    public void buyTicket(double basePrice) {
        double spentSum = getTicketPrice(basePrice);

        System.out.println("Bought ticket with " + spentSum);

    }


}
