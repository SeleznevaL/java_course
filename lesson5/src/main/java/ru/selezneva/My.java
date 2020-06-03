package ru.selezneva;

import java.time.LocalDateTime;
import java.util.*;

public class My implements MyMBean {
    private int count;
    public void run() throws InterruptedException {
        List<Date> list = new LinkedList<>();
        List<Integer> integers = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        List<Date> dates = new LinkedList<>();
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Память общая до " + runtime.totalMemory() + "  " + LocalDateTime.now());
        System.out.println("Память свободная до " + runtime.freeMemory() + "  " + LocalDateTime.now());
        for (int i = 0; i < count; i++) {
            Date date = new Date();
            list.add(date);
            integers.add(i);
            strings.add(" " + i);
            dates.add(new Date());
            if (i%3 == 0) {
                list.remove(date);
            }
            if (i%10_000 == 0) {
                System.out.println("Память общая при i=" + i + " " + runtime.totalMemory() + "  " + LocalDateTime.now());
                System.out.println("Память свободная при i=" + i + " " + runtime.freeMemory() + "  " + LocalDateTime.now());
            }
        }
        System.out.println("Память общая после " + runtime.totalMemory() + "  " + LocalDateTime.now());
        System.out.println("Память свободная после " + runtime.freeMemory() + "  " + LocalDateTime.now());
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }
}
