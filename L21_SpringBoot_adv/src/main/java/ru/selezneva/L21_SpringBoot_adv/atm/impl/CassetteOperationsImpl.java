package ru.selezneva.L21_SpringBoot_adv.atm.impl;


import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.selezneva.L21_SpringBoot_adv.atm.CassetteOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;

@Getter
@Component
public class CassetteOperationsImpl implements CassetteOperations {
    @Override
    public void add(Cassette cassette, int count ) {
        if ( count < 0 ) throw new IncorectValue();
        count = cassette.getCount() + count;
        cassette.setCount(count);
    }

    @Override
    public void extract(Cassette cassette, int count ) {
        if ( count < 0 ) throw new IncorectValue();
        count = cassette.getCount() - count;
        cassette.setCount(count);
    }
}
