package ru.selezneva.L21_SpringBoot_adv.atm.utils;


import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;

public interface ATMSaving {
    ATM create();

    ATM download(Integer id);

}
