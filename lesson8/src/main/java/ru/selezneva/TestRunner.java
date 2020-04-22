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
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> passTestMethod = new ArrayList<>();
        List<Method> failTestMethod = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(className);
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.getAnnotation(Before.class) != null) {
                    beforeMethods.add(method);
                }
                if (method.getAnnotation(After.class) != null) {
                    afterMethods.add(method);
                }
                if (method.getAnnotation(Test.class) != null) {
                    testMethods.add(method);
                }
            }
            Constructor<?> constructor = clazz.getConstructor();
            for (Method testMethod : testMethods) {
                constructor.setAccessible(true);
                Object testObject = constructor.newInstance();
                for (Method beforeMethod: beforeMethods) {
                    beforeMethod.setAccessible(true);
                    beforeMethod.invoke(testObject);
                }
                for (Method afterMethod: afterMethods) {
                    afterMethod.setAccessible(true);
                    afterMethod.invoke(testObject);
                }
                testMethod.setAccessible(true);
                try {
                    testMethod.invoke(testObject);
                    passTestMethod.add(testMethod);
                } catch (Exception e) {
                    failTestMethod.add(testMethod);
                }
            }
            printTestResults(passTestMethod, failTestMethod);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Класс не найден!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    static private void printTestResults(List<Method> passTestMethods, List<Method> failTestMethod) {
        System.out.println("Запущено тестов: " + (passTestMethods.size() + failTestMethod.size()));
        System.out.println("Пройдено тестов: " + passTestMethods.size());
        System.out.println("Не пройдено тестов: " + failTestMethod.size());
    }
}
