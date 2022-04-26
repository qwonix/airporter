package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@ToString
@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @Column(name = "model")
    private String model;

    @Column(name = "description")
    private String description;

    @Column(name = "range")
    private int range;

    @Column(name = "production_year")
    private LocalDate production_date;
}