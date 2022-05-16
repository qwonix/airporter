package ru.qwonix.suai.airporter.controller.ticket;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.SearchableComboBox;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.View;
import ru.qwonix.suai.airporter.model.dao.TicketDao;
import ru.qwonix.suai.airporter.model.entity.Ticket;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class TicketDetailsController {

    private final ControllerUtils controllerUtils;
    private final TicketDao ticketDao;

    public Label priceLabel;
    public Label conditionDescription, conditionLabel;
    public Label aircraftLabel, aircraftProductionDate;
    public Label ticketTitleLabel, flightDurationLabel;

    public Label airlineName;

    public Label departureTimeLabel, departureDateLabel, departureCityLabel, departureAirportLabel;
    public Label arrivalTimeLabel, arrivalDateLabel, arrivalCityLabel, arrivalAirportLabel;

    public Button buyButton, backButton;
    public SearchableComboBox seatComboBox;

    public TicketDetailsController(ControllerUtils controllerUtils, TicketDao ticketDao) {
        this.controllerUtils = controllerUtils;
        this.ticketDao = ticketDao;
    }


    public void setup(TicketType ticketType) {
        priceLabel.setText(ticketType.getPrice() + "₽");
        conditionLabel.setText(ticketType.getCondition().getName());
        conditionDescription.setText(ticketType.getCondition().getDescription());

        aircraftLabel.setText(ticketType.getFlight().getAircraft().getModel());
        LocalDate production_date = ticketType.getFlight().getAircraft().getProduction_date();

        aircraftProductionDate.setText(production_date.format(DateTimeFormatter.ofPattern("MMMM, yyyy")));

        ticketTitleLabel.setText(ticketType.getFlight().getDepartureAirport().getCity()
                + " – " + ticketType.getFlight().getArrivalAirport().getCity());

        flightDurationLabel.setText(Duration.between(ticketType.getFlight().getScheduledDeparture()
                , ticketType.getFlight().getScheduledArrival()).toMinutes() + "мин.");

        airlineName.setText(ticketType.getFlight().getAircraft().getAirline().getName());


        departureTimeLabel.setText(ticketType.getFlight().getScheduledDeparture()
                .format(DateTimeFormatter.ofPattern("h:mm")));
        departureDateLabel.setText(ticketType.getFlight().getScheduledDeparture()
                .format(DateTimeFormatter.ofPattern("d MMM E")));

        departureCityLabel.setText(ticketType.getFlight().getDepartureAirport().getCity());
        departureAirportLabel.setText(ticketType.getFlight().getDepartureAirport().getName());

        arrivalTimeLabel.setText(ticketType.getFlight().getScheduledArrival()
                .format(DateTimeFormatter.ofPattern("h:mm")));
        arrivalDateLabel.setText(ticketType.getFlight().getScheduledArrival()
                .format(DateTimeFormatter.ofPattern("d MMM E")));
        arrivalCityLabel.setText(ticketType.getFlight().getArrivalAirport().getCity());
        arrivalAirportLabel.setText(ticketType.getFlight().getArrivalAirport().getName());

        seatComboBox.setItems(FXCollections.observableArrayList(
                ticketDao.findAllByTicketType(ticketType).stream().map(Ticket::getSeatId).collect(Collectors.toList())
        ));
        seatComboBox.getSelectionModel().selectFirst();

        buyButton.setOnMouseClicked(event -> {

            controllerUtils.getPassenger().ifPresentOrElse(passenger -> {
                        System.out.println(passenger.getUsername() + "купил");
                    },
                    () -> {
                        controllerUtils.openAuthorization();
                    });


        });

        backButton.setOnMouseClicked(event -> {
            controllerUtils.changeScene(View.TICKET_SEARCH);
        });

    }
}
