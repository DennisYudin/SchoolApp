package dev.yudin.connection;

import dev.yudin.exceptions.ConnectionException;
import dev.yudin.filereader.Reader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerTesting implements Manager {
	private static final String PROPERTIES_TEST_FILE = "test-application.properties";
	private Reader reader;
	public ConnectionManagerTesting(Reader reader) {
		this.reader = reader;
	}

	@Override
	public Connection getConnection() {
		String url = reader.getPropValue("test.db.url", PROPERTIES_TEST_FILE);

		Connection conn;
		try {
			DriverManager.registerDriver(new org.h2.Driver());
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new ConnectionException("Could not establish connection with database", e);
		}
		return conn;
	}
}
