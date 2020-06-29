package ru.selezneva.L21_SpringBoot_adv.atm.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.selezneva.L21_SpringBoot_adv.atm.CassetteOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.dto.CashPair;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ATMOperationsImplTest {
    ATMOperationsImpl subj;
    ATM atm;
    @MockBean
    CassetteOperations cassetteOperations;

    @Before
    public void setUp() throws Exception {
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        subj = new ATMOperationsImpl(cassetteOperations);
        atm = new ATM(cashPairs);
    }

    @Test
    public void add() {
        List<Nominal> pack = new ArrayList<>();
        for (int i  = 1; i <= 2; i++) {
            pack.add(Nominal.ONE_HUND);
        }
        for (int i  = 1; i <= 1; i++) {
            pack.add(Nominal.FIVE_THOUS);
        }
        int add = subj.add(atm, pack);
        assertEquals(5200, add);
        verify(cassetteOperations, times(1)).add(atm.getCassettes().get(Nominal.ONE_HUND), 2);
        verify(cassetteOperations, times(1)).add(atm.getCassettes().get(Nominal.FIVE_THOUS), 1);
    }

    @Test
    public void addZero() {
        List<Nominal> pack = new ArrayList<>();
        int add = subj.add(atm, pack);
        assertEquals(0, add);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, subj.balance(atm));
    }

    @Test
    public void addInvalidNominal() {
        List<Nominal> pack = new ArrayList<>();
        for (int i  = 1; i <= 2; i++) {
            pack.add(Nominal.ONE_THOUS);
        }
        for (int i  = 1; i <= 1; i++) {
            pack.add(Nominal.FIVE_THOUS);
        }
        assertThrows(NoAvailableRequestCount.class, () -> {
            subj.add(atm, pack);
        });
    }

    @Test
    public void addSellOverflow() {
        List<Nominal> pack = new ArrayList<>();
        for (int i  = 1; i <= 10000; i++) {
            pack.add(Nominal.ONE_THOUS);
        }
        for (int i  = 1; i <= 1; i++) {
            pack.add(Nominal.FIVE_THOUS);
        }
        assertThrows(NoAvailableRequestCount.class, () -> {
            subj.add(atm, pack);
        });
    }

    @Test
    public void get() {
        List<Nominal> nominals = subj.get(atm, 12300);
        List<Nominal> nominalList = new ArrayList<>();
        nominalList.add(Nominal.ONE_HUND);
        nominalList.add(Nominal.TWO_HUND);
        nominalList.add(Nominal.TWO_THOUS);
        nominalList.add(Nominal.FIVE_THOUS);
        nominalList.add(Nominal.FIVE_THOUS);
        assertEquals(nominalList, nominals);
        List<CashPair> cashPairs = new ArrayList<>();
        verify(cassetteOperations, times(1)).extract(atm.getCassettes().get(Nominal.ONE_HUND), 1);
        verify(cassetteOperations, times(1)).extract(atm.getCassettes().get(Nominal.TWO_HUND), 1);
        verify(cassetteOperations, times(1)).extract(atm.getCassettes().get(Nominal.TWO_THOUS), 1);
        verify(cassetteOperations, times(1)).extract(atm.getCassettes().get(Nominal.FIVE_THOUS), 2);
    }

    @Test
    public void getAmountCannotBeIssued() {
        assertThrows(AmountCannotBeCollected.class, () -> {
            subj.get(atm, 12350);
        });
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, subj.balance(atm));
    }

    @Test
    public void getNegative() {
        assertThrows(AmountCannotBeCollected.class, () -> {
            subj.get(atm, -12300);
        });
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, subj.balance(atm));
    }

    @Test
    public void getZero() {
        List<Nominal> nominals = subj.get(atm,0);
        List<Nominal> nominalList = new ArrayList<>();
        assertEquals(nominalList, nominals);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, subj.balance(atm));
    }

    @Test
    public void balance() {
        List<CashPair> balance = subj.balance(atm);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, balance);
    }
}