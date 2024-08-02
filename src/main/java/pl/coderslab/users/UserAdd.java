package pl.coderslab.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserAdd", urlPatterns = "/user/add")
public class UserAdd extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserAdd.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/users/add.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User newUser = new User();

        newUser
                .setUserName(username)
                .setEmail(email)
                .setPassword(password);

        try {
            UserDao.create(newUser);
            resp.sendRedirect("/user/list");
        } catch (SQLException e) {
            log.error("Database error", e);
            resp.sendError(500, "Database error");
        }
    }
}
