package ru.girchev.springbootjpaexamples.service.chapter6.UnsyncEmWithTransactions;

import org.springframework.stereotype.Service;
import ru.girchev.springbootjpaexamples.domain.chapter6.Department;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Service
@javax.transaction.Transactional
public class Chapter6_2Service2 {

    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    Department dept;

    public void initDeparnment() {
        em = emf.createEntityManager(SynchronizationType.UNSYNCHRONIZED);
        dept = em.find(Department.class, 2L);
        System.out.println("6.22 contains TRUE=" + em.contains(dept));
    }

    public void setNameDep(String newName) {
        System.out.println("6.22 contains TRUE=" + em.contains(dept));
        System.out.println("6.22 JOINED FALSE=" + em.isJoinedToTransaction());
        em.joinTransaction();
        dept.setName(newName);
    }

    public void print() {
        System.out.println("6.22 result=" +
                em.createNativeQuery("select p.name from chapter6.department p where p.id = 2")
                        .getSingleResult());
    }
}
