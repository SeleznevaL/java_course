package ru.selezneva.L21_SpringBoot_adv.atm.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CassetteOperationsImplTest {
    CassetteOperationsImpl subj = new CassetteOperationsImpl();
    @Mock
    Cassette cassette;

    @Before
    public void setUp() throws Exception {
        cassette = new Cassette( Nominal.ONE_HUND);
        cassette.setCount(5);
    }

    @Test
    public void add() {
        subj.add(cassette,4);
        assertEquals(Integer.valueOf(9), cassette.getCount());
    }

    @Test
    public void addZero() {
        subj.add(cassette,0);
        assertEquals(Integer.valueOf(5), cassette.getCount());
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
    }

    @Test
    public void extractZero() {
        subj.extract(cassette,0);
    }

    @Test
    public void extractMinus() {
        assertThrows(IncorectValue.class, () -> {
            subj.extract(cassette,-3);
        });
    }

    @Test
    public void count() {
    }
}
