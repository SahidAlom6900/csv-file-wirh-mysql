package com.technoelevate.csv_mysql.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.csv_mysql.exception.CsvException;
import com.technoelevate.csv_mysql.helper.CsvHelper;
import com.technoelevate.csv_mysql.pojo.Student;
import com.technoelevate.csv_mysql.repository.CsvRepository;

@Service
public class CsvServiceImpl implements CsvService {

	@Autowired
	private CsvRepository csvRepository;
	private int isPresentStudent;

	public InputStream load() {
		List<Student> tutorials = csvRepository.findAll();
		return CsvHelper.tutorialsToCSV(tutorials);
	}

	@Override
	public void store(MultipartFile file) {
		try {
			List<Student> tutorials = CsvHelper.csvToTutorials(file.getInputStream());
			csvRepository.saveAll(tutorials);
		} catch (IOException e) {
			throw new CsvException("fail to store csv data: " + e.getMessage());
		}
	}

	@Override
	public Student storingInCsv(int id) {
		Optional<Student> student = csvRepository.findById(id);
		student.ifPresentOrElse(student0 -> isPresentStudent = CsvHelper.storingInCsv(student0),
				() -> new CsvException("Student not Found!!!"));
		if (isPresentStudent == 0 && student.isPresent())
			return student.get();
		else if(!student.isPresent())
			throw new CsvException("Student not Found!!!");
		return null;
	}

	@Override
	public List<Student> addStudent() {
		List<Student> students = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			students.add(new Student("Sahid" + i, "Assam" + i, (8399842584l + i)));
		}
		return csvRepository.saveAll(students);
	}

}
