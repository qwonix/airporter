package ru.qwonix.suai.airporter.controller.searching;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.Airport;

/**
 * Контроллер ячейки SearchableComboBox
 */
@Component
public class AirportCellController {
    @FXML
    private Label airportCity, airportId, airportCountry;

    public void cellSetup(Airport airport) {
        airportCity.setText(airport.getCity());

        airportCountry.setText(airport.getCountry());
        airportId.setText(airport.getId());
    }
}
