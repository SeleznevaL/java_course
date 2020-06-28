package ru.selezneva.L21_SpringBoot_adv.atm.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.selezneva.L21_SpringBoot_adv.atm.CassetteOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.dto.CashPair;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.ATMRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ATMOperationsImplTest {
    ATM atm;
    @MockBean
    CassetteOperations cassetteOperations;
    @MockBean
    ATMRepository atmRepository;

    ATMOperationsImpl subj;


    @Before
    public void setUp() throws Exception {
        subj = new ATMOperationsImpl(cassetteOperations, atmRepository);
        atm = new ATM().setId(1);
        List<Cassette> cassettes = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS)) {
                Cassette cassette = new Cassette().setNominal(nominal).setCassetteCount(2);
                cassettes.add(cassette);
            }
        }
        atm.setCassettes(cassettes);
        when(atmRepository.findById(1)).thenReturn(atm);
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
        int add = subj.add(atm.getId(), pack);
        assertEquals(5200, add);
        verify(cassetteOperations, times(1)).add(new Cassette().setNominal(Nominal.ONE_HUND).setCassetteCount(2), 2);
        verify(cassetteOperations, times(1)).add(new Cassette().setNominal(Nominal.FIVE_THOUS).setCassetteCount(2), 1);
    }

    @Test
    public void addZero() {
        List<Nominal> pack = new ArrayList<>();
        int add = subj.add(atm.getId(), pack);
        assertEquals(0, add);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 2));
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
        Assertions.assertThrows(NoAvailableRequestCount.class, () -> {
            subj.add(atm.getId(), pack);
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
        Assertions.assertThrows(NoAvailableRequestCount.class, () -> {
            subj.add(atm.getId(), pack);
        });
    }

    @Test
    public void get() {
        List<Nominal> nominals = subj.get(atm.getId(), 12300);
        List<Nominal> nominalList = new ArrayList<>();
        nominalList.add(Nominal.ONE_HUND);
        nominalList.add(Nominal.TWO_HUND);
        nominalList.add(Nominal.TWO_THOUS);
        nominalList.add(Nominal.FIVE_THOUS);
        nominalList.add(Nominal.FIVE_THOUS);
        assertEquals(nominalList, nominals);
        List<CashPair> cashPairs = new ArrayList<>();
        verify(cassetteOperations, times(1)).extract(new Cassette().setNominal(Nominal.ONE_HUND).setCassetteCount(2), 1);
        verify(cassetteOperations, times(1)).extract(new Cassette().setNominal(Nominal.TWO_HUND).setCassetteCount(2), 1);
        verify(cassetteOperations, times(1)).extract(new Cassette().setNominal(Nominal.TWO_THOUS).setCassetteCount(2), 1);
        verify(cassetteOperations, times(1)).extract(new Cassette().setNominal(Nominal.FIVE_THOUS).setCassetteCount(2), 2);
    }

    @Test
    public void getAmountCannotBeIssued() {
        Assertions.assertThrows(AmountCannotBeCollected.class, () -> {
            subj.get(atm.getId(), 12350);
        });
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 2));
        }
        assertEquals(cashPairs, subj.balance(atm));
    }

    @Test
    public void getNegative() {
        Assertions.assertThrows(AmountCannotBeCollected.class, () -> {
            subj.get(atm.getId(), -12300);
        });
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 2));
        }
        assertEquals(cashPairs, subj.balance(atm));
    }

    @Test
    public void getZero() {
        List<Nominal> nominals = subj.get(atm.getId(),0);
        List<Nominal> nominalList = new ArrayList<>();
        assertEquals(nominalList, nominals);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 2));
        }
        assertEquals(cashPairs, subj.balance(atm));
    }

    @Test
    public void balance() {
        List<CashPair> balance = subj.balance(atm);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 2));
        }
        assertEquals(cashPairs, balance);
    }
}