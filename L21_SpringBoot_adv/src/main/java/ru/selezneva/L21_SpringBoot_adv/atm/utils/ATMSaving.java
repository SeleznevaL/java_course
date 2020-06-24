package ru.selezneva.L21_SpringBoot_adv.atm.utils;


import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;

public interface ATMSaving {
    void save(ATM atm, String path);

    ATM downloadFromFile(String path);
}
