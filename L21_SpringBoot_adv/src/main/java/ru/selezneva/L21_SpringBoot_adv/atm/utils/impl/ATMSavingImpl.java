package ru.selezneva.L21_SpringBoot_adv.atm.utils.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.Cassette;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.DownloadATMExeption;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.SavingATMToFileException;
import ru.selezneva.L21_SpringBoot_adv.atm.impl.ATMOperationsImpl;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.ATMRepository;
import ru.selezneva.L21_SpringBoot_adv.atm.repository.CassetteRepository;
import ru.selezneva.L21_SpringBoot_adv.atm.utils.ATMSaving;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ATMSavingImpl implements ATMSaving {
    @Autowired
    private ATMRepository atmRepository;
    @Autowired
    private CassetteRepository cassetteRepository;

    @Override
    public ATM create() {
        ATM atm = atmRepository.create(new ATM());
        for ( Nominal nominal : Nominal.values() ) {
            cassetteRepository.create(new Cassette().setNominal(nominal).setAtmId(atm.getId()).setCassetteCount(0));
        }
        return atmRepository.findById(atm.getId());
    }

    @Override
    public ATM download(Integer atmId) {
        return atmRepository.findById(atmId);
    }
}
