package ru.selezneva;

import ru.selezneva.exceptions.UnsupportedOperationException;

import java.util.*;
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
        DIYarrayList<Integer> list1 = new DIYarrayList<>();
        list1.addAll(list);
        DIYarrayList<Integer> list2 = new DIYarrayList<>();
        list2.addAll(list);
        System.out.println("------------------------------------------------------------------------");
        DIYarrayList.sort(list1, Integer::compareTo);
        Collections.sort(list2, Integer::compareTo);
        System.out.println("Список, упорядоченный методом класса DIYarrayList: " + list1);
        System.out.println("Список, упорядоченный методом класса Collections: " + list2);

        //проверяем работу метода set
        System.out.println("------------------------------------------------------------------------");
        list.set(5, 1000);
        System.out.println("Исходный список, элемент с идексом пять изменен: " + list);
        //проверяем работу метода get
        System.out.println("Элемент с индексом пять: " + list.get(5));

        //проверяем работу метода addAll
        System.out.println("------------------------------------------------------------------------");
        List<Integer> src = Arrays.asList(1, 2, 3, 4, 5);
        list1.addAll(src);
        Collections.addAll(list2, 1, 2, 3, 4, 5);
        System.out.println("Добавлен список: " + src);
        System.out.println("Результирующий список после добавления элементов методом класса DIYarrayList: " + list1);
        System.out.println("Результирующий список после добавления элементов методом класса Collections: " + list2);

        //проверяем работу итератора
        System.out.println("------------------------------------------------------------------------");
        Iterator<Integer> iterator = list.iterator();
        System.out.println(iterator.hasNext());
        System.out.println(iterator.next());

        for (Integer integer: list) {
            System.out.print(integer + " ");
        }
        System.out.println();

        //проверяем работу метода clear
        System.out.println("------------------------------------------------------------------------");
        list.clear();
        System.out.println("Размер списка после очистки: " + list.size() + ". Очищенный список: " + list);
        System.out.println("Список пуст: " + list.isEmpty());

        //проверяем работу итератора на пустом списке
        if (list.iterator().hasNext()) {
            System.out.println(iterator.next());
        }
        for (Integer integer: list) {
            System.out.print(integer + " ");
        }

        //проверяем работу метода copy
        System.out.println("------------------------------------------------------------------------");
        src = Stream.iterate(0, n -> n + 11).limit(25).collect(Collectors.toList());
        System.out.println("Сгенерированный список: " + src);
        DIYarrayList.copy(list1, src);
        Collections.copy(list2, src);
        System.out.println("Список копия, сформированный методом класса DIYarrayList: " + list1);
        System.out.println("Список копия, сформированный методом класса Collections: " + list2);

        //проверяем работу неподдерживаемого метода
        System.out.println("------------------------------------------------------------------------");
        try {
            list.contains(new Object());
        } catch (UnsupportedOperationException e) {
            System.out.println("Метод не поддерживается");
        }
    }
}
