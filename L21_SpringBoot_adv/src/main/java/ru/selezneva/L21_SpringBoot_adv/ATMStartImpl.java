package ru.selezneva.L21_SpringBoot_adv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.selezneva.L21_SpringBoot_adv.atm.ATMOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.CashMashineOperations;
import ru.selezneva.L21_SpringBoot_adv.atm.dto.CashPair;
import ru.selezneva.L21_SpringBoot_adv.atm.entity.ATM;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.DownloadATMExeption;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;
import ru.selezneva.L21_SpringBoot_adv.atm.utils.ATMSaving;
import ru.selezneva.L21_SpringBoot_adv.atm.utils.BanknoteScanner;

import java.util.List;
import java.util.Scanner;

@Slf4j
@Component
public class ATMStartImpl implements ATMStart {
    private final ATMSaving atmSaving;
    private final ATMOperations atmOperations;
    private final CashMashineOperations cmo;
    private final BanknoteScanner banknoteScanner;
    private Integer atmId;

    public ATMStartImpl(ATMSaving atmSaving, ATMOperations atmOperations, CashMashineOperations cmo, BanknoteScanner banknoteScanner) {
        this.atmSaving = atmSaving;
        this.atmOperations = atmOperations;
        this.cmo = cmo;
        this.banknoteScanner = banknoteScanner;
    }

    @Override
    public void start(Integer id) {
        try {
            if (id != 0) {
                ATM atm = atmSaving.download(id);
                atmId = id;
                log.info("Состояние банкомата прочитано из базы данных");
            } else {
                ATM atm = atmSaving.create();
                atmId = atm.getId();
                log.info("Пустой банкомат инициализирован");
            }
        } catch (DownloadATMExeption e) {
            log.error("Ошибка чтения состояния банкомата {} ", e.getStackTrace());
        }
        printATMBalance(atmId);

        int answer = 0;
        while (answer != 3) {
            answer = nextInt("Введите номер пункта меню: \n 1. Принять наличные \n 2. Выдать наличные \n 3. Выйти");
            switch (answer) {
                case 1:
                    cashAcceptance(atmId);
                    break;
                case 2:
                    cashWithdrawal(atmId);
                    break;
                case 3:
                    System.out.println("Выход");
                    break;
                default:
                    System.out.println("Введено неверное значение");
                    break;
            }
        }
    }

    private void cashAcceptance(Integer atmId) {
        List<Nominal> nominals = null;
        try {
            nominals = banknoteScanner.scanBanknote(nextString("Введите номиналы купюр в строку через пробел"));
        } catch (IncorectValue e) {
            log.error("Купюра не может быть считана", e);
        }
        if (nominals != null) {
            try {
                int sum = atmOperations.add(atmId, nominals);
                System.out.println("Внесено " + sum + " рублей");
                log.info("В банкомат внесены купюры {}", nominals);
            } catch (NoAvailableRequestCount e) {
                log.error("Ошибка внесения купюр", e);
            }
        }
        printATMBalance(atmId);
    }

    private void cashWithdrawal(Integer atmId) {
        int sum = Integer.parseInt(nextString("Введите сумму выдачи"));
        try {
            List<Nominal> nominals = atmOperations.get(atmId, sum);
            System.out.println("Выдано" + nominals);
            log.info("Выданы купюры {}", nominals);
        } catch (AmountCannotBeCollected e) {
            log.error("Ошибка выдачи", e);
        }
        printATMBalance(atmId);
    }

    private static String nextString(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    private static int nextInt(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        return i;
    }

    private void printATMBalance(Integer atmId) {
        ATM atm = atmSaving.download(atmId);
        List<CashPair> balance = cmo.balance(atm);
        System.out.println("Состояние банкомата: ");
        for ( CashPair pair : balance ) {
            System.out.println("  " + pair.getNominal() + " - " + pair.getCount());
        }
        log.info("Выведено состояние банкомата");
    }
}
