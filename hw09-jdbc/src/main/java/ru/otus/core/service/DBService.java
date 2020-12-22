package ru.otus.core.service;

import ru.otus.jdbc.model.Client;

import java.util.Optional;

public interface DBService {

    Object saveObject(Object object);

    Optional<Object> getObject(Object id, Class clazz);

    //List<Client> findAll();
}
