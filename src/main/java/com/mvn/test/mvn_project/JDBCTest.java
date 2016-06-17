package com.mvn.test.mvn_project;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.PreparedStatement;

import java.sql.Statement;

public class JDBCTest {

	public static void main(String[] args) throws SQLException, IOException {

		JDBCTest jdbcTest = new JDBCTest();
		Properties properties = jdbcTest.loadProperties();

		Connection connection = DriverManager.getConnection(properties.getProperty("url"),
				properties.getProperty("username"), properties.getProperty("password"));

		System.out.println("connection established");

	}

	private Properties loadProperties() throws IOException {
		Properties properties = new Properties();
		InputStream stream = getClass().getResourceAsStream("db.properties");
		properties.load(stream);
		return properties;
	}

	public void printStudents(Connection connection) throws SQLException {
		String sql = "select * from students";
		Statement statement = connection.createStatement();
		statement.execute(sql);
		ResultSet resultSet = statement.getResultSet();
		while (resultSet.next()) {
			String str = "";
			str += resultSet.getString("id") + ", ";
			str += resultSet.getString("lastname") + ", ";
			str += resultSet.getString("firstname") + ", ";
			str += resultSet.getString("age") + ", ";
			System.out.println(str);

		}
	}

	public void addStudent(Connection connection) throws SQLException {
		String sql = "insert into students (lastname,firstname,age) values(?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "Smith");
		preparedStatement.setString(2, "John");
		preparedStatement.setInt(3, 44);

		preparedStatement.execute();

	}

}
