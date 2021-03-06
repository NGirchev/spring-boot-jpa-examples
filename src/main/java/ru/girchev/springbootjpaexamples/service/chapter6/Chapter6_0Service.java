package ru.girchev.springbootjpaexamples.service.chapter6;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.girchev.springbootjpaexamples.domain.chapter6.Department;
import ru.girchev.springbootjpaexamples.domain.chapter6.Employee;

import javax.persistence.*;

/**
 *
 * will be like statefull bean in JEE for tests
 *
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Service
@Transactional
public class Chapter6_0Service {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    Department dept;

    public void initDeparnment() {
        dept = em.find(Department.class, 2L);
        System.out.println("6.0 contains TRUE=" + em.contains(dept));
    }

    public void setNameDep(String newName) {
        System.out.println("6.0 contains TRUE=" + em.contains(dept));
        System.out.println("6.0 JOINED TRUE=" + em.isJoinedToTransaction());
        dept.setName(newName);
    }

    public void print() {
        System.out.println("6.0 result=" +
                em.createNativeQuery("select p.name from chapter6.department p where p.id = 2")
                        .getSingleResult());
    }
}
