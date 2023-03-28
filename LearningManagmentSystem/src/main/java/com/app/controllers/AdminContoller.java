package com.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AddPeriod;
import com.app.dto.UpdateStatus;
import com.app.dto.UserLoginRequest;
import com.app.service.IAdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000/")
public class AdminContoller {
	
	@Autowired
	private IAdminService adminService;
	
	@PostMapping("/login")
	ResponseEntity<?> authenticateAdmin(@RequestBody @Valid UserLoginRequest admin){
		return ResponseEntity.ok(adminService.login(admin.getEmail(), admin.getPassword(), admin.getRole()));
	}
	
	@GetMapping("/registerrequest/{role}")
	ResponseEntity<?> fetchAllRegistrationRequest(@PathVariable String role){
		return ResponseEntity.ok(adminService.getAllRequest(role));
		
	}
	
	@PostMapping("/updatestatus")
	ResponseEntity<?> updateRegisterRequest(@RequestBody @Valid UpdateStatus user){
		return ResponseEntity.ok(adminService.changeStatus(user));
	}
	
	@GetMapping("/getallschedule/{stdId}")
	ResponseEntity<?> getAllSchedule(@PathVariable int stdId){
		return ResponseEntity.ok(adminService.getAllschedule(stdId));
	}
	
	@GetMapping("/getteacherbysubid/{subId}")
	ResponseEntity<?> getTeacherBySubId(@PathVariable int subId){
		return ResponseEntity.ok(adminService.getTeacherBySubId(subId));
	}
	@PostMapping("/addperiod")
	ResponseEntity<?> addPeriod(@RequestBody AddPeriod period){
		return ResponseEntity.ok(adminService.addPeriod(period));
	}
	
	@DeleteMapping("/deleteperiod/{periodId}")
	ResponseEntity<?> deletePeriod( @PathVariable int periodId){
		return ResponseEntity.ok(adminService.deletePeriod(periodId));
	}
	
}
