package ru.selezneva.L21_SpringBoot_adv.atm.repository;

import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;

public interface ATMRepository {
    ATM create(ATM atm);
    ATM findById(Integer id);
}
