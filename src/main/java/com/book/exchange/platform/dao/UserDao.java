package com.book.exchange.platform.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.book.exchange.platform.model.User;

public class UserDao {

	// Database connection details	
	private static final String URL = System.getenv("DB_CONNECTION_URL");
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

	// Common method to get a connection
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	// Common method to close resources
	private void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Common method to execute update operations
	private void executeUpdate(String query, Object... params) {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Common method to execute select operations
	private <T> List<T> executeQuery(String query, ResultSetMapper<T> mapper, Object... params) {
		List<T> resultList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				T result = mapper.map(resultSet);
				resultList.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	// Method to save a new user
	public void saveUser(User user) {
		String saveQuery = "INSERT INTO users (name, email, location, password) VALUES (?, ?, ?, ?)";
		executeUpdate(saveQuery, user.getName(), user.getEmail(), user.getLocation(), user.getPassword());
	}

	public void updateUser(User user) {
	    StringBuilder queryBuilder = new StringBuilder("UPDATE users SET ");
	    List<Object> params = new ArrayList<>();

	    if (user.getName() != null) {
	        queryBuilder.append("name = ?, ");
	        params.add(user.getName());
	    }
	    if (user.getEmail() != null) {
	        queryBuilder.append("email = ?, ");
	        params.add(user.getEmail());
	    }
	    if (user.getLocation() != null) {
	        queryBuilder.append("location = ?, ");
	        params.add(user.getLocation());
	    }
	    if (user.getPassword() != null) {
	        queryBuilder.append("password = ?, ");
	        params.add(user.getPassword());
	    }
	    if (user.getGenre() != null && !user.getGenre().isEmpty()) {
	        // Convert the list of genres to a comma-separated string
	        String genreString = String.join(",", user.getGenre());
	        queryBuilder.append("genre = ?, ");
	        params.add(genreString);
	    }

	    queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());

	    queryBuilder.append(" WHERE id = ?");
	    params.add(user.getId());

	    executeUpdate(queryBuilder.toString(), params.toArray());
	}

	// Method to delete a user by ID
	public void deleteUser(int userId) {
		executeUpdate("DELETE FROM users WHERE id = ?", userId);
	}

	// Method to get a user by ID
	public User getUserById(int userId) {
	    String query = "SELECT * FROM users WHERE id = ?";
	    List<User> users = executeQuery(query, rs -> {
	        User user = new User();
	        user.setId(rs.getInt("id"));
	        user.setName(rs.getString("name"));
	        user.setEmail(rs.getString("email"));
	        user.setLocation(rs.getString("location"));
	        user.setPassword(rs.getString("password"));
	  
	        String genreString = rs.getString("genre");
	        List<String> genreList = Arrays.asList(genreString.split(","));
	        user.setGenre(genreList);
	        return user;
	    }, userId);
	    return users.isEmpty() ? null : users.get(0);
	}

	// Method to get a user by email
	public User getUserByEmail(String email) {
	    String query = "SELECT * FROM users WHERE email = ?";
	    List<User> users = executeQuery(query, rs -> {
	        User user = new User();
	        user.setId(rs.getInt("id"));
	        user.setName(rs.getString("name"));
	        user.setEmail(rs.getString("email"));
	        user.setLocation(rs.getString("location"));
	        user.setPassword(rs.getString("password"));
	   
	        String genreString = rs.getString("genre");
	        List<String> genreList = Arrays.asList(genreString.split(","));
	        user.setGenre(genreList);
	        return user;
	    }, email);
	    return users.isEmpty() ? null : users.get(0);
	}

}