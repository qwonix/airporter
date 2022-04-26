package ru.qwonix.suai.airporter.views.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.TicketType;

@Component
@FxmlView("ticket-type-cell-layout.fxml")
public class TicketTypeCellController {

    @FXML
    public Label flightLabel, conditionLabel, ticketsLabel;
    @FXML
    protected Label idLabel;

    @FXML
    protected Label priceLabel;

    public void CellSetup(TicketType ticketType) {
        idLabel.setText(String.valueOf(ticketType.getId()));
        flightLabel.setText(ticketType.getFlight().getDepartureAirport().getName() + " -- " + ticketType.getFlight().getArrivalAirport().getName());
        conditionLabel.setText(ticketType.getCondition().getName());
        priceLabel.setText(String.valueOf(ticketType.getPrice()));
        ticketsLabel.setText(String.valueOf(ticketType.getTickets().size()));
    }
}

