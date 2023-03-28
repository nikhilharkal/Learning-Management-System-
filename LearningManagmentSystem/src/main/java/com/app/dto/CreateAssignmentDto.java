package com.app.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssignmentDto {
	private MultipartFile assignmentFile;
	private int stdId;
	private int subId;
}
