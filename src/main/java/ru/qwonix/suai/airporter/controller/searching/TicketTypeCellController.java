package ru.qwonix.suai.airporter.controller.searching;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.ticket.TicketController;
import ru.qwonix.suai.airporter.model.entity.Ticket;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер ячейки выбора билета
 */
@Slf4j
@Component
public class TicketTypeCellController {

    private final ControllerUtils controllerUtils;
    private final ApplicationContext applicationContext;

    @FXML
    private Button selectTicketButton;
    @FXML
    private Label priceLabel, ticketsCountLabel, airlineLabel, conditionLabel;

    @FXML
    private Label departureTimeLabel, departureCityLabel, departureDateLabel;
    @FXML
    private Label flightDurationLabel;
    @FXML
    private Label arrivalTimeLabel, arrivalCityLabel, arrivalDateLabel;

    @Value("classpath:views/ticket/ticket-view.fxml")
    private Resource ticketView;

    public TicketTypeCellController(ControllerUtils controllerUtils, ApplicationContext applicationContext) {
        this.controllerUtils = controllerUtils;
        this.applicationContext = applicationContext;
    }

    public void cellSetup(TicketType ticketType) {
        this.fieldsSetup(ticketType);

        selectTicketButton.setOnMouseClicked(event -> onSelectTicket(ticketType));
    }

    private void onSelectTicket(TicketType ticketType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ticketView.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent load = fxmlLoader.load();
            TicketController cellLayoutController = fxmlLoader.getController();
            cellLayoutController.setup(ticketType);

            controllerUtils.getMainStage().setScene(new Scene(load));
        } catch (IOException ex) {
            log.error("failed to create new ticket view: {}", ex.getMessage());
        }
    }

    private void fieldsSetup(TicketType ticketType) {
        priceLabel.setText(String.valueOf(ticketType.getPrice()));

        List<Ticket> tickets = ticketType.getTickets().stream()
                .filter(ticket -> ticket.getPassenger() == null)
                .collect(Collectors.toList());
        ticketsCountLabel.setText(String.valueOf(tickets.size()));

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

