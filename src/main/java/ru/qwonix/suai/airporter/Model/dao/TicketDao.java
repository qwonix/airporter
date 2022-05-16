package ru.qwonix.suai.airporter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qwonix.suai.airporter.model.entity.Ticket;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.util.List;

public interface TicketDao extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByTicketType(TicketType ticketType);

    List<Ticket> findAllByTicketTypeAndPassengerIsNull(TicketType ticketType);
}
