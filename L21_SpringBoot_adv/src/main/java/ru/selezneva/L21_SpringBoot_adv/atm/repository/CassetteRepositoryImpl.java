package ru.selezneva.L21_SpringBoot_adv.atm.repository;

import org.springframework.stereotype.Repository;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CassetteRepositoryImpl implements CassetteRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Cassette get(int id) {
        return entityManager.find(Cassette.class, id);
    }

    @Override
    @Transactional
    public Cassette create(Cassette cassette) {
        entityManager.persist(cassette);
        entityManager.flush();
        return cassette;
    }

    @Override
    @Transactional
    public Cassette update(Cassette updateCassette) {
        Cassette cassette = entityManager.find(Cassette.class, updateCassette.getId());
        cassette.setCassetteCount(updateCassette.getCassetteCount());
        entityManager.flush();
        return cassette;
    }
}
