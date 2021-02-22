package ru.otus.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.User;

import java.util.Optional;

public class UserDaoHibernate extends ObjectDaoHibernate<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }

    public Optional<User> findByLogin(String login) {
        var currentSession = sessionManager.getCurrentSession();
        try {
            var session = currentSession.getHibernateSession();
            var query = session.createQuery("from User where login = :login");
            query.setParameter("login", login);
            var res = query.list();
            return res.stream().findAny();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }


}
