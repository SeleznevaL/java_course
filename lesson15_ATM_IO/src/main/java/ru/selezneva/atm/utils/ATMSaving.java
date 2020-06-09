package ru.selezneva.atm.utils;

import ru.selezneva.atm.ATM;
import ru.selezneva.atm.impl.ATMImpl;

import java.io.File;
import java.util.Optional;

public interface ATMSaving {
    void save(ATMImpl atm, String path);

    ATMImpl downloadFromFile(String path);
}
