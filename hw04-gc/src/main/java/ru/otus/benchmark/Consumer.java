package ru.otus.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Consumer implements ConsumerMBean {
    private List<String> lsBuffer = new ArrayList<String>();
    public void run()  throws InterruptedException {
        int minSz = 1024; // Минимальный размер строки
        int maxDif = 4096; // Максимальное приращение
        String stT = "0123456";
        Random rnd = new Random();
        while (true) {
            // Добавляем в массив 2 строки со случайной длиной
            lsBuffer.add(stT.repeat(minSz + rnd.nextInt(maxDif)));
            lsBuffer.add(stT.repeat(minSz + rnd.nextInt(maxDif)));
            int rIdx0 = rnd.nextInt(lsBuffer.size());
            int rIdx1 = rnd.nextInt(lsBuffer.size());
            // Один элемент массива переставляем
            lsBuffer.set(rIdx0, lsBuffer.get(rIdx1));
            // Один элемент массива удаляем
            lsBuffer.remove(rIdx1);
            Thread.sleep(5);
        }
    }
}
