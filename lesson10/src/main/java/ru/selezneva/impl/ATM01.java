package ru.selezneva.impl;

import ru.selezneva.ATM;
import ru.selezneva.banknotes.Banknote;
import ru.selezneva.exceptions.CellOverflowExceptionException;
import ru.selezneva.exceptions.InsufficientFundsInCellException;

import java.util.*;

public class ATM01 implements ATM {
    private final static int ATM_TOTAL_CAPACITY = 100000;
    private final int CELL_SIZE;
    private HashMap<Banknote, Integer> storage = new HashMap<>();
    private static ATM01 instance;

    private ATM01(int numberTypesOfNotes) {
        CELL_SIZE = (int) ATM_TOTAL_CAPACITY / numberTypesOfNotes;
    }

    public static ATM initializeATM(List<Banknote> banknotes) {
        int i = banknotes.size();
        if (instance == null) {
            instance = new ATM01(i);
            for (Banknote banknote : banknotes) {
                instance.storage.put(banknote, 0);

            }
            return instance;
        }

        return instance;
    }

    @Override
    public void addBanknotesToATM(Map<Banknote, Integer> amount) {
        Set<Banknote> banknotes = amount.keySet();
        for (Banknote banknote : banknotes) {
            if (storage.get(banknote) + amount.get(banknote) <= CELL_SIZE) {
                int quantityInCell = storage.get(banknote) + amount.get(banknote);
                storage.put(banknote, quantityInCell);
            } else throw new CellOverflowExceptionException();
        }
    }

    @Override
    public void receiveBanknotesFromATM(Map<Banknote, Integer> amount) {
        Set<Banknote> banknotes = amount.keySet();
        for (Banknote banknote : banknotes) {
            if (storage.get(banknote) - amount.get(banknote) >= 0) {
                int quantityInCell = storage.get(banknote) - amount.get(banknote);
                storage.put(banknote, quantityInCell);
            } else throw new InsufficientFundsInCellException(banknote);
        }
    }

    @Override
    public Map<Banknote, Integer> numberOfBanknotesByType() {
         return Collections.unmodifiableMap(storage);
    }

    @Override
    public int getCellSize() {
        return CELL_SIZE;
    }

    @Override
    public int getTotalCapacity() {
        return ATM_TOTAL_CAPACITY;
    }
}
