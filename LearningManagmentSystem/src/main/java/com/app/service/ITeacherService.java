package com.app.service;

import java.time.LocalDate;
import java.util.List;

import com.app.dto.ApiResponse;
import com.app.dto.AssignmentRequestDto;
import com.app.dto.TeacherRegisterRequest;
import com.app.dto.StudentLoginResponse;
import com.app.dto.StudentStatus;
import com.app.dto.TeacherLoginResponse;
import com.app.entities.Assignment;

public interface ITeacherService {
	TeacherLoginResponse login(String email, String password, String role);
	ApiResponse registerNewTeacher(TeacherRegisterRequest teacher);
	List<String> getTimeTable(int teacherId);
	List<String> getWeeklyScheduleDetailsByTeacherId(int teacherId);
	//Assignment setAssignment(String std, String subject,int assignmentNo);
	
	List<String> getAllSchedule(int teacherId);
	List<String> studentAttendenceList(int periodId);
	ApiResponse updateAttendence(StudentStatus status);
	List<String> getStdByTeacherId(int teacherId);
	List<String> getallStudentByStd(int stdId, int subId);
	ApiResponse updateAssigmentStatus(StudentStatus status);
	
}
