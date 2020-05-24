package ru.selezneva.atm.exceptions;

public class ATMException extends RuntimeException {
    public String getSbrfError() {
        return "СБ Ошибка";
    }
}
