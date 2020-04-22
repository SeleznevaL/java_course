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
        System.out.println("Запущен метод before");
    }

    @After
    void after() {
        System.out.println("Запущен метод after");
    }

    @Test
    void test_1() throws Exception {
        System.out.println("Запущен метод test_1");
    }

    @Test
    void test_2() throws Exception {
        System.out.println("Запущен метод test_2");
        throw new Exception();
    }

    @Test
    void test_3() throws Exception {
        System.out.println("Запущен метод test_3");
    }
}
