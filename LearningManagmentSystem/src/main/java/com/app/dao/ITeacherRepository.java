package com.app.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Assignment;
import com.app.entities.Teacher;

public interface ITeacherRepository extends JpaRepository<Teacher, Integer> {
	
	Optional<Teacher> findByEmailAndPasswordAndRole(String email,String password,String role );
	
	@Query("select t.firstName,t.lastName,t.subjects.subjectName,t.id from Teacher t where t.status = 'pending'")
	List<String> getTeacherRegRequest();
	
	@Query("select t.id, t.firstName, t.lastName from Teacher t where t.subjects.id = ?1 and t.status = 'approved'")
	List<String> getTeacherBySubId(int subId);
	
//	@Query("Insert into Assignment (std,subId,assignmentNo) values (?1,?2,?3)")
//	Assignment setAssignmentForStudent(String std, String subject,int assinmentNo);

}
