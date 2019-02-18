package ru.girchev.springbootjpaexamples.service.chapter6;

import org.springframework.stereotype.Service;
import ru.girchev.springbootjpaexamples.domain.chapter6.Department;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Service
public class Chapter6_3Service {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    Department dept;

    public void initDeparnment() {
        dept = em.find(Department.class, 2L);
        System.out.println("6.3 contains FALSE=" + em.contains(dept));
    }

    public void setNameDep(String newName) {
        System.out.println("6.3 contains FALSE=" + em.contains(dept));
        System.out.println("6.3 JOINED TRUE=" + em.isJoinedToTransaction());
        dept.setName(newName);
    }

    public void print() {
        System.out.println("6.3 result=" +
                em.createNativeQuery("select p.name from chapter6.department p where p.id = 2")
                        .getSingleResult());
    }
}
