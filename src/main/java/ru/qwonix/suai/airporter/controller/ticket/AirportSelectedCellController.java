package ru.qwonix.suai.airporter.controller.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.Airport;

@Component
public class AirportSelectedCellController {
    @FXML
    private Label airportCity, airportId;

    public void cellSetup(Airport airport) {
        airportCity.setText(airport.getCity());
        airportId.setText(airport.getId());
    }
}