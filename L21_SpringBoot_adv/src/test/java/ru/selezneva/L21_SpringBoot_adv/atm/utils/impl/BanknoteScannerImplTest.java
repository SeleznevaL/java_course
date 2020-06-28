package ru.selezneva.L21_SpringBoot_adv.atm.utils.impl;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;
import ru.selezneva.L21_SpringBoot_adv.atm.utils.BanknoteScanner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class BanknoteScannerImplTest {

    BanknoteScanner subj = new BanknoteScannerImpl();

    @Test
    public void scanBanknote() {
        List<Nominal> nominals = subj.scanBanknote("5000 100 100 200 500 5000");
        List<Nominal> list = new ArrayList<>();
        list.add(Nominal.FIVE_THOUS);
        list.add(Nominal.ONE_HUND);
        list.add(Nominal.ONE_HUND);
        list.add(Nominal.TWO_HUND);
        list.add(Nominal.FIVE_HUND);
        list.add(Nominal.FIVE_THOUS);
        assertEquals(list, nominals);
    }

    @Test
    public void scanBanknoteEmpty() {
        List<Nominal> nominals = subj.scanBanknote(" ");
        List<Nominal> list = new ArrayList<>();
        assertEquals(list, nominals);
    }

    @Test
    public void scanBanknoteError() {
        Assertions.assertThrows(IncorectValue.class, () -> {
            subj.scanBanknote("50000 100 100 200 500 5000");
        });
    }
}
