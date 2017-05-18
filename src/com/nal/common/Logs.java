package com.nal.common;

public class Logs {
	public static void logMessage(String msg) {
		System.out.println("[" + msg + "]");
	}

	public static void logWarning(String error) {
		System.err.println("[WARN] " + error);
	}
	
	public static void logError(String error) {
		System.err.println("[ERROR] " + error);
	}
}

