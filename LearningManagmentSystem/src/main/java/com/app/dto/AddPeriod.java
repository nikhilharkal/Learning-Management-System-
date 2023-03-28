package com.app.dto;

import java.sql.Time;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AddPeriod {
	//LocalDate date = LocalDate.parse("2018-05-05");
	@JsonFormat(pattern ="yyyy-MM-dd" )
	private LocalDate date ;
	@JsonFormat(pattern =" HH:mm:ss" )
	private Time startTime;
	@JsonFormat(pattern =" HH:mm:ss" )
	private Time endTime;
	private int stdId;
	private int subjectId;
	private int teacherId;
	
}
