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
                    "INSERT INTO users (username, password)" +
                    "VALUE (?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers () {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    this.connection.prepareStatement("SELECT username, password FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(username, password);
                users.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;

    }

    public boolean checkIfUsernameExists (String username) {
        try {
            PreparedStatement preparedStatement =
                    this.connection.prepareStatement(
                            "SELECT username FROM users " +
                                    "WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByUsernameAndPassword (String username, String password) {
        try {
            PreparedStatement preparedStatement =
                    this.connection.prepareStatement(
                            "SELECT username, password FROM users " +
                                    "WHERE username = ? " +
                                    "AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String usr = resultSet.getString("username");
                String pwd = resultSet.getString("password");
                return new User(usr, pwd);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
