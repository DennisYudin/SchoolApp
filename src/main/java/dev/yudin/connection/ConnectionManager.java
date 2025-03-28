package dev.yudin.connection;

import dev.yudin.exceptions.ConnectionException;
import dev.yudin.filereader.Reader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements Manager {
	public static final String PROPERTIES_FILE = "application.properties";
	public static final String DB_DRIVER = "db.driver";
	public static final String DB_URL = "db.url";
	private Logger log = LogManager.getLogger(ConnectionManager.class);
	private Reader reader;

	public ConnectionManager(Reader reader) {
		this.reader = reader;
	}

	@Override
	public Connection getConnection() {
		var url = reader.getPropValue(DB_URL, PROPERTIES_FILE);

		Connection conn;
		try {
			conn = DriverManager.getConnection(url);
			return conn;
		} catch (SQLException e) {
			log.error("Could not establish connection with database");
			throw new ConnectionException("Could not establish connection with database", e);
		}
	}
}
