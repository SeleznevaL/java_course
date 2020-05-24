package ru.selezneva.atm.cassette;

import ru.selezneva.atm.ref.Nominal;

public interface Cassette {
    void add(int count);
    void extract(int count);
    int count();
    Nominal getNominal();
    public int getCapacity();
}
