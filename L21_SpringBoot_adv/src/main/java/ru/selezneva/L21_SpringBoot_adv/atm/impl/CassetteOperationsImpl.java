package ru.selezneva.L21_SpringBoot_adv.atm.impl;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.selezneva.L21_SpringBoot_adv.atm.CassetteOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.CassetteRepository;

import javax.transaction.Transactional;

@Getter
@Component
public class CassetteOperationsImpl implements CassetteOperations {
    private CassetteRepository cassetteRepository;

    @Autowired
    public  CassetteOperationsImpl(CassetteRepository cassetteRepository) {
        this.cassetteRepository = cassetteRepository;
    }

    @Override
    @Transactional
    public void add(Cassette cassette, int count ) {
        if ( count < 0 ) throw new IncorectValue();
        count = cassette.getCassetteCount() + count;
        cassetteRepository.update(cassette.setCassetteCount(count));
    }

    @Override
    @Transactional
    public void extract(Cassette cassette, int count ) {
        if ( count < 0 ) throw new IncorectValue();
        count = cassette.getCassetteCount() - count;
        cassetteRepository.update(cassette.setCassetteCount(count));
    }
}
