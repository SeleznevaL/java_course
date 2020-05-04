package ru.selezneva.impl;

import ru.selezneva.ATM;
import ru.selezneva.ATMService;
import ru.selezneva.banknotes.Banknote;
import ru.selezneva.exceptions.CellOverflowExceptionException;
import ru.selezneva.exceptions.InsufficientFundsInCellException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMServiceImpl implements ATMService {
    private final ATM atm;

    public ATMServiceImpl(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void fillATM(Map<Banknote, Integer> amount) {
        Map<Banknote, Integer> emptyPlacesInCells = getEmptyPlacesInCells();
        for(Banknote banknote: emptyPlacesInCells.keySet()) {
            if (amount.get(banknote) != null && emptyPlacesInCells.get(banknote) < amount.get(banknote)) {
                throw new CellOverflowExceptionException();
            }
        }
        atm.addBanknotesToATM(amount);
    }

    @Override
    public Map<Banknote, Integer> removeBanknotesFromATM(Map<Banknote, Integer> amount) {
        Map<Banknote, Integer> map = atm.numberOfBanknotesByType();
        for(Banknote banknote: amount.keySet()) {
            if (map.get(banknote) < amount.get(banknote)) {
                throw new InsufficientFundsInCellException(banknote);
            }
        }
        atm.receiveBanknotesFromATM(amount);
        return amount;
    }

    @Override
    public Map<Banknote, Integer> removeAllBanknotesFromATM() {
        Map<Banknote, Integer> map = atm.numberOfBanknotesByType();
        atm.receiveBanknotesFromATM(map);
        return map;
    }

    @Override
    public int getCellCapacity() {
        return atm.getCellSize();
    }

    @Override
    public List<Banknote> getListValidBanknote() {
        List<Banknote> list = new ArrayList<>();
        for (Banknote banknote : atm.numberOfBanknotesByType().keySet()) {
            list.add(banknote);
        }
        return list;
    }

    @Override
    public Map<Banknote, Integer> getEmptyPlacesInCells() {
        int cellSize = atm.getCellSize();
        Map<Banknote, Integer> map = atm.numberOfBanknotesByType();
        Map<Banknote, Integer> result = new HashMap<>();
        for (Banknote banknote : map.keySet()) {
            result.put(banknote, cellSize - map.get(banknote));
        }
        return result;
    }

    @Override
    public int getTotalCash() {
        Map<Banknote, Integer> map = atm.numberOfBanknotesByType();
        int amount = 0;
        for (Banknote banknote : map.keySet()) {
            amount += map.get(banknote) * banknote.getDenomination();
        }
        return amount;
    }

    @Override
    public int getTotalCapacity() {
        return atm.getTotalCapacity();
    }
}
