package dev.yudin;

import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;

/**
 * Hello world!
 */
public class SchoolApp {
	public static void main(String[] args) {

		//todo init database structer

		Reader reader = new FileReader();

		var driver = reader.getValue("db.driver");
		var url = reader.getValue("db.url");

		ConnectionManager connectionManager = new ConnectionManager();

		connectionManager.getConnection(driver, url);
	}
}
