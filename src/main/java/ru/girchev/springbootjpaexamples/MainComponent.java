package ru.girchev.springbootjpaexamples;

import fr.zebasto.spring.post.initialize.PostInitialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.girchev.springbootjpaexamples.service.*;
import ru.girchev.springbootjpaexamples.service.UnsyncEmWithTransactions.*;

import javax.transaction.*;

/**
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Component
public class MainComponent {

    @Autowired
    private Chapter6_0Service chapter60Service;
    @Autowired
    private Chapter6_1Service chapter61Service;
    @Autowired
    private Chapter6_2Service chapter62Service;
    @Autowired
    private Chapter6_3Service chapter63Service;
    @Autowired
    private Chapter6_4Service chapter64Service;

    @Autowired
    private Chapter6_0Service2 chapter60Service2;
    @Autowired
    private Chapter6_1Service2 chapter61Service2;
    @Autowired
    private Chapter6_2Service2 chapter62Service2;
    @Autowired
    private Chapter6_3Service2 chapter63Service2;
    @Autowired
    private Chapter6_4Service2 chapter64Service2;

    @Autowired
    private UserTransaction userTransaction;

    @PostInitialize
    public void init() {
        try {
            System.out.println("PostInitialize");
            chapter61Service.init();

            containerManagedPc();
            applicationManagedPc();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void containerManagedPc() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        System.out.println("containerManagedPc------------------");
        // spring @Transactional + PersistenceContextType.EXTENDED
        // changes in DB name
        // PC shared between two method
        chapter60Service.initDeparnment();
        chapter60Service.setNameDep("MyFavoriteName0");
        chapter60Service.print();

        // without transaction + PersistenceContextType.TRANSACTION
        // not changes in DB name
        // PC empty, LazyInitializationException
        chapter61Service.initDeparnment();
        chapter61Service.setNameDep("MyFavoriteName1");
        chapter61Service.print();

        // @javax.transaction.Transactional + PersistenceContextType.EXTENDED
        // LIKE WITH A SPRING TRANSACTIONS
        chapter62Service.initDeparnment();
        chapter62Service.setNameDep("MyFavoriteName2");
        chapter62Service.print();

        // UserTransaction + PersistenceContextType.TRANSACTION
        // changes in DB name
        // BUT PC empty and not shared between two method. WAT!?
        userTransaction.begin();
        chapter63Service.initDeparnment();
        chapter63Service.setNameDep("MyFavoriteName3");
        userTransaction.commit();
        chapter63Service.print();

        // spring @Transactional + PersistenceContextType.TRANSACTION
        // NOT changes in DB name
        // PC not shared, LazyInitializationException
//  WARN  bitronix.tm.twopc.Preparer: executing transaction with 0 enlisted resource
        chapter64Service.initDeparnment();
        chapter64Service.setNameDep("MyFavoriteName4");
        chapter64Service.print();
    }

    /**
     * Always em.contains(entity)=true
     * But EM not synchronized with Transaction
     * NOT changes in DB name before joinTransaction()
     */
    private void applicationManagedPc() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        System.out.println("applicationManagedPc------------------");
        // spring @Transactional
        chapter60Service2.initDeparnment();
        chapter60Service2.setNameDep("MyFavoriteName02");
        chapter60Service2.print();

        // without transaction
        chapter61Service2.initDeparnment();
        chapter61Service2.setNameDep("MyFavoriteName12");
        chapter61Service2.print();

        // @javax.transaction.Transactional
        chapter62Service2.initDeparnment();
        chapter62Service2.setNameDep("MyFavoriteName22");
        chapter62Service2.print();

        // UserTransaction
        userTransaction.begin();
        chapter63Service2.initDeparnment();
        chapter63Service2.setNameDep("MyFavoriteName32");
        userTransaction.commit();
        chapter63Service2.print();

        // spring @Transactional + PersistenceContextType.TRANSACTION
        // changes in DB name
        // PC shared
        chapter64Service2.initDeparnment();
        chapter64Service2.setNameDep("MyFavoriteName42");
        chapter64Service2.print();
    }
}
