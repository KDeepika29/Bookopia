package com.book.exchange.platform.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper<T> {
    T map(ResultSet resultSet) throws SQLException;
}