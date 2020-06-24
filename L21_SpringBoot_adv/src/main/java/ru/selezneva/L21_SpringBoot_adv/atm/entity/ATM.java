package ru.selezneva.L21_SpringBoot_adv.atm.entity;

import lombok.Getter;
import lombok.Setter;
import ru.selezneva.L21_SpringBoot_adv.atm.dto.CashPair;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import java.util.List;
import java.util.TreeMap;

@Getter
@Setter
public class ATM {
    private TreeMap<Nominal, Cassette> cassettes;

    //конструктор создающий внутренную структуру ячеек
    public ATM(List<CashPair> cashPairs) {
        cassettes = new TreeMap<>();
        for (CashPair cashPair : cashPairs) {
            Cassette cassette = new Cassette(cashPair.getNominal());
            if (cashPair.getCount() <= cassette.getCapacity()) {
                cassette.setCount(cashPair.getCount());
                cassettes.put(cashPair.getNominal(), cassette);
            } else  {
                throw new IncorectValue();
            }
        }
    }

    public ATM() {
        cassettes = new TreeMap<>();
        for ( var nominal : Nominal.values() ) {
            cassettes.put(nominal, new Cassette(nominal) );
        }
    }

}
