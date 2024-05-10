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

import com.book.exchange.platform.model.Book;
import com.book.exchange.platform.model.Provider;

public class BookDao {

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

	// Method to save a new book
	public void addBook(Book book) {
		String query = "INSERT INTO books (title, author, `condition`, genre, availability, operationType, provider_name, provider_email) \r\n"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		executeUpdate(query, book.getTitle(), book.getAuthor(), book.getCondition(), book.getGenre(),
				book.getOperationType(), book.getProvider().getName(), book.getProvider().getEmail());
	}

	// Method to update an existing book
	public void updateBook(Book book) {
		StringBuilder queryBuilder = new StringBuilder("UPDATE books SET ");
		List<Object> params = new ArrayList<>();

		if (book.getTitle() != null) {
			queryBuilder.append("title = ?, ");
			params.add(book.getTitle());
		}
		if (book.getAuthor() != null) {
			queryBuilder.append("author = ?, ");
			params.add(book.getAuthor());
		}
		if (book.getCondition() != null) {
			queryBuilder.append("condition = ?, ");
			params.add(book.getCondition());
		}
		if (book.getGenre() != null) {
			queryBuilder.append("genre = ?, ");
			params.add(book.getGenre());
		}
		if (book.getOperationType() != null) {
			queryBuilder.append("operationType = ?, ");
			params.add(book.getOperationType());
		}
		if (book.getProvider() != null) {
			if (book.getProvider().getName() != null) {
				queryBuilder.append("provider_name = ?, ");
				params.add(book.getProvider().getName());
			}
			if (book.getProvider().getEmail() != null) {
				queryBuilder.append("provider_email = ?, ");
				params.add(book.getProvider().getEmail());
			}
		}

		
		queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());

		
		queryBuilder.append(" WHERE id = ?");
		params.add(book.getId());

		executeUpdate(queryBuilder.toString(), params.toArray());
	}

	// Method to get a list of books based on provider email
	public List<Book> getBooksByEmail(String email) {
		String query = "SELECT * FROM books WHERE provider_email = ?";
		return executeQuery(query, rs -> {
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setCondition(rs.getString("condition"));
			List<String> genres = Arrays.asList(rs.getString("genre").split(","));
			book.setGenre(genres);
			book.setOperationType(rs.getString("operationType"));
			Provider provider = new Provider();
			provider.setName(rs.getString("provider_name"));
			provider.setEmail(rs.getString("provider_email"));
			book.setProvider(provider);
			return book;
		}, email);
	}

	// Method to delete a book by ID
	public void deleteBook(int bookId) {
		String query = "DELETE FROM books WHERE id = ?";
		executeUpdate(query, bookId);
	}

	public List<Book> searchBooksByFilters(List<String> genres, String author, String title, String location,
			boolean availability) {
		String query = "SELECT * FROM books WHERE ";
		List<Object> parameters = new ArrayList<>();

		if (genres != null && !genres.isEmpty()) {
			query += "(";
			for (int i = 0; i < genres.size(); i++) {
				if (i > 0) {
					query += " OR ";
				}
				query += "genre = ?";
				parameters.add(genres.get(i));
			}
			query += ")";
		}
		if (author != null && !author.isEmpty()) {
			if (!parameters.isEmpty()) {
				query += " AND ";
			}
			query += "author = ?";
			parameters.add(author);
		}
		if (title != null && !title.isEmpty()) {
			if (!parameters.isEmpty()) {
				query += " AND ";
			}
			query += "title LIKE ?";
			parameters.add("%" + title + "%");
		}
		if (location != null && !location.isEmpty()) {
			if (!parameters.isEmpty()) {
				query += " AND ";
			}
			query += "location = ?";
			parameters.add(location);
		}
		if (availability) {
			if (!parameters.isEmpty()) {
				query += " AND ";
			}
			query += "availability = ?";
			parameters.add(1);
		}

		return executeQuery(query, rs -> {
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setCondition(rs.getString("condition"));
			List<String> bookGenres = new ArrayList<>();
			for (String genre : genres) {
				bookGenres.add(genre);
			}
			book.setGenre(bookGenres);
			book.setOperationType(rs.getString("operationType"));
			Provider provider = new Provider();
			provider.setName(rs.getString("provider_name"));
			provider.setEmail(rs.getString("provider_email"));
			book.setProvider(provider);
			return book;
		}, parameters.toArray());
	}
}