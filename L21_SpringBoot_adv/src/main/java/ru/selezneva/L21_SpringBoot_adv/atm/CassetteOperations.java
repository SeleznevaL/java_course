package ru.selezneva.L21_SpringBoot_adv.atm;


import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;

public interface CassetteOperations {
    void add(Cassette cassette, int count);
    void extract(Cassette cassette, int count);
}
