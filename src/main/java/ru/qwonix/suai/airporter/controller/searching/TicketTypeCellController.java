package ru.qwonix.suai.airporter.controller.searching;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.ticket.TicketDetailsController;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Component
public class TicketTypeCellController {

    private final ControllerUtils controllerUtils;
    private final ApplicationContext applicationContext;

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

    @Value("classpath:views/ticket/ticket.fxml")
    private Resource ticketView;

    public TicketTypeCellController(ControllerUtils controllerUtils, ApplicationContext applicationContext) {
        this.controllerUtils = controllerUtils;
        this.applicationContext = applicationContext;
    }

    private void onSelect(TicketType ticketType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ticketView.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent load = fxmlLoader.load();
            TicketDetailsController cellLayoutController = fxmlLoader.getController();
            cellLayoutController.setup(ticketType);

            controllerUtils.getMainStage().setScene(new Scene(load));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void cellSetup(TicketType ticketType) {
        priceLabel.setText(ticketType.getPrice() + "руб");
        ticketsCountLabel.setText(ticketType.getTickets().size() + " билетов");

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

        selectTicketButton.setOnMouseClicked(event -> onSelect(ticketType));
    }
}

