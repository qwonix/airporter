package ru.qwonix.suai.airporter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qwonix.suai.airporter.model.entity.TicketType;

public interface TicketTypeDao extends JpaRepository<TicketType, Integer> {
}
