package ru.selezneva.atm.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.selezneva.atm.dto.CashPair;
import ru.selezneva.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.atm.ref.Nominal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName(value = "Банкомат должен")
public class ATMImplTest {
    private ATMImpl atm;

    @BeforeEach
    public void setUp() throws Exception {
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
            cashPairs.add(new CashPair(nominal, 10));
        }
        atm = new ATMImpl(cashPairs);
    }

    @Test
    @DisplayName(value = "принимать купюры и увеличивать баланс")
    public void add() {
        List<Nominal> pack = new ArrayList<>();
        for (int i  = 1; i <= 2; i++) {
            pack.add(Nominal.ONE_HUND);
        }
        for (int i  = 1; i <= 1; i++) {
            pack.add(Nominal.FIVE_THOUS);
        }
        int add = atm.add(pack);
        assertEquals(5200, add);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_HUND) && !nominal.equals(Nominal.FIVE_THOUS) && !nominal.equals(Nominal.ONE_THOUS)) {
                cashPairs.add(new CashPair(nominal, 10));
            } else if (nominal.equals(Nominal.ONE_HUND)) {
                cashPairs.add(new CashPair(nominal, 12));
            } else if (nominal.equals(Nominal.FIVE_THOUS)) {
                cashPairs.add(new CashPair(nominal, 11));
            }
        }
        assertEquals(cashPairs, atm.balance());
    }

    @Test
    @DisplayName(value = "принимать пустой список купюр и не увеличивать баланс")
    public void addZero() {
        List<Nominal> pack = new ArrayList<>();
        int add = atm.add(pack);
        assertEquals(0, add);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, atm.balance());
    }

    @Test
    @DisplayName(value = "не принимать купюры недоступного номинала")
    public void addInvalidNominal() {
        List<Nominal> pack = new ArrayList<>();
        for (int i  = 1; i <= 2; i++) {
            pack.add(Nominal.ONE_THOUS);
        }
        for (int i  = 1; i <= 1; i++) {
            pack.add(Nominal.FIVE_THOUS);
        }
        assertThrows(NoAvailableRequestCount.class, () -> {
            atm.add(pack);
        });
    }

    @Test
    @DisplayName(value = "не принимать купюры в количестве выше допустимой вместимости")
    public void addSellOverflow() {
        List<Nominal> pack = new ArrayList<>();
        for (int i  = 1; i <= 10000; i++) {
            pack.add(Nominal.ONE_THOUS);
        }
        for (int i  = 1; i <= 1; i++) {
            pack.add(Nominal.FIVE_THOUS);
        }
        assertThrows(NoAvailableRequestCount.class, () -> {
            atm.add(pack);
        });
    }

    @Test
    @DisplayName(value = "выдавать запрошенную сумму и уменьшать баланс")
    public void get() {
        List<Nominal> nominals = atm.get(12300);
        List<Nominal> nominalList = new ArrayList<>();
        nominalList.add(Nominal.ONE_HUND);
        nominalList.add(Nominal.TWO_HUND);
        nominalList.add(Nominal.TWO_THOUS);
        nominalList.add(Nominal.FIVE_THOUS);
        nominalList.add(Nominal.FIVE_THOUS);
        assertEquals(nominalList, nominals);
        List<CashPair> cashPairs = new ArrayList<>();
        cashPairs.add(new CashPair(Nominal.ONE_HUND, 9));
        cashPairs.add(new CashPair(Nominal.TWO_HUND, 9));
        cashPairs.add(new CashPair(Nominal.FIVE_HUND, 10));
        cashPairs.add(new CashPair(Nominal.TWO_THOUS, 9));
        cashPairs.add(new CashPair(Nominal.FIVE_THOUS, 8));
        assertEquals(cashPairs, atm.balance());
    }

    @Test
    @DisplayName(value = "не выдавать запрошенную сумму, если она не можен быть набрана, не изменять баланс")
    public void getAmountCannotBeIssued() {
        assertThrows(AmountCannotBeCollected.class, () -> {
            atm.get(12350);
        });
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, atm.balance());
    }

    @Test
    @DisplayName(value = "не выдавать отрицательную запрошенную сумму, не изменять баланс")
    public void getNegative() {
        assertThrows(AmountCannotBeCollected.class, () -> {
            atm.get(-12300);
        });
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, atm.balance());
    }

    @Test
    @DisplayName(value = "выдавать пустой список купюр при запросе нулевой суммы, не изменять баланс")
    public void getZero() {
        List<Nominal> nominals = atm.get(0);
        List<Nominal> nominalList = new ArrayList<>();
        assertEquals(nominalList, nominals);
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, atm.balance());
    }

    @Test
    @DisplayName(value = "возвращать бвлвнс")
    public void balance() {
        List<CashPair> balance = atm.balance();
        List<CashPair> cashPairs = new ArrayList<>();
        for (Nominal nominal : Nominal.values()) {
            if (!nominal.equals(Nominal.ONE_THOUS))
                cashPairs.add(new CashPair(nominal, 10));
        }
        assertEquals(cashPairs, balance);
    }
}