package ru.qwonix.suai.airporter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qwonix.suai.airporter.model.entity.Airport;

public interface AirportDao extends JpaRepository<Airport, String> {
}
