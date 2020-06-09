package ru.selezneva;

import lombok.extern.slf4j.Slf4j;
import ru.selezneva.atm.ATM;
import ru.selezneva.atm.CashMashine;
import ru.selezneva.atm.dto.CashPair;
import ru.selezneva.atm.exceptions.AmountCannotBeCollected;
import ru.selezneva.atm.exceptions.DownloadATMExeption;
import ru.selezneva.atm.exceptions.IncorectValue;
import ru.selezneva.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.atm.impl.ATMImpl;
import ru.selezneva.atm.ref.Nominal;
import ru.selezneva.atm.utils.ATMSaving;
import ru.selezneva.atm.utils.BanknoteScanner;
import ru.selezneva.atm.utils.impl.ATMSavingImpl;
import ru.selezneva.atm.utils.impl.BanknoteScannerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class App {
    public static void main( String[] args ) {
        ATMSaving atmSaving = new ATMSavingImpl();
        ATMImpl atm = null;
        try {
            if (args.length > 0) {
                atm = atmSaving.downloadFromFile(args[0]);
                log.info("Состояние банкомата прочитано из файла");
            }
        } catch (DownloadATMExeption e) {
            log.error("Ошибка чтения состояния банкомата {} ", e.getStackTrace());
        }

        if ( atm == null ) {
            atm = new ATMImpl();
            log.info("Пустой банкомат инициализирован");
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

    private static void cashAcceptance(ATMImpl atm) {
        BanknoteScanner banknoteScanner = new BanknoteScannerImpl();
        List<Nominal> nominals = null;
        try {
            nominals = banknoteScanner.scanBanknote(nextString("Введите номиналы купюр в строку через пробел"));
        } catch (IncorectValue e) {
            log.error("Купюра не может быть считана", e);
        }
        if (nominals != null) {
            try {
                int sum = atm.add(nominals);
                System.out.println("Внесено " + sum + " рублей");
                log.info("В банкомат внесены купюры {}", nominals);
            } catch (NoAvailableRequestCount e) {
                log.error("Ошибка внесения купюр", e);
            }
        }
        printATMBalance(atm);
    }

    private static void cashWithdrawal(ATMImpl atm) {
        int sum = Integer.parseInt(nextString("Введите сумму выдачи"));
        try {
            List<Nominal> nominals = atm.get(sum);
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

    private static void printATMBalance(CashMashine atm) {
        List<CashPair> balance = atm.balance();
        System.out.println("Состояние банкомата: ");
        for ( CashPair pair : balance ) {
            System.out.println("  " + pair.getNominal() + " - " + pair.getCount());
        }
        log.info("Выведено состояние банкомата");
    }
}
