package ru.otus.jdbc.model;

import ru.otus.jdbc.annotation.ID;

public class Client {
    @ID
    public long id;

    public String name;
    public int age;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
