package ru.otus;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.ObjectDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.AddressData;
import ru.otus.model.PhoneData;
import ru.otus.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@DisplayName("Проверено, что")
public class HibernateCacheTest {
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;

    @Mock
    private MyCache<Optional,User> cacheMock;

    @BeforeEach
    void setUp(){
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        sessionFactory = HibernateUtils.buildSessionFactory(
                configuration
                , User.class, AddressData.class, PhoneData.class
        );
        sessionManager = new SessionManagerHibernate(sessionFactory);
        sessionManager.beginSession();
    }

    @AfterEach
    void tearDown(){
        sessionManager.close();
        sessionFactory.close();
    }

    @DisplayName("DAO обращается к методам кеша при записи и чтении объектов")
    @Test
    void cacheTest(){
        cacheMock = mock(MyCache.class);

        var userDaoHibernate = new ObjectDaoHibernate<User>(sessionManager, cacheMock, User.class);

        var user = new User();

        user.setName("User");
        user.setAge(55);
        user.setAddressDataSet(new AddressData("Street 1"));
        user.setPhoneDataSet(new ArrayList(List.of(
                new PhoneData("000", user)
                ,new PhoneData("111", user)
        )));

        var id = userDaoHibernate.insertOrUpdate(user);
        Mockito.verify(cacheMock).put(Optional.of(user.getId()),user);
        var userOptional = userDaoHibernate.findById(id);
        Mockito.verify(cacheMock).get(Optional.of(user.getId()));

    }

    @DisplayName("Кеш очищается при вызове GC")
    @Test
    void cacheCleanTest(){
        MyCache<Optional,User> cache = new MyCache<Optional,User>();
        var userDaoHibernate = new ObjectDaoHibernate<User>(sessionManager, cache, User.class);

        Integer cntClean = 0;
        Integer cacheSize = 0;

        Integer i = 100;

        while (0 < i--){
            var user = new User();

            user.setName("User");
            user.setAge(55);
            user.setAddressDataSet(new AddressData("Street 1"));
            user.setPhoneDataSet(new ArrayList(List.of(
                    new PhoneData("000", user)
                    ,new PhoneData("111", user)
            )));

            var id = userDaoHibernate.insert(user);

            if (cacheSize >= cache.getSize()) {
                cntClean++;
            }
            cacheSize = cache.getSize();


            if (i%10 == 0){
                System.gc();
            }

        }
        Assert.assertFalse(cntClean == 0);
    }

}
