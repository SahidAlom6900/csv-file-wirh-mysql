package com.technoelevate.csv_mysql;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCsvMysqlApplication {
//	private static String csvFile;
//	private static File csvOutputFile;

	public static void main(String[] args) {

		SpringApplication.run(SpringBootCsvMysqlApplication.class, args);
	}

//	@Bean
//	public CSVPrinter csvPrinter() {
//		String csvFile = "src/main/resources/student.csv";
//		File csvOutputFile = new File(csvFile);
//		String STUDENT = "Student";
//		String[] HEADER = { STUDENT + " Id", STUDENT + " Name", STUDENT + " Address", STUDENT + " Contact Number" };
//
//		try {
//			CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(csvOutputFile),
//					CSVFormat.DEFAULT.withHeader(HEADER).withIgnoreHeaderCase().withTrim());
//			return csvPrinter;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Bean
//	public CSVParser csvParser() {
//		String csvFile = "src/main/resources/student.csv";
//		try {
//			CSVParser csvParser = new CSVParser(new FileReader(csvFile),
//					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
//			System.out.println(csvParser);
//			System.out.println();
//			return csvParser;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
