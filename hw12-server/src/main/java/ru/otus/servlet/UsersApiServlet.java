package ru.otus.servlet;

import com.google.gson.Gson;
import ru.otus.core.dao.UserDaoHibernate;
import ru.otus.model.User;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UsersApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final UserDaoHibernate userDao;
    private final Gson gson;

    public UsersApiServlet(UserDaoHibernate userDao, Gson gson) {
        this.userDao = userDao;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userDao.findById(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(user));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userLogin = request.getParameter("login");

        User user = userDao.findByLogin(userLogin).orElse(new User());

        user.setName(request.getParameter("name"));
        user.setLogin(userLogin);
        user.setPassword(request.getParameter("password"));
        user.setRole(request.getParameter("role"));

        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(userDao.insertOrUpdate(user)));

    }
    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
