package ru.selezneva.atm;

import ru.selezneva.atm.ref.Nominal;

import java.util.List;

public interface ATM {
    int add(List<Nominal> pack);

    List<Nominal> get(int summ);
}
