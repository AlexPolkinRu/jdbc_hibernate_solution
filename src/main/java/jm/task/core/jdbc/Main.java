package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        List<User> users = new ArrayList<>();

        users.add(new User("Иван", "Иванов", (byte) 20));
        users.add(new User("Петр", "Петров", (byte) 25));
        users.add(new User("Сергей", "Сергеев", (byte) 30));
        users.add(new User("Елена", "Ленина", (byte) 35));

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        for (User user : users) {
            userService.saveUser(
                    user.getName(),
                    user.getLastName(),
                    user.getAge()
            );
        }

        System.out.println(userService.getAllUsers());

        userService.removeUserById(1);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
