package ru.selezneva.L21_SpringBoot_adv.atm.exceptions;

public class IncorectValue extends ATMException{
    public IncorectValue() {}

    public IncorectValue(String message) {
        super(message);
    }
}
