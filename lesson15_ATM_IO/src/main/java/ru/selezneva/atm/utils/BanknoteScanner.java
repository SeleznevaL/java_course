package ru.selezneva.atm.utils;

import ru.selezneva.atm.ref.Nominal;

import java.util.List;

public interface BanknoteScanner {
    List<Nominal> scanBanknote(String str);
}
