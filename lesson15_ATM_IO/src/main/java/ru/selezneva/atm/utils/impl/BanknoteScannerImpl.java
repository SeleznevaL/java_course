package ru.selezneva.atm.utils.impl;

import ru.selezneva.atm.exceptions.IncorectValue;
import ru.selezneva.atm.ref.Nominal;
import ru.selezneva.atm.utils.BanknoteScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BanknoteScannerImpl implements BanknoteScanner {

    @Override
    public List<Nominal> scanBanknote(String str) {
        String[] split = str.split(" ");
        List<Nominal> nominals = new ArrayList<>();
        for (String s : split) {
            Nominal nominal = null;
            switch (s) {
                case "100":
                    nominals.add(Nominal.ONE_HUND);
                    break;
                case "200":
                    nominals.add(Nominal.TWO_HUND);
                    break;
                case "500":
                    nominals.add(Nominal.FIVE_HUND);
                    break;
                case "1000":
                    nominals.add(Nominal.ONE_THOUS);
                    break;
                case "2000":
                    nominals.add(Nominal.TWO_THOUS);
                    break;
                case "5000":
                    nominals.add(Nominal.FIVE_THOUS);
                    break;
                default: throw new IncorectValue("Купюра не может быть считана: " + s);
            }
        }
        return nominals;
    }
}
