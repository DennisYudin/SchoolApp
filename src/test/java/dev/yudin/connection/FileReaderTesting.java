package dev.yudin.connection;

import dev.yudin.exceptions.FileProcessingException;
import dev.yudin.filereader.FileReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileReaderTesting extends FileReader {

	public static final String PROPERTIES_TEST_FILE = "src/test/resources/test-application.properties";

	@Override
	public String getValue(String valueName) {
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROPERTIES_TEST_FILE)) {
			prop.load(input);
		} catch (IOException e) {
			throw new FileProcessingException("Error during read file :" + PROPERTIES_TEST_FILE, e);
		}
		return prop.getProperty(valueName);
	}
}
