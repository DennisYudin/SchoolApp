package dev.yudin.filereader;

import java.util.List;

public interface Reader {

	List<String> read(String input);

	String getValue(String valueName);
}
