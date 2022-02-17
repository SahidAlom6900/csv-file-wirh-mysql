package com.technoelevate.csv_mysql.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.csv_mysql.pojo.Student;

public interface CsvService {

	InputStream load();

	void store(MultipartFile file);

	Student storingInCsv(int id);
	
	List<Student> addStudent();
 
}
