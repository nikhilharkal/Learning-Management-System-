package com.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.IPeriodRepository;
import com.app.dao.IStudentAssignmentRepository;
import com.app.dao.IStudentAttendence;
import com.app.dao.IStudentRepository;
import com.app.dao.ISubjectRepository;
import com.app.dao.ITeacherRepository;
import com.app.dto.ApiResponse;
import com.app.dto.TeacherRegisterRequest;
import com.app.dto.StudentLoginResponse;
import com.app.dto.StudentStatus;
import com.app.dto.TeacherLoginResponse;
import com.app.entities.Assignment;
import com.app.entities.Std;
import com.app.entities.Student;
import com.app.entities.StudentAssignment;
import com.app.entities.StudentAttendence;
import com.app.entities.Subject;
import com.app.entities.Teacher;

@Service
@Transactional
public class TeacherServiceImpl implements ITeacherService {
	
	@Autowired
	private ITeacherRepository teacherRepo;
	
	@Autowired
	private ModelMapper mapper; 
	
	@Autowired
	private IPeriodRepository periodRepo;
	
	@Autowired
	private ISubjectRepository subRepo;
	
	@Autowired
	private IStudentAttendence attendenceRepo;
	
	@Autowired
	private IStudentAssignmentRepository assignmentRepo;
	
	@Override
	public TeacherLoginResponse login(String email, String password, String role) {
		Teacher teacher = teacherRepo.findByEmailAndPasswordAndRole(email, password, role).orElseThrow(() -> new ResourceNotFoundException("Credentials Invalid!"));
		System.out.println("subject:"+teacher.getSubjects().getSubjectName());
		return new TeacherLoginResponse(teacher.getFirstName(),teacher.getLastName(),teacher.getEmail(),teacher.getRole(),teacher.getId(),teacher.getStatus(),teacher.getSubjects().getSubjectName(),teacher.getSubjects().getId());
	}


	@Override
	public ApiResponse registerNewTeacher(TeacherRegisterRequest teacher) {
		Subject subject = subRepo.findById(teacher.getSubject()).orElseThrow(()-> new ResourceNotFoundException("Invalid SubjectID"));
//		List<Subject>subjects = new ArrayList<Subject>();
//		subjects.add(subId);
		Teacher transientTeacher = new Teacher(teacher.getFirstName(), teacher.getLastName(), teacher.getGender(), teacher.getEmail(), teacher.getPassword(), teacher.getRole(), subject,"pending");
		Teacher persistentTeacher = teacherRepo.save(transientTeacher);
		return new ApiResponse("Teacher register suceesfully with ID:"+persistentTeacher.getId());
	}


	@Override
	public List<String> getTimeTable(int teacherId) {
		
		return periodRepo.findByTeacherIdAndDate(teacherId);
	}


	@Override
	public List<String> getWeeklyScheduleDetailsByTeacherId(int teacherId) {
		
		return periodRepo.getWeeklyScheduleByTeacherId(teacherId);
	}

	@Override
	public List<String> getAllSchedule(int teacherId) {
		
		return periodRepo.getAllSchedule(teacherId);
	}

	@Override
	public List<String> studentAttendenceList(int periodId) {
		

		return attendenceRepo.fetchAttendence(periodId);
	}


	@Override
	public ApiResponse updateAttendence(StudentStatus status) {
		StudentAttendence studAttendence =  attendenceRepo.findById(status.getId()).orElseThrow(() -> new ResourceNotFoundException("Credentials Invalid!"));
		studAttendence.setAttendednce(status.getStatus());
		StudentAttendence persistAttendence = attendenceRepo.save(studAttendence);
		return new ApiResponse(persistAttendence.getStudentId().getFirstName()+" was "+persistAttendence.getAttendednce());
	}


	@Override
	public List<String> getStdByTeacherId(int teacherId) {
		
		return periodRepo.geyStdByTeacherId(teacherId);
	}


	@Override
	public List<String> getallStudentByStd(int stdId,int subId) {
		return assignmentRepo.getStudentsByStdId(stdId,subId);
	}


	@Override
	public ApiResponse updateAssigmentStatus(StudentStatus status) {
		StudentAssignment stud = assignmentRepo.findById(status.getId()).orElseThrow(() -> new ResourceNotFoundException("Credentials Invalid!"));
		stud.setRemarks(status.getStatus());
		StudentAssignment persistentStud = assignmentRepo.save(stud);
		return new ApiResponse("Assignment Status Changed to "+persistentStud.getRemarks());
	}
}
