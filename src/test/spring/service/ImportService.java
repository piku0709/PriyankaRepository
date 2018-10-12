package test.spring.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.spring.dao.ChallengeDao;
import test.spring.domain.Student;
import test.spring.model.Challenge;

@Service
public class ImportService {
	
	@Autowired
	ChallengeDao challengeDao;
	
	public String test() {
		String test = "hello "+ challengeDao.getStudentName();
		System.out.println("test");
		return test;
	}
	
	public void readFromCsv(Challenge challenge) {
		
		List<Student> studentsInfo = new ArrayList<Student>();
		
		try {
			//create an instance of BufferedReader
			//get file's input stream
			BufferedReader bReader = new BufferedReader(new InputStreamReader(challenge.getFile().getInputStream()));
			
			//read the first line from text file...
			String currentLine = "";
			
			//loop until all lines are read
			while((currentLine = bReader.readLine()) != null) {
				
				//skip when line contains column names
				if(!currentLine.contains("SchoolYr")) {

					//use string split method to load a string array with values 
					//from each line separated by a comma in given csv file
					String[] info = currentLine.split(","); 
					
					Student student = createStudentInfo(info);
					
					studentsInfo.add(student);
				}
				else {
					continue;
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		challenge.setStudents(studentsInfo);
		
	}
	
	public void loadStudentInfo(List<Student> students) {
		for(Student student : students) {
			challengeDao.saveStudentData(student);
		}
	}
	
	public void getStudentsInfo(Challenge challenge) {
		List<Student> studentsInfo = challengeDao.getStudentsInfo();
		challenge.setStudents(studentsInfo);
	}
	
	public void clearStudentInfo() {
		challengeDao.clearDtudentsInfo();
	}
	
	public void searchStudents(Challenge challenge) {
		List<Student> studentsInfo = new ArrayList<Student>();
		String search = challenge.getSearchText();
		
		if(search != null && search.trim().length() != 0) {
			studentsInfo = challengeDao.getStudentsInfo(search);
		} else {
			studentsInfo = challengeDao.getStudentsInfo();
		}
		
		challenge.setStudents(studentsInfo);
	}
	
	public void addStudent(Challenge challenge) {
		List<Student> studentsInfo = challenge.getStudents();
		
		Student student = new Student();
		student.setNewRow(true);
		studentsInfo.add(student);
		
		challenge.setStudents(studentsInfo);
	}
	
	public void saveStudent(Challenge challenge) {
		List<Student> studentsInfo = challenge.getStudents();
	
		for(Student student : studentsInfo) {
			//insert new students records
			if(student.isNewRow()) {
				challengeDao.saveStudentData(student);
			} else {
				//update the changes in student data
				if (dataHasChanged(student)) {
					challengeDao.updateStudentData(student);
				}
			}
		}
		
		
		//get students from table
		getStudentsInfo(challenge);
	}
	
	private Student createStudentInfo(String[] info) {
		String schoolYear = info[0];
		String campusId = info[1];
		String studentId = info[2];
		String entryDate = info[3];
		String grade = info[4];
		String name = info[5];
		
		Student student = new Student();
		student.setSchoolYear(schoolYear);
		student.setCampusId(campusId);
		student.setStudentId(studentId);
		student.setEntryDate(entryDate);
		student.setGrade(grade);
		student.setName(name);
		
		return student;
	}
	
	private boolean dataHasChanged(Student student) {
		
		if(!student.getName().equalsIgnoreCase(student.getOrigName())) {
			return true;
		}
		
		if(!student.getEntryDate().equals(student.getOrigEntryDate())) {
			return true;
		}
		
		if(!student.getGrade().equals(student.getGrade())) {
			return true;
		}		
		
		return false;
	}

}
