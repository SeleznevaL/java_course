package ru.selezneva.atm.utils.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.selezneva.atm.exceptions.DownloadATMExeption;
import ru.selezneva.atm.exceptions.SavingATMToFileException;
import ru.selezneva.atm.impl.ATMImpl;
import ru.selezneva.atm.utils.ATMSaving;

import java.io.File;
import java.io.IOException;

public class ATMSavingImpl implements ATMSaving {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void save(ATMImpl atm, String path) {
        File file = new File(path);
        try {
            objectMapper.writeValue(file, atm);
        } catch (IOException e) {
            throw  new SavingATMToFileException(e);
        }
    }

    @Override
    public ATMImpl downloadFromFile(String path) {
        File file = new File(path);
        ATMImpl atm = null;
        try {
            atm = objectMapper.readValue(file, ATMImpl.class);
        } catch (IOException e) {
            throw new DownloadATMExeption(e);
        }
        return atm;
    }
}
