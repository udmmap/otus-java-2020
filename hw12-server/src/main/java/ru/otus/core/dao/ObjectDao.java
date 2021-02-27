package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.model.User;

import java.util.List;
import java.util.Optional;


public interface ObjectDao<T> {
    Optional<T> findById(Object id);

    List<T> findAll();

    Optional<T> findRandomUser();

    Object insert(T obj);

    void update(T obj);

    Object insertOrUpdate(T obj);

    SessionManager getSessionManager();
}
