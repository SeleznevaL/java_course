package ru.selezneva.test;

import ru.selezneva.annotations.After;
import ru.selezneva.annotations.Before;
import ru.selezneva.annotations.Test;

public class TestClass_1 {
    private static int COUNT = 0;

    public TestClass_1() {
        System.out.println("Создан объект класса TestClass_1, счетчик " + ++COUNT);
    }

    @Before
    void before() {
        System.out.println("Запущен метод before объкта класса TestClass_1 со счетчиком " + COUNT);
    }

    @After
    void after() {
        System.out.println("Запущен метод after объкта класса TestClass_1 со счетчиком " + COUNT);
    }

    @Test
    void test_1() throws Exception {
        System.out.println("Запущен метод test_1 объкта класса TestClass_1 со счетчиком " + COUNT);
    }

    @Test
    void test_2() throws Exception {
        System.out.println("Запущен метод test_2 объкта класса TestClass_1 со счетчиком " + COUNT);
        throw new Exception();
    }

    @Test
    void test_3() throws Exception {
        System.out.println("Запущен метод test_3 объкта класса TestClass_1 со счетчиком " + COUNT);
    }
}
