package ru.selezneva.impl;

import ru.selezneva.ATM;
import ru.selezneva.ATMUse;
import ru.selezneva.banknotes.Banknote;
import ru.selezneva.exceptions.AmountCannotBeIssuedException;
import ru.selezneva.exceptions.BanknoteDoesNotSupportExepsion;
import ru.selezneva.exceptions.CellOverflowExceptionException;
import ru.selezneva.exceptions.InsufficientFundsInCellException;

import java.util.*;

public class ATMUseImpl implements ATMUse {
    private final ATM atm;

    public ATMUseImpl(ATM atm) {
        this.atm = atm;
    }

    @Override
    public int putMoney(Map<Banknote, Integer> amount) {
        int sum = 0;
        Map<Banknote, Integer> emptyPlacesInCells = getEmptyPlacesInCells();
        for (Banknote banknote : amount.keySet()) {
            if (!emptyPlacesInCells.containsKey(banknote)) {
                throw new BanknoteDoesNotSupportExepsion(banknote);
            }
            if (emptyPlacesInCells.get(banknote) < amount.get(banknote)) {
                throw new CellOverflowExceptionException();
            }
            sum = sum + amount.get(banknote) * banknote.getDenomination();
        }
        atm.addBanknotesToATM(amount);
        return sum;
    }

    @Override
    public Map<Banknote, Integer> getMoney(int amount) {
        Map<Banknote, Integer> result = new HashMap<>();
        Map<Banknote, Integer> map = atm.numberOfBanknotesByType();
        Set<Banknote> treeSet = new TreeSet<>(Comparator.comparingInt(Banknote::getDenomination).reversed());
        treeSet.addAll(map.keySet());
        for (Banknote banknote: treeSet) {
            int i = amount / banknote.getDenomination();
            if (i != 0) {
                i = Integer.min(i, map.get(banknote));
                result.put(banknote, i);
                amount = amount - banknote.getDenomination() * i;
            }
        }
        if (amount != 0) {
            throw new AmountCannotBeIssuedException();
        }
        atm.receiveBanknotesFromATM(result);
        return result;
    }

    @Override
    public List<Banknote> availableBanknoteDenominations() {
        List<Banknote> list = new ArrayList<>();
        for (Banknote banknote : atm.numberOfBanknotesByType().keySet()) {
            if (atm.numberOfBanknotesByType().get(banknote) > 0) {
                list.add(banknote);
            }
        }
        return list;
    }

    private Map<Banknote, Integer> getEmptyPlacesInCells() {
        int cellSize = atm.getCellSize();
        Map<Banknote, Integer> map = atm.numberOfBanknotesByType();
        Map<Banknote, Integer> result = new HashMap<>();
        for (Banknote banknote : map.keySet()) {
            result.put(banknote, cellSize - map.get(banknote));
        }
        return result;
    }

    private Map<Banknote, Integer> removeBanknotesFromATM(Map<Banknote, Integer> amount) {
        Map<Banknote, Integer> map = atm.numberOfBanknotesByType();
        for(Banknote banknote: amount.keySet()) {
            if (map.get(banknote) > amount.get(banknote)) {
                throw new InsufficientFundsInCellException(banknote);
            }
        }
        atm.receiveBanknotesFromATM(amount);
        return amount;
    }
}
