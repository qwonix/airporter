package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@ToString
@Entity
@Table(name = "employee")
public class Employee {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNum;

    @Column(name = "passport_data")
    private String passport;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(name = "salary")
    private Double salary;
}


