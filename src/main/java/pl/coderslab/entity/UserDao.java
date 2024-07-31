package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.NonExistentRecordException;

import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";

    public static User create(User user) throws SQLException {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stmt = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmail());
            if (Objects.isNull(user.getPassword())) {
                throw new SQLIntegrityConstraintViolationException("Null password.");
            }
            stmt.setString(3, hashPassword(user.getPassword()));
            stmt.executeUpdate();
            log.info("User record successfully created.");

            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                try {
                    user.setId(resultSet.getInt(1));
                    User updatedUser = readOrError(user.getId());
                    user.setUserName(updatedUser.getUserName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                } catch (NonExistentRecordException e) {
                    log.error("Unable to read record that should exist. Something might have prevented its creation.", e);
                    return null;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            log.warn("Database constraint violation. User creation failure, entity was not created: {}", e.getMessage());
            return null;
        }
        return user;
    }

    public static User read(int userId) throws SQLException {
        User resultUser = new User();

        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stmt = conn.prepareStatement(READ_USER_QUERY);
            stmt.setInt(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                resultUser.setId(resultSet.getInt("id"));
                resultUser.setEmail(resultSet.getString("email"));
                resultUser.setUserName(resultSet.getString("username"));
                resultUser.setPassword(resultSet.getString("password"));
            } else {
                return null;
            }
        }
        return resultUser;
    }

    public static void update(User newUser) throws SQLException {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stmt = conn.prepareStatement(UPDATE_USER_QUERY);
            stmt.setString(1, newUser.getUserName());
            stmt.setString(2, newUser.getEmail());

            User currentUser = readOrError(newUser.getId());
            String currentUserPassword = currentUser.getPassword();
            if (Objects.isNull(newUser.getPassword())) {
                throw new SQLIntegrityConstraintViolationException("Null password.");
            }
            String newUserPassword = newUser.getPassword();

            if (!currentUserPassword.equals(newUserPassword) && (!BCrypt.checkpw(newUserPassword, currentUserPassword))) {
                stmt.setString(3, hashPassword(newUserPassword));
            } else {
                stmt.setString(3, currentUserPassword);
            }
            stmt.setInt(4, newUser.getId());

            if (stmt.executeUpdate() == 1) {
                log.info("User record successfully updated.");
                try {
                    User updatedUser = readOrError(newUser.getId());
                    newUser.setUserName(updatedUser.getUserName());
                    newUser.setEmail(updatedUser.getEmail());
                    newUser.setPassword(updatedUser.getPassword());
                } catch (NonExistentRecordException e) {
                    log.error("Unable to read record that should exist. Update of the User object has failed.", e);
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            log.warn("Database constraint violation. Update failure, entity remains unchanged: {}", e.getMessage());
        } catch (NonExistentRecordException e) {
            log.warn("Unable to read record. User update stopped.");
        }
    }

    public static void delete(int userId) throws SQLException {
        try (Connection conn = DbUtil.connect()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement stmt = conn.prepareStatement(DELETE_USER_QUERY);
                stmt.setInt(1, userId);
                if (stmt.executeUpdate() == 1) {
                    conn.commit();
                    log.info("User record successfully deleted.");
                } else {
                    throw new NonExistentRecordException("Specified record does not exist.");
                }
            } catch (NonExistentRecordException e) {
                conn.rollback();
                log.warn("Unable to delete record. Nothing has been deleted: {}", e.getMessage());
            }
        }
    }

    public static User[] findAll() throws SQLException {
        User[] resultUsers = new User[0];

        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stmt = conn.prepareStatement(READ_ALL_USERS_QUERY);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                resultUsers = addToArray(user, resultUsers);
            }
        }
        return resultUsers;
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private static User[] addToArray(User userToAdd, User[] currentUsers) {
        User[] resultUsers = Arrays.copyOf(currentUsers, currentUsers.length + 1);
        resultUsers[currentUsers.length] = userToAdd;
        return resultUsers;
    }

    private static User readOrError(int userId) throws SQLException, NonExistentRecordException {
        User resultUser = read(userId);
        if (Objects.isNull(resultUser)) {
            throw new NonExistentRecordException("Specified record does not exist.");
        }
        return resultUser;
    }

}
