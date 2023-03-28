package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_attendence")
public class StudentAttendence extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "period_id")
	private Period periodId;
	
	@OneToOne
	@JoinColumn(name = "student_id")
	private Student studentId;
	
	@Column(length = 10)
	private String attendednce; 
}
