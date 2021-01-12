package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;


public interface ObjectDao<T> {
    Optional<T> findById(Object id);
    //List<User> findAll();

    Object insert(T obj);

    void update(T obj);

    Object insertOrUpdate(T obj);

    SessionManager getSessionManager();
}
