package ru.otus.core.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectDaoHibernate<T> implements ObjectDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectDaoHibernate.class);

    protected final Class<T> clazz;
    private final List<Field> idFields;
    private final List<Field> nIdFields;

    protected final SessionManagerHibernate sessionManager;

    private Object getId(T obj) throws IllegalAccessException {
        Field idF = idFields.get(0);
        Boolean canAcc = idF.canAccess(obj);
        if (!canAcc) idF.setAccessible(true);
        Object id = idF.get(obj);
        if (!canAcc) idF.setAccessible(false);
        return id;
    }

    public ObjectDaoHibernate(SessionManagerHibernate sessionManager, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.clazz = clazz;
        idFields = Arrays.stream(clazz.getDeclaredFields()).filter(f->f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
        nIdFields = Arrays.stream(clazz.getDeclaredFields()).filter(f->!f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(Object id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(clazz, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            var session = currentSession.getHibernateSession();
            var criteria = session.getCriteriaBuilder().createQuery(clazz);
            criteria.from(clazz);
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<T>();
    }

    @Override
    public Optional<T> findRandomUser() {
        Random r = new Random();
        List<T> all = findAll();
        return all.size()>0 ? Optional.of(all.get(r.nextInt(all.size()))) : Optional.empty();
    }



    @Override
    public Object insert(T obj) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(obj);
            hibernateSession.flush();
            return this.getId(obj);
        } catch (Exception e) {
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public void update(T obj) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(obj);
        } catch (Exception e) {
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
            return this.getId(obj);
        } catch (Exception e) {
            throw new ObjectDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
