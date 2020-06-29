package ru.selezneva.L21_SpringBoot_adv.atm.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.CassetteRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CassetteOperationsImplTest {
    @MockBean
    CassetteRepository cassetteRepository;
    CassetteOperationsImpl subj;
    Cassette cassette;

    @Before
    public void setUp() throws Exception {
        subj = new CassetteOperationsImpl(cassetteRepository);
        cassette = new Cassette().setNominal(Nominal.ONE_HUND);
        cassette.setCassetteCount(5);
    }

    @Test
    public void add() {
        subj.add(cassette,4);
        verify(cassetteRepository, times(1)).update(cassette.setCassetteCount(9));
    }

    @Test
    public void addZero() {
        subj.add(cassette,0);
        verify(cassetteRepository, times(1)).update(cassette.setCassetteCount(5));
    }

    @Test
    public void addMinus() {
        assertThrows( IncorectValue.class, () -> {
            subj.add(cassette,-1);
        });
    }

    @Test
    public void extract() {
        subj.extract(cassette,3);
        verify(cassetteRepository, times(1)).update(cassette.setCassetteCount(2));
    }

    @Test
    public void extractZero() {
        subj.extract(cassette,0);
        verify(cassetteRepository, times(1)).update(cassette.setCassetteCount(5));
    }

    @Test
    public void extractMinus() {
        assertThrows(IncorectValue.class, () -> {
            subj.extract(cassette,-3);
        });
    }
}
