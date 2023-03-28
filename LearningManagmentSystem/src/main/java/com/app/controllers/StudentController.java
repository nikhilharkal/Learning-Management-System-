package com.app.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.StudUploadAssignmentDto;
import com.app.dto.StudentRegisterRequest;
import com.app.dto.SubAssignmentRequest;
import com.app.dto.UserLoginRequest;
import com.app.service.IFileHandlingService;
import com.app.service.IStudentService;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController {
	
	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private IFileHandlingService fileService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateStudent(@RequestBody @Valid UserLoginRequest user){
		System.out.println(user.toString());
		return  ResponseEntity.ok(studentService.login(user.getEmail(), user.getPassword(), user.getRole()));
		// return new ResponseEntity<>(studentService.login(user.getEmail(), user.getPassword(), user.getRole()),HttpStatus.OK);
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerStudent(@RequestBody @Valid StudentRegisterRequest student ){
		return ResponseEntity.ok(studentService.registerNewStudent(student));
		
	}
	
	@GetMapping("/timetable/{stdId}")
	public ResponseEntity<?> getPeriodsByStdAndDate(@PathVariable int stdId){
		return ResponseEntity.ok(studentService.getPeriodsDetailsByStdAndDate(stdId));
		
	}
	@GetMapping("/{stdId}")
	public ResponseEntity<?> getWeeklyScheduleByStd( @PathVariable int stdId){
		return ResponseEntity.ok(studentService.getWeeklyScheduleDetailsByStd(stdId));
		
	}
	@PostMapping("/subassignment")
	public ResponseEntity<?> getAssignmentByStdAndSub(@RequestBody @Valid SubAssignmentRequest subAssignment ){
		return ResponseEntity.ok(studentService.getAssignment(subAssignment.getStdId(),subAssignment.getSubId(),subAssignment.getStudentId()));
		
	}
	
	@GetMapping(value = "/sdownload/{assignmentId}")
	public ResponseEntity<?> studDownloadAssignment(@PathVariable int assignmentId){
		return ResponseEntity.ok(fileService.studAssignment(assignmentId));
	}
	
	@PutMapping("/studSubmitAssignment")
	public ResponseEntity<?> studUploadAssignment(@ModelAttribute StudUploadAssignmentDto upload) throws IOException{
		return ResponseEntity.ok(fileService.studSubmitAssignment(upload));
	}
	
	@GetMapping("/attendence/{studentId}/{subjectId}")
	public ResponseEntity<?> getAttendeceBySubject( @PathVariable int studentId, @PathVariable int subjectId){
		return ResponseEntity.ok(studentService.getAttendeceBySubject(studentId,subjectId));
		
	}
}
