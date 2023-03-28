package com.app.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.IAssignmentRepository;
import com.app.dao.IStudentAssignmentRepository;
import com.app.dao.IStudentRepository;
import com.app.dto.ApiResponse;
import com.app.dto.AssignmentRequestDto;
import com.app.dto.StudUploadAssignmentDto;
import com.app.entities.Assignment;
import com.app.entities.Std;
import com.app.entities.Student;
import com.app.entities.StudentAssignment;
import com.app.entities.Subject;
@Service
@Transactional
public class FileHandlingServiceImpl implements IFileHandlingService {

	@Autowired
	private IAssignmentRepository assignmentRepo;
	
	@Autowired
	private IStudentRepository studentRepo;
	
	@Autowired
	private IStudentAssignmentRepository studentAssignmentRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ApiResponse createAssignment( AssignmentRequestDto assignment) {
		Subject subId = new Subject();
		subId.setId(assignment.getSubId());
		Std stdId = new Std();
		stdId.setId(assignment.getStdId());
		int max =  assignmentRepo.findByStdIdAndSubId(assignment.getStdId(),assignment.getSubId());
		//OptionalInt max = asg.stream().mapToInt(Assignment::getAssignmentNo).max();
		//System.out.println(max);
		Assignment transientAssignment = new Assignment(max + 1,assignment.getAssignmentName(), assignment.getGetFileType() ,assignment.getPublishDate(),subId,stdId, assignment.getAssignmentFile());//mapper.map(assignment, Assignment.class);
		//System.out.println(transientAssignment.getStdId());
		Assignment persistentAssignment = assignmentRepo.save(transientAssignment);
		//System.out.println(persistentAssignment.getAssignmentName());
		List<Student> students = studentRepo.findStudentsByStdId(assignment.getStdId());
		for (Student student : students) {
			StudentAssignment transientStudAssign = new StudentAssignment(null, "Pending", student, persistentAssignment); 
			studentAssignmentRepo.save(transientStudAssign);
		}
		
		//System.out.println(persistentAssignment.getPublishDate()+ " "+persistentAssignment.getId());
		return new ApiResponse("Assignment Upload Successfully with Assignment Id:"+persistentAssignment.getId());
		
//		File path = new File(imagePath);
//		if (path.isFile() && path.canRead()) {
//			// => valid path
//			user.setImage(FileUtils.readFileToByteArray(path));
	}

	@Override
	public byte[] studAssignment(int assignmentId) {
		Assignment assign = assignmentRepo.findById(assignmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid AssignmentId"));
		return assign.getAssignmentFile();
	}

	@Override
	public ApiResponse studSubmitAssignment(StudUploadAssignmentDto upload) throws IOException {
		//System.out.println(assignment.getOriginalFilename() + assignment.getSize());
		StudentAssignment stud = studentAssignmentRepo.findByStudIdAndAssignmentId(upload.getStudentId(),upload.getAssignmentId());
		stud.setSubmitFile(upload.getAssignmentFile().getBytes());
		//System.out.println(assignment.getOriginalFilename() +" "+ assignment.getSize());
		stud.setRemarks("Submitted");
		StudentAssignment persistentStud = studentAssignmentRepo.save(stud);
		return new ApiResponse("Assignment for:"+persistentStud.getAssignment().getSubId().getSubjectName()
				+" with Assignment NO:"+ persistentStud.getAssignment().getAssignmentNo()
				+" with tracking Id:"+persistentStud.getId()
				+ " Submitted Successsfully");
	}

	@Override
	public byte[] teacherDownload( int assignmentId) {
		StudentAssignment assign = studentAssignmentRepo.findById(assignmentId).orElseThrow(() -> new ResourceNotFoundException("Invalid AssignmentId"));
				//.orElseThrow(() -> new ResourceNotFoundException("Invalid AssignmentId"));
		return assign.getSubmitFile();
	}

}
