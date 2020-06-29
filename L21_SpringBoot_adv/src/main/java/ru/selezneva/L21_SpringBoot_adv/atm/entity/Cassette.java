package ru.selezneva.L21_SpringBoot_adv.atm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

@Getter
@Setter
@NoArgsConstructor
public class Cassette {
    private Nominal nominal;
    private Integer count;
    private final Integer capacity = 1000;

    public Cassette(Nominal nominal ) {
        this.nominal = nominal;
        this.count = 0;
    }
}
