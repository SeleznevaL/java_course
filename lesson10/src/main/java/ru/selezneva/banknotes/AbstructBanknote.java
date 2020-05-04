package ru.selezneva.banknotes;

import java.util.Objects;

abstract class AbstructBanknote implements Banknote {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
