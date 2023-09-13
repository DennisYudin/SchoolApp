package dev.yudin.connection;

import dev.yudin.exceptions.FileProcessingException;
import dev.yudin.filereader.FileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileReaderTesting extends FileReader {
	private final Logger log = LogManager.getLogger(FileReaderTesting.class);
	public static final String PROPERTIES_TEST_FILE = "test-application.properties";

	@Override
	public String getPropValue(String valueName) {
		Properties prop = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_TEST_FILE)) {
			prop.load(input);
			return prop.getProperty(valueName);
		} catch (IOException e) {
			log.error(ERROR_MESSAGE + PROPERTIES_TEST_FILE, e);
			throw new FileProcessingException(ERROR_MESSAGE + PROPERTIES_TEST_FILE, e);
		}
	}
}
