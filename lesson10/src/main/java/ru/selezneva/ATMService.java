package ru.selezneva;

import ru.selezneva.banknotes.Banknote;

import java.util.List;
import java.util.Map;

public interface ATMService {
    void fillATM(Map<Banknote, Integer> amount);
    Map<Banknote, Integer> removeBanknotesFromATM(Map<Banknote, Integer> amount);
    Map<Banknote, Integer> removeAllBanknotesFromATM();
    int getCellCapacity();
    List<Banknote> getListValidBanknote();
    Map<Banknote, Integer> getEmptyPlacesInCells();
    int getTotalCash();
    int getTotalCapacity();
}
