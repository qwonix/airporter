package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table(name = "airline")
public class Airline {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "company_tin")
    private String companyTin;

    @Column(name = "registered_address")
    private String registeredAddress;

    @Column(name = "chief_executive")
    private String chiefExecutive;


}