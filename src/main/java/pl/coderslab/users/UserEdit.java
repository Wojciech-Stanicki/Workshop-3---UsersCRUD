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

@WebServlet(name = "UserEdit", urlPatterns = "/user/edit")
public class UserEdit extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserList.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            User userToEdit = UserDao.read(id);
            req.setAttribute("userToEdit", userToEdit);

            getServletContext().getRequestDispatcher("/WEB-INF/users/edit.jsp")
                    .forward(req, resp);
        } catch (SQLException e) {
            log.error("Database error", e);
            resp.sendError(500, "Database error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User editedUser = new User();

        editedUser
                .setId(id)
                .setUserName(username)
                .setEmail(email)
                .setPassword(password);

        try {
            UserDao.update(editedUser);
            resp.sendRedirect("/user/list");
        } catch (SQLException e) {
            log.error("Database error", e);
            resp.sendError(500, "Database error");
        }
    }

}
