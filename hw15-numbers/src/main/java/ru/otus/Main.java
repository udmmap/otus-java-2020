package ru.otus;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String... args) {
        Blocker blocker = new Blocker(new ReentrantLock());
        Counter counter = new Counter(0);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new Actor(blocker, counter, -1));
        try {
            pool.submit(new Actor(blocker, counter, 0)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}