package com.app.controllers;

import java.io.IOException;
import java.time.LocalDate;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.dto.AssignmentRequestDto;
import com.app.dto.CreateAssignmentDto;
import com.app.dto.StudentStatus;
import com.app.dto.TeacherRegisterRequest;
import com.app.dto.TimeTableRequestByTeacher;
import com.app.dto.UserLoginRequest;
import com.app.service.IFileHandlingService;
import com.app.service.ITeacherService;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:3000/")
public class TeacherController {
	@Autowired
	private ITeacherService teacherService;
	
	@Autowired
	private IFileHandlingService fileService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateTeacher(@RequestBody @Valid UserLoginRequest user){
		return ResponseEntity.ok(teacherService.login(user.getEmail(), user.getPassword(), user.getRole()));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerTeacher(@RequestBody @Valid TeacherRegisterRequest teacher){
		return ResponseEntity.ok(teacherService.registerNewTeacher(teacher));
	}
	
	@GetMapping("/timetable/{teacherId}")
	public ResponseEntity<?> getTimeTableByTeacherIdAndDate( @PathVariable int teacherId){
		return ResponseEntity.ok(teacherService.getTimeTable(teacherId));
	}
	@GetMapping("/{teacherId}")
	public ResponseEntity<?> getWeeklyScheduleByStd( @PathVariable int teacherId){
		return ResponseEntity.ok(teacherService.getWeeklyScheduleDetailsByTeacherId(teacherId));
		
	}
	
	@PostMapping("/createassignment") 
	public ResponseEntity<?> createAssignment(@ModelAttribute CreateAssignmentDto assignment ) throws IOException{ 
		//System.out.println(assignment.getOriginalFilename()+ stdId + subId);
		AssignmentRequestDto dto = new AssignmentRequestDto();
		dto.setAssignmentName(assignment.getAssignmentFile().getOriginalFilename());
		dto.setAssignmentFile(assignment.getAssignmentFile());
		dto.setGetFileType(assignment.getAssignmentFile());
		dto.setDate(LocalDate.now());
		dto.setStd(assignment.getStdId());
		dto.setSubject(assignment.getSubId());
	//	ApiResponse res = fileservice.createAssignment(dto);
	//	System.out.println(res.getMessage());
		return ResponseEntity.ok(fileService.createAssignment(dto));
	  
	  }
	
	@GetMapping("/tdownload/{assignmentId}")
	public ResponseEntity<?> tDownloadAssignment( @PathVariable int assignmentId){
		return ResponseEntity.ok(fileService.teacherDownload(assignmentId));
	}
	
	
	@GetMapping("/allschedule/{teacherId}")
	public ResponseEntity<?> getAllSchedule( @PathVariable int teacherId){
		return ResponseEntity.ok(teacherService.getAllSchedule(teacherId));
		
	}
	@GetMapping("/studentattendencelist/{periodId}")
	public ResponseEntity<?> studentAttendenceList(@PathVariable int periodId ){
		return ResponseEntity.ok(teacherService.studentAttendenceList(periodId));
		
	}
	
	@PutMapping("/updateattendence")
	ResponseEntity<?> updateAttendence(@RequestBody StudentStatus status){
		return ResponseEntity.ok(teacherService.updateAttendence(status));
	}
	
	@GetMapping("/getstdid/{teacherId}")
	public ResponseEntity<?> getStdByTeacherId(@PathVariable int teacherId ){
		return ResponseEntity.ok(teacherService.getStdByTeacherId(teacherId));
		
	}
	@GetMapping("/studentlist/{stdId}/{subId}")
	public ResponseEntity<?> getAllStudentByStd(@PathVariable int stdId,@PathVariable int subId){
		return ResponseEntity.ok(teacherService.getallStudentByStd(stdId,subId));
	}
	@PutMapping("/assignstatus")
	public ResponseEntity<?> updateAssignStatus(@RequestBody StudentStatus status){
		return ResponseEntity.ok(teacherService.updateAssigmentStatus(status));
	}
}
