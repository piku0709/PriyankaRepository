package test.spring.domain;

public class Student {
	
	private String schoolYear = "";
	private String studentId = "";
	private String campusId = "";
	private String entryDate = "";
	private String name = "";
	private String grade = "";
	
	private String origEntryDate = "";
	private String origName = "";
	private String origGrade = "";
	
	private boolean newRow = false;
		
	public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getCampusId() {
		return campusId;
	}
	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public boolean isNewRow() {
		return newRow;
	}
	public void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}
	public String getOrigEntryDate() {
		return origEntryDate;
	}
	public void setOrigEntryDate(String origEntryDate) {
		this.origEntryDate = origEntryDate;
	}
	public String getOrigName() {
		return origName;
	}
	public void setOrigName(String origName) {
		this.origName = origName;
	}
	public String getOrigGrade() {
		return origGrade;
	}
	public void setOrigGrade(String origGrade) {
		this.origGrade = origGrade;
	}
	
}
