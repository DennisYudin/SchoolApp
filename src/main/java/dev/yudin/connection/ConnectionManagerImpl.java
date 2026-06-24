package dev.yudin.connection;

import dev.yudin.exceptions.ConnectionException;
import dev.yudin.filereader.Reader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {
	private Logger log = LogManager.getLogger(ConnectionManagerImpl.class);

	private static final String PROPERTIES_FILE = "application.properties";
	private static final String DB_URL = "db.url";

	private Reader reader;

	public ConnectionManagerImpl(Reader reader) {
		this.reader = reader;
	}

	@Override
	public Connection getConnection() {
		String url = reader.getPropValue(DB_URL, PROPERTIES_FILE);
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			log.error("Could not establish connection with database");
			throw new ConnectionException("Could not establish connection with database", e);
		}
	}
}
