package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;


@Data
@ToString
@Entity
@Table(name = "crew")
public class Crew {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "crewing_date")
    private ZonedDateTime crewingDate;

    @ManyToOne
    @JoinColumn(name = "captain_id")
    private Employee captain;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "crew_employee",
            joinColumns = {@JoinColumn(name = "crew_id")},
            inverseJoinColumns = {@JoinColumn(name = "emp_id")})
    private List<Employee> employees;

}

