package ru.selezneva.banknotes;

import java.util.Objects;

public class FiveHundredRubles extends AbstructBanknote {
    private final int denomination = 500;
    private final CurrencyNames currency = CurrencyNames.RUB;
    private static FiveHundredRubles instance;

    private FiveHundredRubles() {
    }

    @Override
    public int getDenomination() {
        return denomination;
    }

    @Override
    public String getCurrencyName() {
        return null;
    }

    public static Banknote getInstance() {
        if (instance == null) {
            instance = new FiveHundredRubles();
            return instance;
        }
        return instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiveHundredRubles that = (FiveHundredRubles) o;
        return denomination == this.denomination &&
                currency == this.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination, currency);
    }
}
