package ru.qwonix.suai.airporter.controller.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Component
public class TicketTypeCellController {

    @FXML
    private Button selectTicketButton;
    @FXML
    private Label priceLabel;
    @FXML
    private Label ticketsCountLabel;
    @FXML
    private Label airlineLabel;
    @FXML
    private Label conditionLabel;

    @FXML
    private Label departureTimeLabel, departureCityLabel, departureDateLabel;
    @FXML
    private Label flightDurationLabel;
    @FXML
    private Label arrivalTimeLabel, arrivalCityLabel, arrivalDateLabel;

    private TicketType ticketType;

    private void onSelect(MouseEvent event) {

    }

    public void cellSetup(TicketType ticketType) {
        this.ticketType = ticketType;

        priceLabel.setText(ticketType.getPrice() + "руб");
        ticketsCountLabel.setText(ticketType.getTickets().size() + " билетов");

        selectTicketButton.setOnMouseClicked(this::onSelect);

        airlineLabel.setText(ticketType.getFlight().getAircraft().getAirline().getName());
        conditionLabel.setText(ticketType.getCondition().getName());

        departureTimeLabel.setText(ticketType.getFlight().getScheduledDeparture()
                .format(DateTimeFormatter.ofPattern("h:mm")));
        departureCityLabel.setText(ticketType.getFlight().getDepartureAirport().getCity());
        departureDateLabel.setText(ticketType.getFlight().getScheduledDeparture()
                .format(DateTimeFormatter.ofPattern("d MMM uuu, E")));

        flightDurationLabel.setText(Duration.between(ticketType.getFlight().getScheduledDeparture(), ticketType.getFlight().getScheduledArrival()).toMinutes() + "мин.");

        arrivalTimeLabel.setText(ticketType.getFlight().getScheduledArrival()
                .format(DateTimeFormatter.ofPattern("h:mm")));
        arrivalCityLabel.setText(ticketType.getFlight().getArrivalAirport().getCity());
        arrivalDateLabel.setText(ticketType.getFlight().getScheduledArrival()
                .format(DateTimeFormatter.ofPattern("d MMM uuu, E")));

    }
}

