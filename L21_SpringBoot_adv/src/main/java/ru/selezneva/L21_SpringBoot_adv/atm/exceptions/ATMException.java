package ru.selezneva.L21_SpringBoot_adv.atm.exceptions;

public class ATMException extends RuntimeException {
    public ATMException() {}

    public ATMException(String message) {
        super(message);
    }

    public String getSbrfError() {
        return "СБ Ошибка";
    }

    public ATMException(Throwable cause) {
        super(cause);
    }
}
