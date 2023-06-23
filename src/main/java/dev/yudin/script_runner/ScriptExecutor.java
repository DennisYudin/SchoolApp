package dev.yudin.script_runner;

import dev.yudin.ConnectionManager;
import dev.yudin.exceptions.AppConfigurationException;
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

	public ScriptExecutor(ConnectionManager datasource) {
		this.datasource = datasource;
	}

	@Override
	public void run(String fileName) {
		try (Connection connection = datasource.getConnection()) {
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
