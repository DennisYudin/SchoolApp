package dev.yudin.filereader;


import dev.yudin.exceptions.FileProcessingException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader implements Reader {

	public static final String PROPERTIES_FILE = "src/main/resources/application.properties";

	private Logger log = LogManager.getLogger(FileReader.class);

	@Override
	public List<String> read(String pathToFile) {
		log.info("Call method read for file:" + pathToFile);

		try (Stream<String> lines = Files.lines(Paths.get(pathToFile), Charset.defaultCharset())) {
			return lines.collect(Collectors.toList());
		} catch (IOException e) {
			log.error("Error during read file :" + pathToFile, e);
			throw new FileProcessingException("Error during read file :" + pathToFile, e);
		}
	}

	public String getValue(String valueName) {
		log.info("Call method getValue for:" + valueName);

		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
			prop.load(input);
		} catch (IOException e) {
			log.error("Error during read file :" + PROPERTIES_FILE, e);
			throw new FileProcessingException("Error during read file :" + PROPERTIES_FILE, e);
		}
		return prop.getProperty(valueName);
	}
}
