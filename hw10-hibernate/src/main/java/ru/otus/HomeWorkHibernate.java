package ru.otus;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.ObjectDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.AddressDataSet;
import ru.otus.model.PhoneDataSet;
import ru.otus.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class HomeWorkHibernate {
    private static final Logger logger = LoggerFactory.getLogger(HomeWorkHibernate.class);
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        try (var sessionFactory = HibernateUtils.buildSessionFactory(
                configuration
                , User.class, AddressDataSet.class, PhoneDataSet.class
        )) {

            try (var sessionManagerHibernate = new SessionManagerHibernate(sessionFactory)) {
                sessionManagerHibernate.beginSession();

                var userDaoHibernate = new ObjectDaoHibernate(sessionManagerHibernate, User.class);
                var user = new User();

                user.setName("User");
                user.setAge(55);
                user.setAddressDataSet(new AddressDataSet("Street 1"));
                user.setPhoneDataSet(new ArrayList(List.of(
                        new PhoneDataSet("000", user)
                        ,new PhoneDataSet("111", user)
                )));

                var id = userDaoHibernate.insertOrUpdate(user);
                Optional<Object> userOptional = userDaoHibernate.findById(id);

                userOptional.ifPresentOrElse(
                        usr -> logger.info("created user: {}", ((User) usr).toString()),
                        () -> logger.info("user was not created")
                );

            }
        }
    }

}
