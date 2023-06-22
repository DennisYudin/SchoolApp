package dev.yudin;

import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;

/**
 * Hello world!
 */
public class SchoolApp {
	public static void main(String[] args) {

		//todo init database structer
		Reader reader = new FileReader();
		ConnectionManager connectionManager = new ConnectionManager();

		Runnable scriptRunner = new ScriptExecutor(connectionManager, reader);

		scriptRunner.run("databaseStructure.sql");


	}
}
