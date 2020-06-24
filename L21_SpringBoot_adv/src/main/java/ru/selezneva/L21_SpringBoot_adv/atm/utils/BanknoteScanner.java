package ru.selezneva.L21_SpringBoot_adv.atm.utils;


import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import java.util.List;

public interface BanknoteScanner {
    List<Nominal> scanBanknote(String str);
}
