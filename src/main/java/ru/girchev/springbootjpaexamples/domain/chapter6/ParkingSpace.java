package ru.girchev.springbootjpaexamples.domain.chapter6;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter6")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "parkingSpace")
    private Employee owner;
}
