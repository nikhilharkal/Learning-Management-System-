package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Assignment;

public interface IAssignmentRepository extends JpaRepository<Assignment, Integer>{
	@Query("select sa.assignment.subId.subjectName, sa.assignment.assignmentNo, DATE_FORMAT(sa.assignment.publishDate , '%Y-%m-%d'),"
			+ "sa.assignment.id, sa.remarks from  StudentAssignment sa "
			+ "where sa.assignment.stdId.id =?1 and sa.assignment.subId.id =?2 and sa.student.id =?3 order by  sa.assignment.subId, sa.assignment.assignmentNo ")
	List<String> getAssignmentBySubIdAndStdId(int stdId, int subId,int studentId);
	
	@Query("select COALESCE(max(a.assignmentNo),0) from Assignment a where a.stdId.id = ?1 and a.subId.id = ?2")
	int findByStdIdAndSubId(int stdId, int subId);

}
//, a.assignmentFile 
