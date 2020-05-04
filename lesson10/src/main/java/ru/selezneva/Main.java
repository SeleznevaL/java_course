package ru.selezneva;

import ru.selezneva.banknotes.Banknote;
import ru.selezneva.banknotes.FiveHundredRubles;
import ru.selezneva.banknotes.OneHundredRubles;
import ru.selezneva.banknotes.TwoHundredRubles;
import ru.selezneva.exceptions.AmountCannotBeIssuedException;
import ru.selezneva.exceptions.CellOverflowExceptionException;
import ru.selezneva.impl.ATM01;
import ru.selezneva.impl.ATMServiceImpl;
import ru.selezneva.impl.ATMUseImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //инициализируем банкомат и интерфейс обслуживания
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(OneHundredRubles.getInstance());
        banknotes.add(TwoHundredRubles.getInstance());
        banknotes.add(FiveHundredRubles.getInstance());
        ATMService service = new ATMServiceImpl(ATM01.initializeATM(banknotes));
        System.out.println("Вместимость каждой ячейки: " + service.getCellCapacity());
        System.out.println("Свободные места для купюр в ячейках: " + service.getEmptyPlacesInCells());
        //заполняем банкомат до полна
        service.fillATM(service.getEmptyPlacesInCells());
        System.out.println("Всего наличных в банкомате: " + service.getTotalCash());

        //инициализируем интерфейс пользователя банкомата
        ATMUse use = new ATMUseImpl(ATM01.initializeATM(banknotes));
        System.out.println("Доступные номиналы купюр: " + use.availableBanknoteDenominations());

        //извлекаем купюры из банкомата в режиме обслуживания
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(OneHundredRubles.getInstance(), 33300);
        map.put(TwoHundredRubles.getInstance(), 33300);
        map.put(FiveHundredRubles.getInstance(), 33300);
        service.removeBanknotesFromATM(map);
        System.out.println("Всего наличных в банкомате: " + service.getTotalCash());

        //снимаем деньги в банкомате
        System.out.println(use.getMoney(25000));
        System.out.println("Всего наличных в банкомате: " + service.getTotalCash());
        System.out.println("Доступные номиналы купюр: " + use.availableBanknoteDenominations());

        try {
            use.getMoney(25000);
        } catch (AmountCannotBeIssuedException e) {
            System.out.println(e.getClass().getSimpleName());
        }

        //кладем деньги в банкомат
        map.clear();
        map.put(OneHundredRubles.getInstance(), 5);
        map.put(TwoHundredRubles.getInstance(), 1);
        map.put(FiveHundredRubles.getInstance(), 8);
        System.out.println("Внесено наличных: " + use.putMoney(map));
        System.out.println("Всего наличных в банкомате: " + service.getTotalCash());

        //добавляем купюры в банкомата в режиме обслуживания
        System.out.println("Свободные ячейки: " + service.getEmptyPlacesInCells());
        map = service.getEmptyPlacesInCells();
        for (Banknote banknote : map.keySet()) {
            Integer temp = map.get(banknote) - 2;
            map.put(banknote, temp);
        }
        service.fillATM(map);
        System.out.println("Свободные ячейки после заполнения: " + service.getEmptyPlacesInCells());
        System.out.println("Всего наличных в банкомате: " + service.getTotalCash());

        //кладем деньги в банкомат, сумма больше доступной вместимости
        map.clear();
        map.put(OneHundredRubles.getInstance(), 5);
        map.put(TwoHundredRubles.getInstance(), 1);
        map.put(FiveHundredRubles.getInstance(), 8);
        try {
            System.out.println("Внесено наличных: " + use.putMoney(map));
        } catch (CellOverflowExceptionException e) {
            System.out.println(e.getClass().getSimpleName());
        }

    }
}