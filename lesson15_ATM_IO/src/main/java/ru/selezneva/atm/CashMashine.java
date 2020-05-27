package ru.selezneva.atm;

import ru.selezneva.atm.dto.CashPair;

import java.util.List;

public interface CashMashine {
    List<CashPair> balance();
}
