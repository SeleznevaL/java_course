package ru.selezneva;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class My implements MyMBean {
    private int count;
    public void run() throws InterruptedException {
        Queue<String> queue = new LinkedList<>();
        while (true) {
            for (int i = 0; i < count; i++) {
                queue.add(new String(new char[1]));
                if (i % 2 == 0) {
                    queue.remove();
                }
//                Thread.sleep(5000);
            }
            count *= 2;
        }
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
