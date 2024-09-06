package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    final Connection CONNECTION = Util.getConnection();

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (
                final Statement STATEMENT = CONNECTION.createStatement()
        ) {
            String queryCreateUsersTable = """
                            CREATE TABLE IF NOT EXISTS users (
                                id SERIAL PRIMARY KEY ,
                                name VARCHAR(100) NOT NULL,
                                lastname VARCHAR(100) NOT NULL,
                                age SMALLINT NOT NULL
                            );
                    """;
            STATEMENT.executeUpdate(queryCreateUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (
                final Statement STATEMENT = CONNECTION.createStatement()
        ) {
            String queryDropUsersTable = "DROP TABLE IF EXISTS users;";
            STATEMENT.executeUpdate(queryDropUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String querySaveUser = """
                    INSERT INTO users (name, lastname, age)
                    VALUES (?, ?, ?);
                """;
        try (
                final PreparedStatement PREPARED_STATEMENT = CONNECTION.prepareStatement(querySaveUser)
        ) {
            PREPARED_STATEMENT.setString(1, name);
            PREPARED_STATEMENT.setString(2, lastName);
            PREPARED_STATEMENT.setByte(3, age);

            PREPARED_STATEMENT.executeUpdate();

            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String queryRemoveUserById = "DELETE FROM users WHERE id = ?;";
        try (
                final PreparedStatement PREPARED_STATEMENT = CONNECTION.prepareStatement(queryRemoveUserById)
        ) {
            PREPARED_STATEMENT.setLong(1, id);
            PREPARED_STATEMENT.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (
                final Statement STATEMENT = CONNECTION.createStatement()
        ) {
            String queryGetAllUsers = "SELECT id, name, lastname, age FROM users;";
            ResultSet resultSet = STATEMENT.executeQuery(queryGetAllUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try (
                final Statement STATEMENT = CONNECTION.createStatement()
        ) {
            String queryCleanUsersTable = "DELETE FROM users;";
            STATEMENT.executeUpdate(queryCleanUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
