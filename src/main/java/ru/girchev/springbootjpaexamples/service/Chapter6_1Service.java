package ru.girchev.springbootjpaexamples.service;

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
public class Chapter6_1Service {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    Department dept;

    @Transactional
    public void init() {
        System.out.println("Initialization...");
        Employee employee = new Employee();
        em.persist(employee);
        Department department = new Department();
        department.setName("Department");
        department.getEmployees().add(employee);
        employee.setDepartment(department);
        em.persist(department);
    }

    public void initDeparnment() {
        dept = em.find(Department.class, 2L);
        System.out.println("6.1 contains FALSE=" + em.contains(dept));
    }

    public void setNameDep(String newName) {
        System.out.println("6.1 contains FALSE=" + em.contains(dept));
        System.out.println("6.1 JOINED FALSE=" + em.isJoinedToTransaction());
        dept.setName(newName);
    }

    public void print() {
        System.out.println("6.1 result=" +
                em.createNativeQuery("select p.name from chapter6.department p where p.id = 2")
                        .getSingleResult());
    }
}
