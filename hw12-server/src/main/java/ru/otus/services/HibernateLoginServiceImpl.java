package ru.otus.services;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.util.security.Password;
import ru.otus.core.dao.UserDaoHibernate;
import ru.otus.model.User;

import java.util.Optional;

public class HibernateLoginServiceImpl extends AbstractLoginService {

    private final UserDaoHibernate userDao;

    public HibernateLoginServiceImpl(UserDaoHibernate userDao) {
        this.userDao = userDao;
    }

    @Override
    protected String[] loadRoleInfo(UserPrincipal userPrincipal) {
        Optional<User> dbUser = userDao.findByLogin(userPrincipal.getName());
        var res = new String[]{dbUser.map(usr->usr.getRole()).orElse("")};
        return res;
    }

    @Override
    protected UserPrincipal loadUserInfo(String login) {
        System.out.println(String.format("UserLoginService#loadUserInfo(%s)", login));
        Optional<User> dbUser = userDao.findByLogin(login);
        return dbUser.map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword()))).orElse(null);
    }
}
