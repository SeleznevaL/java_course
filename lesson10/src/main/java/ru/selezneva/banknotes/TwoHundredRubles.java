package ru.selezneva.banknotes;

import java.util.Objects;

public class TwoHundredRubles extends AbstructBanknote {
    private final int denomination = 200;
    private final CurrencyNames currency = CurrencyNames.RUB;
    private static TwoHundredRubles instance;

    private TwoHundredRubles() {
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
            instance = new TwoHundredRubles();
            return instance;
        }
        return instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoHundredRubles that = (TwoHundredRubles) o;
        return denomination == this.denomination &&
                currency == this.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination, currency);
    }
}
