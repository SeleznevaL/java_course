package ru.selezneva;

import ru.selezneva.annotations.After;
import ru.selezneva.annotations.Before;
import ru.selezneva.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void runTestClass(String className) {
        MethodsForTest methodsForTest;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
            methodsForTest = getMethodsForTest(clazz);
            printTestResults(startTests(clazz.getConstructor(), methodsForTest));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Класс не найден");
        }
    }

    private static TestsResults startTests(Constructor<?> constructor, MethodsForTest methodsForTest) {
        TestsResults testsResults = new TestsResults();
        for (Method method : methodsForTest.test) {
            try {
                constructor.setAccessible(true);
                Object instance = constructor.newInstance();
                for (Method before : methodsForTest.getBefore()) {
                    before.setAccessible(true);
                    before.invoke(instance);
                }
                method.setAccessible(true);
                try {
                    method.invoke(instance);
                    testsResults.getPassed().add(method);
                } catch (Exception e) {
                    testsResults.getFailed().add(method);
                }
                for (Method after : methodsForTest.getAfter()) {
                    after.setAccessible(true);
                    after.invoke(instance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        testsResults.setClazz(constructor.getDeclaringClass());
        return testsResults;
    }

    private static MethodsForTest getMethodsForTest(Class clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        MethodsForTest methodsForTest = new MethodsForTest();
        for (Method method : declaredMethods) {
            if (method.getAnnotation(Before.class) != null) {
                methodsForTest.getBefore().add(method);
            }
            if (method.getAnnotation(After.class) != null) {
                methodsForTest.getAfter().add(method);
            }
            if (method.getAnnotation(Test.class) != null) {
                methodsForTest.getTest().add(method);
            }
        }
        return methodsForTest;
    }

    private static  void printTestResults(TestsResults testsResults) {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("Класс " + testsResults.getClazz().getSimpleName() + ". Запущено тестов: " + (testsResults.getPassed().size() + testsResults.getFailed().size()));
        System.out.println("--------------------------------------------");
        System.out.println("Пройдено тестов: " + testsResults.getPassed().size());
        for (Method method : testsResults.getPassed()) {
            System.out.println(method.getName());
        }
        System.out.println("--------------------------------------------");
        System.out.println("Не пройдено тестов: " + testsResults.getFailed().size());
        for (Method method : testsResults.getFailed()) {
            System.out.println(method.getName());
        }
        System.out.println("--------------------------------------------");
        System.out.println();
    }

    private static class MethodsForTest {
        private List<Method> after = new ArrayList<>();
        private List<Method> before = new ArrayList<>();
        private List<Method> test = new ArrayList<>();

        public List<Method> getAfter() {
            return after;
        }

        public List<Method> getBefore() {
            return before;
        }

        public List<Method> getTest() {
            return test;
        }
    }

    private static class TestsResults {
        private List<Method> passed = new ArrayList<>();
        private List<Method> failed = new ArrayList<>();
        private Class clazz;

        public List<Method> getPassed() {
            return passed;
        }

        public List<Method> getFailed() {
            return failed;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public Class getClazz() {
            return clazz;
        }
    }
}
