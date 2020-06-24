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
public class ATMStart {
    private final ATMSaving atmSaving;
    private final ATMOperations atmOperations;
    private final CashMashineOperations cmo;
    private final BanknoteScanner banknoteScanner;
    private ATM atm;

    public ATMStart(ATMSaving atmSaving, ATMOperations atmOperations, CashMashineOperations cmo, BanknoteScanner banknoteScanner) {
        this.atmSaving = atmSaving;
        this.atmOperations = atmOperations;
        this.cmo = cmo;
        this.banknoteScanner = banknoteScanner;
    }

    public void start(String path) {
        try {
            if (!path.isEmpty()) {
                atm = atmSaving.downloadFromFile(path);
                log.info("Состояние банкомата прочитано из файла");
            } else {
                atm = new ATM();
                log.info("Пустой банкомат инициализирован");
            }
        } catch (DownloadATMExeption e) {
            log.error("Ошибка чтения состояния банкомата {} ", e.getStackTrace());
        }
        printATMBalance(atm);

        int answer = 0;
        while (answer != 3) {
            answer = nextInt("Введите номер пункта меню: \n 1. Принять наличные \n 2. Выдать наличные \n 3. Выйти");
            switch (answer) {
                case 1:
                    cashAcceptance(atm);
                    break;
                case 2:
                    cashWithdrawal(atm);
                    break;
                case 3:
                    System.out.println("Выход");
                    break;
                default:
                    System.out.println("Введено неверное значение");
                    break;
            }
        }
        atmSaving.save(atm, "atm.json");
        log.info("Сохранено состояние банкомата {}", atm);
    }

    private void cashAcceptance(ATM atm) {
        List<Nominal> nominals = null;
        try {
            nominals = banknoteScanner.scanBanknote(nextString("Введите номиналы купюр в строку через пробел"));
        } catch (IncorectValue e) {
            log.error("Купюра не может быть считана", e);
        }
        if (nominals != null) {
            try {
                int sum = atmOperations.add(atm, nominals);
                System.out.println("Внесено " + sum + " рублей");
                log.info("В банкомат внесены купюры {}", nominals);
            } catch (NoAvailableRequestCount e) {
                log.error("Ошибка внесения купюр", e);
            }
        }
        printATMBalance(atm);
    }

    private void cashWithdrawal(ATM atm) {
        int sum = Integer.parseInt(nextString("Введите сумму выдачи"));
        try {
            List<Nominal> nominals = atmOperations.get(atm, sum);
            System.out.println("Выдано" + nominals);
            log.info("Выданы купюры {}", nominals);
        } catch (AmountCannotBeCollected e) {
            log.error("Ошибка выдачи", e);
        }
        printATMBalance(atm);
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

    private void printATMBalance(ATM atm) {
        List<CashPair> balance = cmo.balance(atm);
        System.out.println("Состояние банкомата: ");
        for ( CashPair pair : balance ) {
            System.out.println("  " + pair.getNominal() + " - " + pair.getCount());
        }
        log.info("Выведено состояние банкомата");
    }

    private void setAtm(ATM atm) {
        this.atm = atm;
    }
}
