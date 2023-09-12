package dev.yudin.filereader;


import dev.yudin.exceptions.FileProcessingException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader implements Reader {
	public static final String PROPERTIES_FILE = "application.properties";
	public static final String ERROR_MESSAGE = "Error during read file: ";
	private final Logger log = LogManager.getLogger(FileReader.class);

	@Override
	public List<String> read(String pathToFile) {
		log.info("Call method read for file:" + pathToFile);

		try (Stream<String> lines = Files.lines(Paths.get(pathToFile), Charset.defaultCharset())) {
			return lines.collect(Collectors.toList());
		} catch (IOException e) {
			log.error(ERROR_MESSAGE + pathToFile, e);
			throw new FileProcessingException(ERROR_MESSAGE + pathToFile, e);
		}
	}

//	@Override
//	public List<String> read(String fileName) {
//		log.info("Call method read() file name: " + fileName);
//
////		StringBuilder result = new StringBuilder();
//		List<String> result = new ArrayList<>();
//
//		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
//			 BufferedReader reader =
//					 new BufferedReader(
//							 new InputStreamReader(
//									 Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))) {
//			readAndSaveLines(result, reader);
//		} catch (IOException ex) {
//			log.error("Error during reading file " + fileName);
//			throw new FileProcessingException("Error during reading file " + fileName, ex);
//		}
//		return result;
//	}

	private void readAndSaveLines(List<String> result, BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			result.add(line);
		}
	}

	@Override
	public String getPropValue(String valueName) {
		Properties prop = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			prop.load(input);
			return prop.getProperty(valueName);
		} catch (IOException e) {
			log.error(ERROR_MESSAGE + PROPERTIES_FILE, e);
			throw new FileProcessingException(ERROR_MESSAGE + PROPERTIES_FILE, e);
		}
	}
}
