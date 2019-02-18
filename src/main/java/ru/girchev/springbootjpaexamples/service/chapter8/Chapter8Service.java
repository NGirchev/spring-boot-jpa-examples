package ru.girchev.springbootjpaexamples.service.chapter8;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.girchev.springbootjpaexamples.domain.chapter8.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Service
@Transactional
public class Chapter8Service {

    @PersistenceContext
    private EntityManager em;

    public void init() {
        Garage garage = new Garage();
        em.persist(garage);

        CarOwner ivanov = new CarOwner();
        ivanov.setName("Ivanov");
        em.persist(ivanov);

        CarOwner petrov = new CarOwner();
        petrov.setName("Petrov");
        em.persist(petrov);

        CarOwner sidorov = new CarOwner();
        sidorov.setName("Sidorov");
        em.persist(sidorov);

        Specification s = new Specification();

        Car skoda = new Car();
        skoda.setName("skoda");
        s.setSpeed(new BigDecimal(200.0));
        skoda.setSpecification(s);
        skoda.setGarage(garage);
        ivanov.getCars().add(skoda);
        sidorov.getCars().add(skoda);
        em.persist(skoda);

        SportCar bugatti = new SportCar();
        bugatti.setName("bugatti");
        s = new Specification();
        s.setSpeed(new BigDecimal(800.0));
        bugatti.setSpecification(s);
        bugatti.setRaceCar(true);
        bugatti.setGarage(garage);
        ivanov.getCars().add(bugatti);
        petrov.getCars().add(bugatti);
        em.persist(bugatti);

        SUV jeep = new SUV();
        jeep.setName("jeep");
        s = new Specification();
        s.setSpeed(new BigDecimal(150.0));
        jeep.setSpecification(s);
        jeep.setAllWeelDrive(true);
        jeep.setGarage(garage);
        petrov.getCars().add(jeep);
        em.persist(jeep);
    }

    public void testFetchQuery() {
        //fetch
        String q;
        List<Garage> garageList;

        // LazyInitializationException
        q = "select distinct g from Garage g";
        garageList = em.createQuery(q, Garage.class).getResultList();
        garageList.stream().forEach(em::detach);
//        System.out.println(garageList);

        // LazyInitializationException
        q = "select distinct g from Garage g JOIN g.cars";
        garageList = em.createQuery(q, Garage.class).getResultList();
        garageList.stream().forEach(em::detach);
//        System.out.println(garageList);

        q = "select distinct g from Garage g JOIN FETCH g.cars";
        garageList = em.createQuery(q, Garage.class).getResultList();
        garageList.stream().forEach(em::detach);
        System.out.println(garageList);
    }

    /**
     * LAZY in JPA (unlike EAGER) is merely a hint, which JPA implementations may ignore.
     *
     * ObjectDB always loads basic fields eagerly, regardless of the LAZY / EAGER setting.
     *
     * If you have very large strings that you want to load lazily - keep them in separate
     * entity objects. For example, you can define an entity class, LargeString, with a
     * single String field, setting references to LargeString as LAZY.
     *
     * But
     * fetch all properties - can help to eager loading all of single-valued objects
     */
    public void testSingleValuedLazy() {
        System.out.println(em.createQuery(
                "select distinct g from Garage g fetch all properties", Garage.class)
                .getSingleResult());
    }

