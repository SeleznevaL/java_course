package ru.selezneva.L21_SpringBoot_adv.atm;


import ru.selezneva.L21_SpringBoot_adv.atm.dto.CashPair;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;

import java.util.List;

public interface CashMashineOperations {
    List<CashPair> balance(ATM atm);
}
