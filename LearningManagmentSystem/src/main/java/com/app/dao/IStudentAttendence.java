package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.StudentAttendence;

public interface IStudentAttendence extends JpaRepository<StudentAttendence, Integer>{

	@Query("select sa.studentId.firstName, sa.studentId.lastName, sa.id from StudentAttendence sa where sa.periodId.id =?1 and sa.studentId.status ='approved'")
	List<String> fetchAttendence(int periodId);

	@Query("select  DATE_FORMAT(sa.periodId.date , '%Y-%m-%d'), sa.periodId.startTime, sa.periodId.endTime, sa.periodId.subjectId.subjectName, "
			+ " sa.periodId.teacherId.firstName, sa.periodId.teacherId.lastName, sa.attendednce from StudentAttendence sa "
			+ " where sa.studentId.id =?1 and sa.periodId.subjectId.id =?2")
	List<String> getAttendeceBySubject(int studentId, int subjectId);

	@Query("select sa from StudentAttendence sa where sa.periodId.id = ?1")
	List<StudentAttendence> findByPeriodId(int periodId);

}
