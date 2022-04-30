package ru.qwonix.suai.airporter.controller.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.Airport;

@Component
public class AirportCellController {
    @FXML
    private Label airportCity, airportId, airportCountry;

    private Airport airport;

    public void cellSetup(Airport airport) {
        this.airport = airport;

        airportCity.setText(airport.getCity());

        airportCountry.setText(airport.getCountry());
        airportId.setText(airport.getId());
    }
}
