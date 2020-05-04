package ru.selezneva.exceptions;

import ru.selezneva.banknotes.Banknote;

public class BanknoteDoesNotSupportExepsion extends RuntimeException {
    public BanknoteDoesNotSupportExepsion(Banknote banknote){
        super(banknote.getCurrencyName());
    }
}
