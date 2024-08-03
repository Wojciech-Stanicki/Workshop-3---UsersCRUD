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

@WebServlet(name = "UserShow", urlPatterns = "/user/show")
public class UserShow extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserShow.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            User userToShow = UserDao.read(id);
            req.setAttribute("userToShow", userToShow);

            getServletContext().getRequestDispatcher("/WEB-INF/users/show.jsp")
                    .forward(req, resp);
        } catch (SQLException e) {
            log.error("Database error", e);
            resp.sendError(500, "Database error");
        }
    }
}
