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

@WebServlet (name = "UserList", urlPatterns = {"", "/user/list"})
public class UserList extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserList.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            User[] users = UserDao.findAll();
            req.setAttribute("users", users);

            getServletContext().getRequestDispatcher("/WEB-INF/users/list.jsp")
                    .forward(req, resp);
        } catch (SQLException e) {
            log.error("Database error", e);
            resp.sendError(500, "Database error");
        }


    }
}
