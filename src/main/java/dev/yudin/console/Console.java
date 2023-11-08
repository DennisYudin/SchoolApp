package dev.yudin.console;

import java.util.Scanner;

public interface Console {
	int readInt(String prompt, Scanner scanner);
	String readString(String prompt, Scanner scanner);
}
