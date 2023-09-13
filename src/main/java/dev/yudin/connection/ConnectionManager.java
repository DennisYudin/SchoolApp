package dev.yudin.connection;

import dev.yudin.exceptions.ConnectionException;
import dev.yudin.filereader.Reader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements Manager {
	private Logger log = LogManager.getLogger(ConnectionManager.class);
	private Reader reader;

	public ConnectionManager(Reader reader) {
		this.reader = reader;
	}

	@Override
	public Connection getConnection() {
		var driver = reader.getPropValue("db.driver");
		var url = reader.getPropValue("db.url");

		Connection conn;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url);
			return conn;
		} catch (ClassNotFoundException e) {
			log.error("Problem with jdbcDriver");
			throw new ConnectionException("Problem with jdbcDriver", e);
		} catch (SQLException e) {
			log.error("Could not establish connection with database");
			throw new ConnectionException("Could not establish connection with database", e);
		}
	}
}
