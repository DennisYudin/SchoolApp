package dev.yudin;

import dev.yudin.dao.impl.CoursesDAOImpl;

/**
 * Hello world!
 */
public class SchoolApp {
	public static void main(String[] args) {

		DBInitializer dbStructure = new DBInitializer();

		dbStructure.init();
	}
}
