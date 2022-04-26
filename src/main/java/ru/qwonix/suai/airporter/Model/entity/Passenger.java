package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@ToString
@Entity
@Table(name = "passenger")
public class Passenger {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "email")
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNum;

    @Column(name = "passport_data")
    private String passport;

}


