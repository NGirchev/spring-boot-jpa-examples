package ru.girchev.springbootjpaexamples.service.UnsyncEmWithTransactions;

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
public class Chapter6_1Service2 {

    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    Department dept;

    public void initDeparnment() {
        em = emf.createEntityManager(SynchronizationType.UNSYNCHRONIZED);
        dept = em.find(Department.class, 2L);
        System.out.println("6.12 contains TRUE=" + em.contains(dept));
    }

    public void setNameDep(String newName) {
        System.out.println("6.12 contains TRUE=" + em.contains(dept));
        System.out.println("6.12 JOINED FALSE=" + em.isJoinedToTransaction());
        //Explicitly joining a JTA transaction requires a JTA transaction be currently active
//        em.joinTransaction();
        dept.setName(newName);
    }

    public void print() {
        System.out.println("6.12 result=" +
                em.createNativeQuery("select p.name from chapter6.department p where p.id = 2")
                        .getSingleResult());
    }
}
