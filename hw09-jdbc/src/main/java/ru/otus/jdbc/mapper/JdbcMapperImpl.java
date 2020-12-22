package ru.otus.jdbc.mapper;

import ru.otus.core.sessionmanager.DatabaseSession;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.DatabaseSessionJdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JdbcMapperImpl<T> implements JdbcMapper{
    private final SessionManager sMan;
    private final DbExecutor dbE;

    public JdbcMapperImpl(SessionManager sMan, DbExecutor dbE){
        this.sMan = sMan;
        this.dbE = dbE;
    }

    @Override
    public Object insert(Object objectData) {
        Object res = null;
        var entityClassMetaData = new EntityClassMetaDataImpl(objectData.getClass());
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        List<Field> af = entityClassMetaData.getAllFields();
        List<Object> params = af.stream().map(f-> {
            try {
                return f.get(objectData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        try {
            res = dbE.executeInsert(sMan.getCurrentSession().getConnection()
                    , entitySQLMetaData.getInsertSql()
                    , params);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return res;
    }

    @Override
    public void update(Object objectData) {

    }

    @Override
    public void insertOrUpdate(Object objectData) {

    }

    @Override
    public Optional<Object> findById(Object id, Class clazz) {
        Optional<Object> res = null;
        var entityClassMetaData = new EntityClassMetaDataImpl(clazz);
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);

        try {
            res = dbE.executeSelect(sMan.getCurrentSession().getConnection()
                    , entitySQLMetaData.getSelectByIdSql(), id, rs -> {
                        try {
                            final Object obj = entityClassMetaData.getConstructor().newInstance();
                            List<Field> af = entityClassMetaData.getAllFields();
                            ((ResultSet)rs).next();
                            IntStream.range(0, af.size())
                                    .forEach(i-> {
                                        try {
                                            af.get(i).set(obj, ((ResultSet)rs).getObject(i+1));
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    });

                            return obj;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
        return res;
    }

    @Override
    public SessionManager getSessionManager() {
        return this.sMan;
    }
}
