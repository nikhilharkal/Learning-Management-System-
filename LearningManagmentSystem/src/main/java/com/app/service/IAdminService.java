package com.app.service;

import java.util.List;

import com.app.dto.AddPeriod;
import com.app.dto.AdminLoginResponse;
import com.app.dto.ApiResponse;
import com.app.dto.UpdateStatus;

public interface IAdminService {
	AdminLoginResponse login(String email, String password, String role);

	List<String> getAllRequest(String role);

	ApiResponse changeStatus(UpdateStatus user);

	List<String> getAllschedule(int stdId);

	List<String> getTeacherBySubId(int subId);

	ApiResponse addPeriod(AddPeriod period);

	ApiResponse deletePeriod(int periodId);
}
