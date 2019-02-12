package ru.girchev.springbootjpaexamples.domain.chapter6;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 Directionality: Bidirectional or Unidirectional mapping
 Cardinality: 1 -> *, * <-> *
 Ordinality: 0 -> 1 like optional

 1. Many-to-one
 2. One-to-one
 3. One-to-many
 4. Many-to-many

 Single-Valued Associations - (where the cardinality of the target is “one”
     Many-to-One Mappings
     One-to-One Mappings

 Collection-Valued Associations
    One-to-Many Mappings
    Many-to-Many Mappings

 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter6")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Department department;

    @OneToOne
    @JoinColumn
    private ParkingSpace parkingSpace;

    @ManyToMany
    @JoinTable(schema = "chapter6")
    private List<Document> documents = new ArrayList<Document>();

    @ManyToMany
    @JoinTable(schema = "chapter6")
    private List<Project> projects = new ArrayList<Project>();
}
