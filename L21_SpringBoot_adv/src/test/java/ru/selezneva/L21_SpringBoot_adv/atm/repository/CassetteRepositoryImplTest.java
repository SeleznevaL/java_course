package ru.selezneva.L21_SpringBoot_adv.atm.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CassetteRepositoryImpl.class)
class CassetteRepositoryImplTest {
    @Autowired
    CassetteRepositoryImpl subj;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
        Cassette cassette = subj.create(new Cassette().setCassetteCount(0).setNominal(Nominal.FIVE_THOUS).setAtmId(1));
        Cassette expected = new Cassette().setCassetteCount(0).setNominal(Nominal.FIVE_THOUS).setAtmId(1).setAtmId(1).setId(2);
        assertEquals(expected, cassette);
    }

    @Test
    void update() {
        Cassette cassette = entityManager.find(Cassette.class, 1);
        Cassette update = subj.update(cassette.setCassetteCount(10));
        assertEquals(cassette.setCassetteCount(10), update);
    }
}