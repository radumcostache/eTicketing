package model;
import java.util.Date;

public class HistoricalTicket extends Ticket {
    public Date cancellationDate;
    HistoricalTicket(Event event, Client client, Seat seat) {
        super(event, client, seat);
        // current date as cancellation date
        this.cancellationDate = new Date();
    }

    @Override
    public String getInfo() {
        return "*******************************HISTORICAL TICKET*******************************\n" +
                "End Date: " + cancellationDate + "\n" +
                super.getInfo();
    }

}
