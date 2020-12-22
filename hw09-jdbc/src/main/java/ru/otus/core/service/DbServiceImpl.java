package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.model.Client;

import java.util.Optional;

public class DbServiceImpl implements DBService {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);

    private final JdbcMapper jdbcMapper;

    public DbServiceImpl(JdbcMapper jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Object saveObject(Object object) {
        try (var sessionManager = jdbcMapper.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var objectId = jdbcMapper.insert(object);
                sessionManager.commitSession();

                logger.info("created client: {}", objectId);
                return objectId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Object> getObject(Object id, Class clazz) {
        try (var sessionManager = jdbcMapper.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Object> clientOptional = jdbcMapper.findById(id, clazz);

                logger.info("client: {}", clientOptional.orElse(null));
                sessionManager.commitSession();
                return clientOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
