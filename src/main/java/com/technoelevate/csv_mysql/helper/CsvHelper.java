package com.technoelevate.csv_mysql.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.csv_mysql.exception.CsvException;
import com.technoelevate.csv_mysql.pojo.Student;

public class CsvHelper {

	public static final String TYPE = "text/csv";
	public static final String STUDENT = "Student";
	protected static final String[] HEADER = { STUDENT + " Id", STUDENT + " Name", STUDENT + " Address",
			STUDENT + " Contact Number" };

	public static boolean hasCSVFormat(MultipartFile file) {
		return TYPE.equals(file.getContentType());
	}

	public static List<Student> csvToTutorials(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			List<Student> tutorials = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				Student student = new Student(Integer.parseInt(csvRecord.get(STUDENT + " Id")),
						csvRecord.get(STUDENT + " Name"), csvRecord.get(STUDENT + " Address"),
						Long.parseLong(csvRecord.get(STUDENT + " Contact Number")));
				tutorials.add(student);
			}
			return tutorials;
		} catch (IOException exception) {
			throw new CsvException("fail to parse CSV file: " + exception.getMessage());
		}
	}

	public static InputStream tutorialsToCSV(List<Student> students) {
		final CSVFormat format = CSVFormat.DEFAULT.withHeader(HEADER).withIgnoreHeaderCase().withTrim();
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (Student student : students) {
				List<String> data = Arrays.asList(String.valueOf(student.getId()), student.getName(),
						student.getAddress(), String.valueOf(student.getNumber()));
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
			throw new CsvException("fail to import data to CSV file: " + exception.getMessage());
		}
	}

	public static int storingInCsv(Student student) {
		String csvFile = "src/main/resources/student.csv";
		File csvOutputFile = new File(csvFile);
		Map<Integer, Student> students = new LinkedHashMap<>();
		try (CSVParser csvParser = new CSVParser(new FileReader(csvFile),
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				Student student0 = new Student(Integer.parseInt(csvRecord.get(STUDENT + " Id")),
						csvRecord.get(STUDENT + " Name"), csvRecord.get(STUDENT + " Address"),
						Long.parseLong(csvRecord.get(STUDENT + " Contact Number")));
				students.put(student0.getId(), student0);
			}
			if (students.put(student.getId(), student) != null)
				return 1;
			
			CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(csvOutputFile),
					CSVFormat.DEFAULT.withHeader(HEADER).withIgnoreHeaderCase().withTrim());
			for (Student student0 : students.values()) {
				List<String> data = Arrays.asList(String.valueOf(student0.getId()), student0.getName(),
						student0.getAddress(), String.valueOf(student0.getNumber()));
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			csvPrinter.close();
			return 0;
		} catch (Exception exception) {
			throw new CsvException("fail to import data to CSV file: " + exception.getMessage());
		}
	}
}
