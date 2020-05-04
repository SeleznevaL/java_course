package ru.selezneva;

import ru.selezneva.banknotes.Banknote;

import java.util.List;
import java.util.Map;

public interface ATM {
    void addBanknotesToATM(Map<Banknote, Integer> amount);
    void receiveBanknotesFromATM(Map<Banknote, Integer> amount);
    Map<Banknote, Integer> numberOfBanknotesByType();
    int getCellSize();
    int getTotalCapacity();
}