    /**
     * WHERE allows the following:
     *
     * 1. Navigation operator (.)
     * 2. Unary +/–
     * 3. Multiplication (*) and division (/)
     * 4. Addition (+) and subtraction (–)
     * 5. Comparison operators: =, >, >=, <, <=, <>, [NOT] BETWEEN, [NOT] LIKE, [NOT]
     * IN, IS [NOT] NULL, IS [NOT] EMPTY, [NOT] MEMBER [OF]
     * 6. Logical operators (AND, OR, NOT)
     */
    public void whereTest() {
        System.out.println(em.createQuery(
                "select c.name from CarOwner c" +
                        " where c.name LIKE 'I_%'").getResultList());
        System.out.println(em.createQuery(
                "select distinct '%%%' from CarOwner c" +
                        " where '%%%' LIKE '\\%\\%\\%'").getSingleResult());
        // Escaping the underscore makes it a mandatory part of the expression.
        // For example, “QA_East” would match, but “QANorth” would not.
        System.out.println(em.createQuery(
                "select distinct 'QA_East' from CarOwner c" +
                        " where 'QA_East' LIKE 'QA\\_%' ESCAPE '\\'").getSingleResult());
        // For escaping need to add ' ESCAPE '\' in the end of expression
        System.out.println(em.createQuery(
                "select distinct '%_123' from CarOwner c" +
                        " where '%_123' LIKE '\\%\\_123' ESCAPE '\\'").getSingleResult());

        // объявление идентификационной переменной g в подзапросе переопределяет
        // то же объявление из родительского запроса.

        // Throw exception!!!

        // ■ NOTE Overriding an identification variable name in a subquery is not
        // guaranteed to be supported by all providers. Unique names should be
        // used to ensure portability.
        String query;
//        query = " SELECT g.cars.size" +
//                " FROM Garage g" +
//                " WHERE g.cars = (SELECT MAX(g.cars)" +
//                " FROM Garage g)";
//        System.out.println(em.createQuery(query).getResultList());

        query = " SELECT g.cars.size" +
                " FROM Garage g" +
                " WHERE g.cars.size = (SELECT MAX(g2.cars.size)" +
                " FROM Garage g2)";
        System.out.println(em.createQuery(query).getResultList());

        query = "SELECT g FROM Garage g WHERE EXISTS " +
                "(SELECT 1 FROM g.cars c WHERE c.name = 'bugatti')";
        System.out.println(em.createQuery(query).getResultList());

        query = "SELECT c FROM Car c WHERE c.owners IS NOT EMPTY";
        System.out.println(em.createQuery(query).getResultList());

        query = "SELECT c FROM Car c WHERE" +
                " (select o from CarOwner o where upper(o.name) like upper('Ivanov'))" +
                " MEMBER OF c.owners";
        System.out.println(em.createQuery(query).getResultList());

        // + EXISTS, ANY, ALL, and SOME Expressions
        // WHERE e.salary < ALL (SELECT d.salary FROM e.directs d)

        // ■ TIP The option to place scalar expressions in the SELECT clause was
        // introduced in JPA 2.0. (SUM, Multiplication (*))
    }

    /**
     * Function Description:
     *
     * ABS(number)
     * The ABS function returns the unsigned version of the number
     * argument. The result type is the same as the argument type
     * (integer, float, or double).
     *
     * CONCAT(string1, string2)
     * The CONCAT function returns a new string that is the
     * concatenation of its arguments, string1 and string2.
     *
     * CURRENT_DATE
     * The CURRENT_DATE function returns the current date as
     * defined by the database server.
     *
     * CURRENT_TIME
     * The CURRENT_TIME function returns the current time as
     * defined by the database server.
     *
     * CURRENT_TIMESTAMP
     * The CURRENT_TIMESTAMP function returns the current
     * timestamp as defined by the database server.
     *
     * INDEX(identification variable)
     * The INDEX function returns the position of an
     * entity within an ordered list.
     *
     * LENGTH(string)
     * The LENGTH function returns the number of characters in the string argument.
     *
     * LOCATE(string1, string2 [, start])
     * The LOCATE function returns the position of string1
     * in string2, optionally starting at the position indicated by start. The result
     * is zero if the string cannot be found.
     *
     * LOWER(string)
     * The LOWER function returns the lowercase form of the string argument.
     *
     * MOD(number1, number2)
     * The MOD function returns the modulus of numeric arguments
     * number1 and number2 as an integer.
     *
     * SIZE(collection)
     * The SIZE function returns the number of elements in the collection,
     * or zero if the collection is empty.
     *
     * SQRT(number)
     * The SQRT function returns the square root of the number argument as a double.
     *
     * SUBSTRING(string, start, end)
     * The SUBSTRING function returns a portion of the input string,
     * starting at the index indicated by start up to length characters.
     * String indexes are measured starting from one.
     *
     * UPPER(string)
     * The UPPER function returns the uppercase form of the string
     * argument.
     *
     * TRIM([[LEADING|TRAILING|BOTH] [char] FROM] string)
     * The TRIM function removes leading and/or trailing characters
     * from a string. If the optional LEADING, TRAILING, or BOTH
     * keyword is not used, both leading and trailing characters are
     * removed. The default trim character is the space character.
     */

    /**
     * CASE {WHEN <cond_expr> THEN <scalar_expr>}+ ELSE <scalar_expr> END
     */
}
