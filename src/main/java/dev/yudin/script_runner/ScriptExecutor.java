package dev.yudin.script_runner;

import dev.yudin.ConnectionManager;
import dev.yudin.exceptions.AppConfigurationException;
import dev.yudin.filereader.Reader;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.SQLException;

public class ScriptExecutor implements Runnable {
	private final Logger log = LogManager.getLogger(ScriptExecutor.class);

	public static final String ERROR_MESSAGE_CONNECTION = "Could not get connection";
	public static final String FILE_NOT_FOUND_ERROR_MESSAGE = "file not found! ";
	private ConnectionManager datasource;
	private Reader reader;

	public ScriptExecutor(ConnectionManager datasource, Reader reader) {
		this.datasource = datasource;
		this.reader = reader;
	}

	@Override
	public void run(String fileName) {
		var driver = reader.getValue("db.driver");
		var url = reader.getValue("db.url");

		try (Connection connection = datasource.getConnection(driver, url)) {
			ScriptRunner runner = new ScriptRunner(connection);
			InputStreamReader script = new InputStreamReader(getFileFromResourceFolder(fileName));
			runner.runScript(script);
		} catch (SQLException ex) {
			log.error(ERROR_MESSAGE_CONNECTION, ex);
			throw new AppConfigurationException(ERROR_MESSAGE_CONNECTION, ex);
		}
	}

	private InputStream getFileFromResourceFolder(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			log.error(FILE_NOT_FOUND_ERROR_MESSAGE + fileName);
			throw new AppConfigurationException(FILE_NOT_FOUND_ERROR_MESSAGE + fileName);
		} else {
			return inputStream;
		}
	}
}
