package ru.selezneva.atm.exceptions;

public class ATMException extends RuntimeException {
    public ATMException() {}

    public ATMException(String message) {
        super(message);
    }

    public String getSbrfError() {
        return "СБ Ошибка";
    }
}
