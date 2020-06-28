package ru.selezneva.L21_SpringBoot_adv.atm.repository;

import org.springframework.stereotype.Repository;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ATMRepositoryImpl implements ATMRepository {
    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public ATM create(ATM atm) {
        entityManager.persist(atm);
        entityManager.flush();
        return atm;
    }

    @Override
    public ATM findById(Integer id) {
        return entityManager.find(ATM.class, id);
    }
}
