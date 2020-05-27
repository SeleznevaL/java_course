package ru.selezneva.atm.utils.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.selezneva.atm.dto.CashPair;
import ru.selezneva.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.atm.exceptions.IncorectValue;
import ru.selezneva.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.atm.impl.ATMImpl;
import ru.selezneva.atm.ref.Nominal;
import ru.selezneva.atm.utils.BanknoteScanner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName(value = "Сканер купюр должен ")
public class BanknoteScannerImplTest {
    BanknoteScanner banknoteScanner = new BanknoteScannerImpl();

    @Test
    @DisplayName(value = "принимать сроку значений через пробел и возвращать список купюр")
    public void scanBanknote() {
        List<Nominal> nominals = banknoteScanner.scanBanknote("5000 100 100 200 500 5000");
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
    @DisplayName(value = "принимать пустую строку и возвращать пустой список купюр")
    public void scanBanknoteEmpty() {
        List<Nominal> nominals = banknoteScanner.scanBanknote(" ");
        List<Nominal> list = new ArrayList<>();
        assertEquals(list, nominals);
    }

    @Test
    @DisplayName(value = "принимать сроку значений с ошибкой и выбрасывать исключение")
    public void scanBanknoteError() {
        assertThrows(IncorectValue.class, () -> {
            banknoteScanner.scanBanknote("50000 100 100 200 500 5000");
        });
    }
}
