package dev.yudin.filereader;


import dev.yudin.exceptions.FileProcessingException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader implements Reader {

	@Override
	public List<String> readFile(String pathToFile) {
		try (Stream<String> lines = Files.lines(Paths.get(pathToFile), Charset.defaultCharset())) {
			return lines.collect(Collectors.toList());
		} catch (IOException e) {

			throw new FileProcessingException("Error during read file :" + pathToFile, e);
		}
	}
}

