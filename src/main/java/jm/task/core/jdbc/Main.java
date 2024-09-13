package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static boolean useHibernate = true;

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        List<User> users = new ArrayList<>();

        users.add(new User("Иван", "Иванов", (byte) 20));
        users.add(new User("Петр", "Петров", (byte) 25));
        users.add(new User("Сергей", "Сергеев", (byte) 30));
        users.add(new User("Елена", "Ленина", (byte) 35));

        doSomeOperation(users);
    }

    static void doSomeOperation(List<User> users) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        for (User user : users) {
            userService.saveUser(
                    user.getName(),
                    user.getLastName(),
                    user.getAge()
            );
        }

        List<User> readedUsers = userService.getAllUsers();
        for (User user : readedUsers) {
            System.out.println(user);
        }

        userService.removeUserById(1);

        readedUsers = userService.getAllUsers();
        for (User user : readedUsers) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

        if (useHibernate) {
            Util.closeSessionFactory();
        }


    }
}

