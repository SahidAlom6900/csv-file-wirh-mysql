package com.technoelevate.csv_mysql.exception;

@SuppressWarnings("serial")
public class CsvException extends RuntimeException {
	public CsvException(String msg) {
		super(msg);
	}
}
