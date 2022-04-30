package ru.qwonix.suai.airporter.сontroller.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.time.format.DateTimeFormatter;

@Component

public class TicketTypeCellController {

    @FXML
    public Button selectTicketButton;
    @FXML
    public Label priceLabel;
    @FXML
    public Label ticketsCountLabel;
    @FXML
    public Label airlineLabel;
    @FXML
    public Label conditionLabel;

    @FXML
    public Label departureTimeLabel, departureCityLabel, departureDateLabel;
    @FXML
    public Label flightDurationLabel;
    @FXML
    public Label arrivalTimeLabel, arrivalCityLabel, arrivalDateLabel;

    public void cellSetup(TicketType ticketType) {
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

