package ru.otus.core.dao;

public class ObjectDaoException extends RuntimeException {
    public ObjectDaoException(Exception ex) {
        super(ex);
    }
}
