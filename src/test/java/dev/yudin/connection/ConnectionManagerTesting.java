package dev.yudin.connection;

import dev.yudin.exceptions.ConnectionException;
import dev.yudin.filereader.Reader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerTesting extends ConnectionManager {

	private Logger log = LogManager.getLogger(ConnectionManagerTesting.class);

	private Reader reader;
	public ConnectionManagerTesting(Reader reader) {
		super(reader);
		this.reader = reader;
	}

	@Override
	public Connection getConnection() {
		var driver = reader.getValue("test.db.driver");
		var url = reader.getValue("test.db.url");

		Connection conn;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			throw new ConnectionException("Problem with jdbcDriver", e);
		} catch (SQLException e) {
			throw new ConnectionException("Could not establish connection with database", e);
		}
		return conn;
	}
}
