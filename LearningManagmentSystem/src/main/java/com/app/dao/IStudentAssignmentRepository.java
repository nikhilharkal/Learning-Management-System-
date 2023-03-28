package com.app.dao;

import java.util.List;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.StudentAssignment;

public interface IStudentAssignmentRepository extends JpaRepository<StudentAssignment, Integer> {

	@Query("select sa from StudentAssignment sa where sa.student.id = ?1 and sa.assignment.id = ?2")
	StudentAssignment findByStudIdAndAssignmentId(int studentId, int assignmentId);

	@Query("select sa.student.firstName, sa.student.lastName,sa.assignment.subId.subjectName, sa.assignment.assignmentNo, "
			+ "sa.id from StudentAssignment sa where sa.student.standards.id =?1 and sa.assignment.subId.id =?2"
			+ " and sa.remarks = 'submitted'")
	List<String> getStudentsByStdId(int stdId, int subId);


}
