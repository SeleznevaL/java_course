package ru.selezneva.L21_SpringBoot_adv.atm.exceptions;

public class SavingATMToFileException extends RuntimeException {
    public SavingATMToFileException(Throwable cause) {
        super(cause);
    }
}
