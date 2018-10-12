package test.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import test.spring.domain.Student;

@Repository
public class ChallengeDao{

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
	
	public String getStudentName() {
		String studentName = "";
		
		String sql = "Select stu_name from stu_info where stu_id = '025676' and sch_yr = '2018'";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		try {
			studentName = namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
		}
		catch(EmptyResultDataAccessException e) {
			System.out.println("No rows found in STU_INFO");
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("Program error occurred while reading tudent name from table...");
			e.printStackTrace();
		}
		
		return studentName;
	}
	
	public void saveStudentData(Student student) {
		String sql = "INSERT INTO STU_INFO (SCH_YR, CAMPUS_ID, STU_ID, STU_NAME, ENTRY_DT, GRADE) " +
					 "VALUES(:schoolYear, :campus, :studentId, :name, :entryDate, :grade) ";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("schoolYear", student.getSchoolYear());
		params.put("campus", student.getCampusId());
		params.put("studentId", student.getStudentId());
		params.put("name", student.getName());
		params.put("entryDate", student.getEntryDate());
		params.put("grade", student.getGrade());
		
		try {
			namedParameterJdbcTemplate.update(sql, params);
		}
		catch(Exception e) {
			System.out.println("Program error occurred while loading data into table...");
			e.printStackTrace();
		}		
	}
	
	public void updateStudentData(Student student) {
		String sql = "UPDATE STU_INFO SET STU_NAME = :name, ENTRY_DT = :entryDate, GRADE = :grade " +
					 "WHERE SCH_YR = :schoolYear AND CAMPUS_ID =  :campus AND STU_ID = :studentId ";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("schoolYear", student.getSchoolYear());
		params.put("campus", student.getCampusId());
		params.put("studentId", student.getStudentId());
		params.put("name", student.getName());
		params.put("entryDate", student.getEntryDate());
		params.put("grade", student.getGrade());
		
		try {
			namedParameterJdbcTemplate.update(sql, params);
		}
		catch(Exception e) {
			System.out.println("Program error occurred while updating data into table...");
			e.printStackTrace();
		}
		
	}
	
	public List<Student> getStudentsInfo() {
		List<Student> students = new ArrayList<Student>();
		
		String sql = "SELECT SCH_YR, CAMPUS_ID, STU_ID, STU_NAME, ENTRY_DT, GRADE FROM STU_INFO ORDER BY STU_NAME ASC";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		try {
			students = namedParameterJdbcTemplate.query(sql, params, new StudentInfoMapper());
		}
		catch(Exception e) {
			System.out.println("Program error occurred while getting data from table...");
			e.printStackTrace();
		}		
		
		return students;
	}
	
	
	public List<Student> getStudentsInfo(String search) {
		List<Student> students = new ArrayList<Student>();
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("SELECT SCH_YR, CAMPUS_ID, STU_ID, STU_NAME, ENTRY_DT, GRADE FROM STU_INFO WHERE ");
		
		String searchCriteria = "";
		
		String[] searchArray = search.split(",");
		boolean isNumeric  = false;
		
		if(searchArray.length > 1) {
			try {
				Integer.parseInt(searchArray[1]);
				isNumeric = true;
			} catch (NumberFormatException nfe) {
				isNumeric = false;
			}
		}
		
		//search by gradeLevel starting with given search
		if(isNumeric) {
			
			searchCriteria = "%" + searchArray[1].trim() + "%";
			sql.append(" GRADE LIKE '"+searchCriteria+"' AND ");			
			
		}
		
		//search by student name by default 
		searchCriteria = searchArray[0].trim() + "%";
		sql.append(" STU_NAME LIKE '"+searchCriteria+"' ");
		
		
		sql.append("ORDER BY STU_NAME ASC");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			students = namedParameterJdbcTemplate.query(sql.toString(), params, new StudentInfoMapper());
		}
		catch(Exception e) {
			System.out.println("Program error occurred while searching data from table...");
			e.printStackTrace();
		}	
		
		return students;
	}
	
	public void clearDtudentsInfo() {
		String sql = "DELETE FROM STU_INFO";
		namedParameterJdbcTemplate.update(sql, new HashMap<String, Object>());
	}

	static class StudentInfoMapper implements ParameterizedRowMapper<Student> {

		@Override
		public Student mapRow(ResultSet rs, int arg1) throws SQLException {
			Student student = new Student();
			student.setSchoolYear(rs.getString("SCH_YR"));
			student.setCampusId(rs.getString("CAMPUS_ID"));
			student.setStudentId(rs.getString("STU_ID"));
			student.setName(rs.getString("STU_NAME"));
			student.setEntryDate(rs.getString("ENTRY_DT"));
			student.setGrade(rs.getString("GRADE"));
			return student;
		}
		
	}
}
