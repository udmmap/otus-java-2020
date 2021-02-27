package ru.otus;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

import static java.lang.Thread.sleep;

public class Actor implements Callable<Integer> {
    private final Counter counter;
    private int val;
    private final Blocker blocker;

    Actor(Blocker blocker, Counter counter, int initial){
        this.val = initial;
        this.counter = counter;
        this.blocker = blocker;
    }

    @Override
    public Integer call() throws InterruptedException {
        while(true){
            if (val != counter.getVal()){
                try {
                    ForkJoinPool.managedBlock(blocker);

                    int nextVal = counter.getVal() + counter.getDelta();
                    if (nextVal<1 || nextVal>10){
                        counter.reverseDelta();
                        nextVal = counter.getVal() + counter.getDelta();
                    }

                    val = nextVal;
                    counter.setVal(nextVal);

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                } finally {
                    blocker.unblock();
                }
                sleep(1000);

            }
        }
        return val;
    }
}
