package pl.coderslab.users;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderslab.entity.UserDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserDelete", urlPatterns = "/user/delete")
public class UserDelete extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserDelete.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            UserDao.delete(id);
            resp.sendRedirect("/user/list");
        } catch (SQLException e) {
            log.error("Database error", e);
            resp.sendError(500, "Database error");
        }
    }
}
