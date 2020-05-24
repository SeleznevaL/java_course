package ru.selezneva.atm.impl;

import ru.selezneva.atm.ATM;
import ru.selezneva.atm.CashMashine;
import ru.selezneva.atm.cassette.Cassette;
import ru.selezneva.atm.cassette.CassetteImpl;
import ru.selezneva.atm.dto.CashPair;
import ru.selezneva.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.atm.exceptions.IncorectValue;
import ru.selezneva.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.atm.ref.Nominal;

import java.util.*;
import java.util.stream.Collectors;


public class ATMImpl implements ATM, CashMashine {
    private List<Cassette> cassettes;

    //конструктор создающий внутренную структуру ячеек
    public ATMImpl(List<CashPair> cashPairs) {
        cassettes = new ArrayList<>();
        for (CashPair cashPair : cashPairs) {
            CassetteImpl cassette = new CassetteImpl(cashPair.getNominal());
            if (cashPair.getCount() <= cassette.getCapacity()) {
                cassette.add(cashPair.getCount());
                cassettes.add(cassette);
            } else  {
                throw new IncorectValue();
            }
        }
    }


    public ATMImpl() {
        cassettes = new ArrayList<>();
        for ( var nominal : Nominal.values() ) {
            cassettes.add( new CassetteImpl( nominal ) );
        }
    }

    @Override
    public int add( List<Nominal> pack ) {
        //получаем перечень номиналов купюр в пачке
        Map<Nominal, Integer> nominalMap = new HashMap<Nominal, Integer>();
        for (Nominal nominal : pack) {
            if (nominalMap.containsKey(nominal)) {
                nominalMap.put(nominal, nominalMap.get(nominal) + 1);
            } else {
                nominalMap.put(nominal, 1);
            }
        }

        //провереям, что операция добавления может быть выполнена для каждой ячейки
        for (Nominal nominal : nominalMap.keySet()) {
            boolean flag = false;
            for (Cassette cassette : cassettes) {
                if (cassette.getNominal().equals(nominal) && (cassette.getCapacity() - nominalMap.get(nominal) >= 0)) {
                    flag = true;
                }
            }
            if (!flag) {
                throw new NoAvailableRequestCount();
            }
        }
        //добавляем купюры в кассеты
        int sum = 0;
        for (Nominal nominal : nominalMap.keySet()) {
            for (Cassette cassette : cassettes) {
                if (cassette.getNominal().equals(nominal)) {
                    cassette.add(nominalMap.get(nominal));
                    sum += nominal.getValue() * nominalMap.get(nominal);
                }
            }
        }
        return sum;
    }

    @Override
    public List<Nominal> get( int sum ) {
        //получаем список доступных в банкомате номиналов
        Map<Nominal, Integer> nominalMap = new TreeMap<>((o1, o2) -> o2.getValue() - o1.getValue());
        for (Cassette cassette : cassettes) {
            nominalMap.put(cassette.getNominal(), cassette.count());
        }
        //набираем сумму доступными купюрами
        Map<Nominal, Integer> result = new HashMap<>();
        int temp = sum;
        for (Nominal nominal : nominalMap.keySet()) {
            int k = temp / nominal.getValue();
            if (k > 0) {
                k = Math.min(k, nominalMap.get(nominal));
                temp = temp - k * nominal.getValue();
                result.put(nominal, k);
            }
        }
        if (temp != 0 ) {
            throw new AmountCannotBeCollected();
        }
        //извлекаем купюры из кассет
        List<Nominal> list = new ArrayList<>();
        for (Nominal nominal : result.keySet()) {
            for (int i = 1; i <= result.get(nominal); i++) {
                list.add(nominal);
            }
            for (Cassette cassette : cassettes) {
                if (cassette.getNominal().equals(nominal)) {
                    cassette.extract(result.get(nominal));
                }
            }
        }
        list.sort(Comparator.comparingInt(nominal -> nominal.getValue()));
        return list;
    }

    @Override
    public List<CashPair> balance() {
        return
                cassettes.stream()
                        .map( cassette -> new CashPair( cassette.getNominal(), cassette.count() ) )
                        .collect( Collectors.toList() );
    }
}
