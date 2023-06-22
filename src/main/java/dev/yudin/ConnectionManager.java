package dev.yudin;

import dev.yudin.exceptions.ConnectionException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private Logger log = LogManager.getLogger(ConnectionManager.class);

	public Connection getConnection(String jdbcDriver, String url) {
		log.info("Call method getConnection() with: " + jdbcDriver + " " + url);
		Connection conn;
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			throw new ConnectionException("Problem with jdbcDriver", e);
		} catch (SQLException e) {
			throw new ConnectionException("Could not establish connection with database", e);
		}
		return conn;
	}
}
