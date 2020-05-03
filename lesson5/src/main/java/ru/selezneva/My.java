package ru.selezneva;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class My implements MyMBean {
    private int count;
    private Queue<Integer> queue = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() throws InterruptedException {
        while (true) {
            for (int i = 0; i < count; i++) {
                queue.add(i);
                if (i % 2 == 0) {
                    queue.remove();
                }
                Thread.sleep(5000);
                count *= 2;
            }
        }
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
