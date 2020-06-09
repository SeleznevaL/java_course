package ru.selezneva.atm.exceptions;

public class IncorectValue extends ATMException{
    public IncorectValue() {}

    public IncorectValue(String message) {
        super(message);
    }
}
