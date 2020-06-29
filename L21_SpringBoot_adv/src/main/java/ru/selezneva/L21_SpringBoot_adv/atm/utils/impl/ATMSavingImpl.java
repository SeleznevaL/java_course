package ru.selezneva.L21_SpringBoot_adv.atm.utils.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.DownloadATMExeption;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.SavingATMToFileException;
import ru.selezneva.L21_SpringBoot_adv.atm.impl.ATMOperationsImpl;
import ru.selezneva.L21_SpringBoot_adv.atm.utils.ATMSaving;

import java.io.File;
import java.io.IOException;

@Component
public class ATMSavingImpl implements ATMSaving {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void save(ATM atm, String path) {
        File file = new File(path);
        try {
            objectMapper.writeValue(file, atm);
        } catch (IOException e) {
            throw  new SavingATMToFileException(e);
        }
    }

    @Override
    public ATM downloadFromFile(String path) {
        File file = new File(path);
        ATM atm = null;
        try {
            atm = objectMapper.readValue(file, ATM.class);
        } catch (IOException e) {
            throw new DownloadATMExeption(e);
        }
        return atm;
    }
}
