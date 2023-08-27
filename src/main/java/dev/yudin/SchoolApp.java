package dev.yudin;

public class SchoolApp {
	public static void main(String[] args) {
		DBInitializer dbInitializer = new DBInitializer();
		try {
			dbInitializer.init();
		} catch (Exception ex) {
//			System.err.println(ex.getMessage());
		}
	}
}
