package com.app.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Period;
import com.app.entities.Student;

public interface IStudentRepository extends JpaRepository<Student, Integer> {
	
	Optional<Student> findByEmailAndPasswordAndRole(String email,String password,String role );
	
	@Query("select s from Student s where s.standards.id =?1 and s.status = 'approved'")
	List<Student> findStudentsByStdId(int stdId);

	@Query("select s.firstName,s.lastName,s.standards.std,s.id from Student s where s.status = 'pending'")
	List<String> getStudentRegRequest();

}
