package ru.selezneva;

import ru.selezneva.exceptions.UnsupportedOperationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.random;

public class MainClass {
    public static void main(String[] args) {
        DIYarrayList<Integer> list = new DIYarrayList<>();
        for (int i = 0; i < 36; i++) {
            //проверяем работу метода add
            list.add((int) (random() * 100));
        }

        //проверяем работу метода toString
        System.out.println("Исходный список: " + list);
        System.out.println("------------------------------------------------------------------------");
        //проверяем работу метода isEmpty
        System.out.println("Список пуст: " + list.isEmpty());

        //проверяем работу метода sort
        System.out.println("------------------------------------------------------------------------");
        DIYarrayList.sort(list, Integer::compareTo);
        System.out.println("Упорядоченный список: " + list);

        //проверяем работу метода set
        System.out.println("------------------------------------------------------------------------");
        list.set(5, 1000);
        System.out.println("Исходный список, элемент с идексом пять изменен: " + list);
        //проверяем работу метода get
        System.out.println("Элемент с индексом пять: " + list.get(5));

        //проверяем работу метода addAll
        System.out.println("------------------------------------------------------------------------");
        List<Integer> src = Arrays.asList(1, 2, 3, 4, 5);
        list.addAll(src);
        System.out.println("Добавлен список: " + src);
        System.out.println("Результирующий список: " + list);

        //проверяем работу метода clear
        System.out.println("------------------------------------------------------------------------");
        list.clear();
        System.out.println("Размер списка после очистки: " + list.size() + ". Очищенный список: " + list);
        System.out.println("Список пуст: " + list.isEmpty());

        //проверяем работу метода copy
        System.out.println("------------------------------------------------------------------------");
        src = Stream.iterate(0, n -> n + 11).limit(25).collect(Collectors.toList());
        System.out.println("Сгенерированный список: " + src);
        List<Integer> dest = new ArrayList<>();
        dest.add(3);
        dest.add(8);
        DIYarrayList.copy(dest, src);
        System.out.println("Список копия: " + dest);

        //проверяем работу неподдерживаемого метода
        System.out.println("------------------------------------------------------------------------");
        try {
            list.iterator();
        } catch (UnsupportedOperationException e) {
            System.out.println("Метод не поддерживается!");
        }
    }
}
