package ru.otus;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

public class Blocker implements ForkJoinPool.ManagedBlocker {
    private ReentrantLock lock;

    Blocker(ReentrantLock lock){
        this.lock = lock;
    }

    @Override
    public boolean block() throws InterruptedException {
        lock.lock();
        return true;
    }

    @Override
    public boolean isReleasable() {
        return false;
    }

    public void unblock(){
        lock.unlock();
    }
}
