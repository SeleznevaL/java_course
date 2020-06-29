package ru.selezneva.L21_SpringBoot_adv.atm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.selezneva.L21_SpringBoot_adv.atm.ATMOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.CashMashineOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.CassetteOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.dto.CashPair;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.ATMRepository;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.CassetteRepository;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.CassetteRepositoryImpl;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ATMOperationsImpl implements ATMOperations, CashMashineOperations {
    private CassetteOperations cassetteOperations;
    private ATMRepository atmRepository;

    @Autowired
    ATMOperationsImpl(CassetteOperations cassetteOperations, ATMRepository atmRepository) {
        this.cassetteOperations = cassetteOperations;
        this.atmRepository = atmRepository;
    }

    @Override
    @Transactional
    public int add(Integer atmID, List<Nominal> pack) {
        TreeMap<Nominal, Cassette> cassetteMap = new TreeMap<>();
        for(Cassette cassette : atmRepository.findById(atmID).getCassettes()) {
            cassetteMap.put(cassette.getNominal(), cassette);
        }
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
            for (Nominal n: cassetteMap.keySet()) {
                if (n.equals(nominal) && (cassetteMap.get(n).getCapacity() - nominalMap.get(nominal) >= 0)) {
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
            for (Nominal n: cassetteMap.keySet()) {
                if (n.equals(nominal)) {
                    cassetteOperations.add(cassetteMap.get(n), nominalMap.get(nominal));
                    sum += nominal.getValue() * nominalMap.get(nominal);
                }
            }
        }
        return sum;
    }

    @Override
    public List<Nominal> get(Integer atmId, int sum ) {
        TreeMap<Nominal, Cassette> cassetteMap = new TreeMap<>();
        for(Cassette cassette : atmRepository.findById(atmId).getCassettes()) {
            cassetteMap.put(cassette.getNominal(), cassette);
        }
        //набираем сумму доступными купюрами
        Map<Nominal, Integer> result = new HashMap<>();
        int temp = sum;
        TreeSet<Nominal> nominals = new TreeSet<>(Comparator.comparingInt(Nominal::getValue).reversed());
        nominals.addAll(cassetteMap.keySet());
        for (Nominal nominal : nominals) {
            int k = temp / nominal.getValue();
            if (k > 0) {
                k = Math.min(k, cassetteMap.get(nominal).getCassetteCount());
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
            cassetteOperations.extract(cassetteMap.get(nominal), result.get(nominal));
        }
        list.sort(Comparator.comparingInt(nominal -> nominal.getValue()));
        return list;
    }

    @Override
    public List<CashPair> balance(ATM atm) {
        return
                atm.getCassettes().stream()
                        .map( cassette -> new CashPair( cassette.getNominal(), cassette.getCassetteCount() ) )
                        .collect( Collectors.toList() );
    }
}
