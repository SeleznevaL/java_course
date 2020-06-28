package ru.selezneva.L21_SpringBoot_adv.atm.repository;

import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;

import java.util.List;

public interface CassetteRepository {
    Cassette get(int id);

    Cassette create(Cassette cassette);

    Cassette update(Cassette cassette);
}
