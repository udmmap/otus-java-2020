package ru.otus;

import ru.otus.framework.Tester;

public class HelloOtus {
    public static void main(String... args) {
        try {
            System.out.println(Tester.run("ru.otus.Example"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}