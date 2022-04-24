package ru.qwonix.suai.airporter.views.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("ticket-cell-layout.fxml")
public class TicketCellController {

    @FXML
    protected Label idLabel;
    @FXML
    protected Label seatLabel;
    @FXML
    protected Label priceLabel;

    public void CellSetup(Ticket ticket) {
        idLabel.setText(String.valueOf(ticket.getId()));
        seatLabel.setText(ticket.getSeatId());
        priceLabel.setText(String.valueOf(ticket.getPrice()));
    }
}

