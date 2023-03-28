package com.app.service;

import java.sql.Time;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dto.AddPeriod;
import com.app.dao.IPeriodRepository;
import com.app.dao.IStdRepository;
import com.app.dao.IStudentAttendence;
import com.app.dao.IStudentRepository;
import com.app.dao.ISubjectRepository;
import com.app.dao.ITeacherRepository;
import com.app.dto.AdminLoginResponse;
import com.app.dto.ApiResponse;
import com.app.dto.UpdateStatus;
import com.app.entities.Period;
import com.app.entities.Std;
import com.app.entities.Student;
import com.app.entities.StudentAttendence;
import com.app.entities.Subject;
import com.app.entities.Teacher;


@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private IStudentRepository studentRepo;
	
	@Autowired
	private ITeacherRepository teacherRepo;
	
	@Autowired
	private IPeriodRepository periodRepo;
	
	@Autowired
	private IStdRepository stdRepo;
	
	@Autowired
	private ISubjectRepository subRepo;
	
	@Autowired
	private IStudentAttendence attendenceRepo;
	
	@Override
	public AdminLoginResponse login(String email, String password, String role) {
		if(email.equals("shilpi@gmail.com") && password.equals("shilpi@123") && role.equals("admin"))
			return new AdminLoginResponse("Shilpi","Shalini","shilpi@gmail.com","admin");
		else
			throw new ResourceNotFoundException("Credentials Invalid!");
		
	}

	@Override
	public List<String> getAllRequest(String role) {
		if(role.equals("student")) {
			return studentRepo.getStudentRegRequest();
		}else
			return teacherRepo.getTeacherRegRequest();
	}

	@Override
	public ApiResponse changeStatus(UpdateStatus user) {
		if(user.getRole().equals("student")) {
			Student student = studentRepo.findById(user.getId()).orElseThrow(()-> new ResourceNotFoundException("Credentials Invalid!") );
			student.setStatus(user.getStatus());
			Student persistentStudent =  studentRepo.save(student);
			List<Period> periods = periodRepo.findPeriodsByStdId(persistentStudent.getStandards().getId());
			for (Period period : periods) {
				StudentAttendence transientStudAttendence = new StudentAttendence(period,persistentStudent,"Pending" ); 
				attendenceRepo.save(transientStudAttendence);
			}
			return new ApiResponse(persistentStudent.getFirstName()+"'s Registration Request "+persistentStudent.getStatus());
		} else {
			Teacher teacher = teacherRepo.findById(user.getId()).orElseThrow(()-> new ResourceNotFoundException("Credentials Invalid!") );
			teacher.setStatus(user.getStatus());
			Teacher persistentTeacher =  teacherRepo.save(teacher);
			return new ApiResponse(persistentTeacher.getFirstName()+"'s Registration Request "+persistentTeacher.getStatus());
		}
	}

	@Override
	public List<String> getAllschedule(int stdId) {
		return periodRepo.findByStdId(stdId);
	}

	@Override
	public List<String> getTeacherBySubId(int subId) {
		
		return teacherRepo.getTeacherBySubId(subId);
	}

	@Override
	public ApiResponse addPeriod(AddPeriod period) {
		System.out.println(period.getStartTime().getClass().getName());
		Std std = stdRepo.findById(period.getStdId()).orElseThrow(()-> new ResourceNotFoundException("Credentials Invalid!"));
		Subject subject = subRepo.findById(period.getSubjectId()).orElseThrow(()-> new ResourceNotFoundException("Credentials Invalid!"));
		Teacher teacher = teacherRepo.findById(period.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Credentials Invalid!"));
		Period transientPeriod = new Period(std,period.getDate(),period.getStartTime(),period.getEndTime(),subject,teacher);
		Period persitentPeriod = periodRepo.save(transientPeriod); 
		List<Student> students = studentRepo.findStudentsByStdId(period.getStdId());
		for (Student student : students) {
			StudentAttendence transientStudAttendence = new StudentAttendence(persitentPeriod,student,"Pending" ); 
			attendenceRepo.save(transientStudAttendence);
		}
		return new ApiResponse("Period for "+persitentPeriod.getStd().getStd()+" standard Added Successfully!");
	}

	@Override
	public ApiResponse deletePeriod(int periodId) {
		
		Period period = periodRepo.findById(periodId).orElseThrow(()-> new ResourceNotFoundException("Credentials Invalid!") );
		List<StudentAttendence> studeAttendences = attendenceRepo.findByPeriodId(periodId);
		for (StudentAttendence studeAttendence : studeAttendences) {
			attendenceRepo.delete(studeAttendence);
		}
		periodRepo.delete(period);
		return new ApiResponse("Period for "+period.getStd().getStd()+" standard Deleted Successfully!");
	}

}
