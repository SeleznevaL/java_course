package ru.selezneva.L21_SpringBoot_adv.atm.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ATMRepositoryImpl.class)
class ATMRepositoryImplTest {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ATMRepositoryImpl subj;

    @Test
    void create() {
        ATM result = subj.create(new ATM());
        ATM expect = new ATM().setId(2);
        assertEquals(expect, result);
    }

    @Test
    void findById() {
        ATM result = subj.findById(1);
        ATM expect = entityManager.find(ATM.class, 1);
        assertEquals(expect, result);
    }
}