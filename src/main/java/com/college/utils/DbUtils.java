package com.college.utils;

import com.college.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class DbUtils {
    private Connection connection;


    @PostConstruct
    public void init () {
        String host = "localhost";
        String username = "root";
        String password = "1234";

        String url = "jdbc:mysql://localhost:3306/ash_2025?useSSL=false&serverTimezone=UTC";
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUserOnDb (User user) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO users (first_name, last_name, phone, username)" +
                    "VALUE (?, ?, ?, ?)");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers () {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    this.connection.prepareStatement("SELECT first_name, last_name, phone, username FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String phone = resultSet.getString(3);
                String username = resultSet.getString(4);
                User user = new User(firstName, lastName, phone, username);
                users.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;

    }


}
