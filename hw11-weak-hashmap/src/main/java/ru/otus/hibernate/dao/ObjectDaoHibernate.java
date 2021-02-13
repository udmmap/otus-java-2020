package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.dao.ObjectDao;
import ru.otus.core.dao.ObjectDaoException;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ObjectDaoHibernate<T> implements ObjectDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectDaoHibernate.class);

    private final Class<T> clazz;
    private final List<Field> idFields;
    private final List<Field> nIdFields;

    private final HwCache<Optional,T> cache;

    private final SessionManagerHibernate sessionManager;

    private Object getId(T obj) throws IllegalAccessException {
        Field idF = idFields.get(0);
        Boolean canAcc = idF.canAccess(obj);
        if (!canAcc) idF.setAccessible(true);
        Object id = idF.get(obj);
        if (!canAcc) idF.setAccessible(false);
        return id;
    }

    public ObjectDaoHibernate(SessionManagerHibernate sessionManager, HwCache<Optional,T> cch, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.cache = cch;
        this.clazz = clazz;
        idFields = Arrays.stream(clazz.getDeclaredFields()).filter(f->f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
        nIdFields = Arrays.stream(clazz.getDeclaredFields()).filter(f->!f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(Object id) {
        T cchObject = this.cache.get(Optional.of(id));
        if (cchObject!=null) {
            return Optional.of(cchObject);
        } else {

            DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
            try {
                return Optional.ofNullable(currentSession.getHibernateSession().find(clazz, id));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Object insert(T obj) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(obj);
            hibernateSession.flush();

            this.cache.put(Optional.of(this.getId(obj)), obj);

            return this.getId(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public void update(T obj) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(obj);

            this.cache.put(Optional.of(this.getId(obj)), obj);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public Object insertOrUpdate(T obj) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (this.getId(obj)!=null) {
                hibernateSession.merge(obj);
            } else {
                hibernateSession.persist(obj);
                hibernateSession.flush();
            }

            this.cache.put(Optional.of(this.getId(obj)), obj);

            return this.getId(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
