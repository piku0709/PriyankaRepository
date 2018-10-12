package test.spring.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import test.spring.domain.Student;

public class Challenge {
	
	private String importFile = "";
	private MultipartFile file;
	private String searchText = "";
	private List<Student> students = new ArrayList<Student>();
	
	public String getImportFile() {
		return importFile;
	}
	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
}
