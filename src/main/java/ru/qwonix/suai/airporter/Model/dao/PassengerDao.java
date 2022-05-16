package ru.qwonix.suai.airporter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.qwonix.suai.airporter.model.entity.Passenger;

public interface PassengerDao extends JpaRepository<Passenger, Integer> {

    Passenger findByUsername(String username);

    @Query("select case when p.password = ?#{@controllerUtils.encodePassword(#password)} then true else false end from Passenger p where p.username=:username")
    boolean isCorrectPasswordByUsername(String username, String password);

    boolean existsPassengerByPhone(String phone);

    boolean existsPassengerByUsername(String username);

    boolean existsPassengerByPassport(String passport);

    boolean existsPassengerByEmail(String email);
}
