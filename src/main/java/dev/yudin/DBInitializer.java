package dev.yudin;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.Manager;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;

public class DBInitializer {
	public static final String DATABASE_STRUCTURE_FILE = "databaseStructure.sql";
	Manager dataSource = new ConnectionManager();
	Runnable scriptRunner = new ScriptExecutor(dataSource);

	public void init() {
		scriptRunner.run(DATABASE_STRUCTURE_FILE);

		//populate with data
	}
}
