package ru.selezneva.L21_SpringBoot_adv.atm;


import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import java.util.List;

public interface ATMOperations {
    int add(ATM atm, List<Nominal> pack);

    List<Nominal> get(ATM atm, int summ);
}
