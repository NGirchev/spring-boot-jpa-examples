package ru.girchev.springbootjpaexamples.domain.chapter8;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter8")
public class Garage extends AbstractEntity {

    @OneToMany(mappedBy = "garage",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Car> cars;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] longText;

    @Override
    public String toString() {
        return "Garage{" +
                "id=" + id +
                ", cars=" + cars +
                '}';
    }
}
