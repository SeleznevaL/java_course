package ru.selezneva;

import ru.selezneva.banknotes.Banknote;

import java.util.List;
import java.util.Map;

public interface ATMUse {
    int putMoney(Map<Banknote, Integer> amount);
    Map<Banknote, Integer> getMoney(int amount);
    List<Banknote> availableBanknoteDenominations();
}
