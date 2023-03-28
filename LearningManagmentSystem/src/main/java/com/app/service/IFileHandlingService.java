package com.app.service;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.dto.AssignmentRequestDto;
import com.app.dto.StudUploadAssignmentDto;
import com.app.entities.Assignment;

public interface IFileHandlingService {

	ApiResponse createAssignment(@Valid AssignmentRequestDto assignment);

	byte[] studAssignment(int assignmentId);

	ApiResponse studSubmitAssignment(StudUploadAssignmentDto upload) throws IOException;

	byte[] teacherDownload(int assignmentId);
}
