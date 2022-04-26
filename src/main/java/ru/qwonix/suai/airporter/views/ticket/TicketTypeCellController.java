package ru.qwonix.suai.airporter.views.ticket;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.time.format.DateTimeFormatter;

@Component
@FxmlView("ticket-type-cell-layout.fxml")
public class TicketTypeCellController {


    public Button selectTicketButton;
    public Label priceLabel;
    public Label ticketsCountLabel;
    public Label airlineLabel;
    public Label conditionLabel;
    public Label departureTimeLabel;
    public Label departureCityLabel;
    public Label departureDateLabel;
    public Label flightDurationLabel;
    public Label arrivalTimeLabel;
    public Label arrivalCityLabel;
    public Label arrivalDateLabel;

    public void CellSetup(TicketType ticketType) {
        priceLabel.setText(ticketType.getPrice() + "руб");
        ticketsCountLabel.setText(ticketType.getTickets().size() + " билетов");

        selectTicketButton.setOnMouseClicked(event -> {
            System.out.println("select " + ticketType.getId());
        });

        airlineLabel.setText(ticketType.getFlight().getAircraft().getAirline().getName());
        conditionLabel.setText(ticketType.getCondition().getName());

        departureTimeLabel.setText(ticketType.getFlight().getScheduledDeparture()
                .format(DateTimeFormatter.ofPattern("h:mm")));
        departureCityLabel.setText(ticketType.getFlight().getDepartureAirport().getCity());
        departureDateLabel.setText(ticketType.getFlight().getScheduledDeparture()
                .format(DateTimeFormatter.ofPattern("d MMM uuu, E")));

        flightDurationLabel.setText("todo");

        arrivalTimeLabel.setText(ticketType.getFlight().getScheduledArrival()
                .format(DateTimeFormatter.ofPattern("h:mm")));
        arrivalCityLabel.setText(ticketType.getFlight().getArrivalAirport().getCity());
        arrivalDateLabel.setText(ticketType.getFlight().getScheduledArrival()
                .format(DateTimeFormatter.ofPattern("d MMM uuu, E")));


    }
}

