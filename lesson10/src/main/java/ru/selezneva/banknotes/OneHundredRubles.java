package ru.selezneva.banknotes;

import java.util.Objects;

public class OneHundredRubles extends AbstructBanknote {
    private final int denomination = 100;
    private final CurrencyNames currency = CurrencyNames.RUB;
    private static OneHundredRubles instance;


    @Override
    public int getDenomination() {
        return denomination;
    }

    @Override
    public String getCurrencyName() {
        return currency.name();
    }

    public static Banknote getInstance() {
        if (instance == null) {
            instance = new OneHundredRubles();
            return instance;
        }
        return instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneHundredRubles that = (OneHundredRubles) o;
        return denomination == that.denomination &&
                currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination, currency);
    }
}
