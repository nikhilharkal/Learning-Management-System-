package com.app.service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.IAssignmentRepository;
import com.app.dao.IPeriodRepository;
import com.app.dao.IStdRepository;
import com.app.dao.IStudentAttendence;
import com.app.dao.IStudentRepository;
import com.app.dto.ApiResponse;
import com.app.dto.StudentRegisterRequest;
import com.app.dto.StudentLoginResponse;
import com.app.entities.Period;
import com.app.entities.Std;
import com.app.entities.Student;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional

public class StudentServiceImpl implements IStudentService {
	
	@Autowired
	private IStudentRepository studentRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IAssignmentRepository assignmentRepo;
	
	@Autowired 
	private IPeriodRepository periodRepo;
	
	@Autowired
	private IStdRepository stdRepo;
	
	@Autowired
	private IStudentAttendence attendenceRepo;
	
	@Override
	public StudentLoginResponse login(String email, String password, String role) {
		Student student = studentRepo.findByEmailAndPasswordAndRole(email, password, role).orElseThrow(() -> new ResourceNotFoundException("Credentials Invalid!"));
		
		return new StudentLoginResponse(student.getFirstName(),student.getLastName(),student.getEmail(),student.getRole(),student.getStandards().getStd(),student.getStandards().getId(),student.getId(),student.getStatus());
	}

	@Override
	public ApiResponse registerNewStudent(StudentRegisterRequest studentDto) {
		System.out.println(studentDto.getStd());
		System.out.println(studentDto.getFirstName());
		Std std = stdRepo.findById(studentDto.getStd()).orElseThrow(()-> new ResourceNotFoundException("Invalid StdId"));
		//std.setId(studentDto.getStd());
		Student transientStudent = new Student(studentDto.getFirstName(),studentDto.getLastName(),studentDto.getGender(),studentDto.getEmail(),studentDto.getPassword(),studentDto.getRole(),std,"pending");
		System.out.println(transientStudent.getFirstName());
		Student persistentStudent =  studentRepo.save(transientStudent);
		System.out.println(persistentStudent.toString());
		return new ApiResponse("Student Register Successfully with ID:"+persistentStudent.getId());
	}

	@Override
	public List<String> getPeriodsDetailsByStdAndDate(int stdId) {
		
		return periodRepo.findByStdAndDate(stdId);
	}

	@Override
	public List<String> getWeeklyScheduleDetailsByStd(int stdId) {
		
		return periodRepo.getWeeklyScheduleByStd(stdId);
	}

	@Override
	public List<String> getAssignment(int stdId, int subId,int studentId) {
	
		return assignmentRepo.getAssignmentBySubIdAndStdId(stdId,subId,studentId);
	}

	@Override
	public List<String> getAttendeceBySubject(int studentId, int subjectId) {

		return attendenceRepo.getAttendeceBySubject(studentId,subjectId);
	}


}
