package ru.selezneva.L21_SpringBoot_adv.atm;


import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import java.util.List;

public interface ATMOperations {
    int add(Integer atmID, List<Nominal> pack);

    List<Nominal> get(Integer atmId, int summ);
}
