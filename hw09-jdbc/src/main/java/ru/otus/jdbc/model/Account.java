package ru.otus.jdbc.model;

import ru.otus.jdbc.annotation.ID;

public class Account {
    @ID
    public String no;

    public String type;
    public double rest;

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
