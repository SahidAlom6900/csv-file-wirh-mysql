package com.technoelevate.csv_mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.csv_mysql.pojo.Student;

public interface CsvRepository extends JpaRepository<Student, Integer> {

}
