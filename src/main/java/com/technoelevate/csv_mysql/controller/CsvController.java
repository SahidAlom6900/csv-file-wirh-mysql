package com.technoelevate.csv_mysql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.csv_mysql.helper.CsvHelper;
import com.technoelevate.csv_mysql.pojo.Student;
import com.technoelevate.csv_mysql.response.ResponseMessage;
import com.technoelevate.csv_mysql.service.CsvService;

@RestController
@RequestMapping("api/v1/csv")
public class CsvController {

	@Autowired
	private CsvService csvService;

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile() {
		String filename = "student.csv";
		InputStreamResource file = new InputStreamResource(csvService.load());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

	@PostMapping("/store/{id}")
	public ResponseEntity<ResponseMessage> storingInCsv(@PathVariable("id") int id) {
		Student student = csvService.storingInCsv(id);
		if (student != null)
			return new ResponseEntity<>(new ResponseMessage(false, "Fetch Student By Id", student), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "Student Already Present!!!", student), HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<ResponseMessage> addStudents() {
		try {
			List<Student> addStudent = csvService.addStudent();
			return new ResponseEntity<>(new ResponseMessage(false, "Add Students", addStudent), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage(true, "Student Already Present!!!", e.getMessage()),
					HttpStatus.OK);
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> name(MultipartFile file) {
		if (CsvHelper.hasCSVFormat(file)) {
			try {
				csvService.store(file);
				String message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return new ResponseEntity<>(new ResponseMessage(false, message, ""), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(new ResponseMessage(true, "Fail To Upload CSV File !!!", e.getMessage()),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new ResponseMessage(true, "Please upload a csv file!", null), HttpStatus.OK);
	}

}
