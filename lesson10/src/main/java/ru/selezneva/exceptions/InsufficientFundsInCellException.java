package ru.selezneva.exceptions;

import ru.selezneva.banknotes.Banknote;

public class InsufficientFundsInCellException extends RuntimeException {
    public InsufficientFundsInCellException(Banknote banknote){
        super(banknote.getCurrencyName());
    }
}
