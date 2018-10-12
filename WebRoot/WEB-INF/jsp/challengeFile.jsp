<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
  <head>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/challenge.js"></script>
	
	<style>
		table {
		    border-collapse: collapse
		}
		
		td, th {
		    padding: 8px;
		}
		
		tr:nth-child(even) {
		    background-color: #DDDDE9;
		}
		
		tbody{
		  display:block;
		  overflow:auto;
		  height:650px;
		}
		
		thead tr{
		  display:block;
		  background-color: #bbbbe6;
		}
		
		header {
		    background-color: #a2a293;
		    padding: 10px;
		    text-align: center;
		    font-size: 20px;
		}
		
		footer {
		    background-color: #a2a293;
		    padding: 10px;
		    text-align: center;
		}
		
	</style>
	
	
  </head>
  
  <body>
  
  	<header>
  	 <b>Coding Challenge</b>
  	</header>
    
    
    <form:form id="challengeForm" method="POST" commandName="challenge" enctype="multipart/form-data">
    
    	<div style="margin-top: 20px; margin-left:42px">
    		<div style="color:red; font-weight:bold;">${errorMessage}</div>
			<div style="color:green; font-weight:bold;">${saveMessage}</div>
		</div>
		
    	<div style="margin-top: 20px; padding-left: 20px;">
	    	<form:label path="file" style="margin-left: 20px; margin-right: 21px;">Import Path:</form:label>
			<form:input id="importFile" type="text" path="importFile" style="width:230px; margin-right: 2px;"/>
			
			<form:input id="file" path="file" type="file"  style="display: none;"/>
			<button id="browseButton" type="button" style="margin-right: 2px;">Browse</button>
			
			<form:button id="execute" name="execute" disabled="true" style="margin-right: 2px;">
				Import
			</form:button>			
		</div>
		
		<div style="clear: both; margin-top: 10px; padding-left: 40px; height: 780px;">
		
		<c:if test="${showSearch}">
			<div style="padding-bottom:10px;">
				<form:label path="searchText">Search Student:</form:label>
				<form:input id="searchText" type="text" path="searchText" style="width:230px; margin-right: 2px;"/>
				
				<form:button id="searchStudent" name="searchStudent">
					Search
				</form:button>
			</div>	
		</c:if>
			
		<c:if test="${fn:length(challenge.students) > 0}">					
		
			<form:button id="AddButton" name="addStudent" style="display: none;">
				Add
			</form:button>
			
			<form:button id="SaveButton" name="saveStudent" style="display: none;">
				Save
			</form:button>
				
			<table id="table1">
				<thead>
					<!-- Grid Table Header -->
					<tr>
						<th style="text-align: center; width: 85px;">School Year</th>					
						<th style="text-align: center; width: 85px;">Campus</th>					
						<th style="text-align: center; width: 85px;">Student ID</th>					
						<th style="text-align: left; width: 180px;">Student Name</th>					
						<th style="text-align: center; width: 180px;">Entry Date</th>					
						<th style="text-align: center; width: 178px;">Grade</th>					
					</tr>
				</thead>
				<tbody>
				<c:forEach var="dataRow" items="${challenge.students}" varStatus="status">				
										
					<!-- Grid Table Data Rows -->
					<tr>						
						<!-- school year -->
						<td style="text-align: center; width: 85px;">
							<c:choose>
								<c:when test="${challenge.students[status.index].newRow}">
									<form:input type="text" path="students[${status.index}].schoolYear" style="width: 55px;" maxLength="4"/>
								</c:when>
								<c:otherwise>
									<span>${challenge.students[status.index].schoolYear}</span>
									<form:hidden path="students[${status.index}].schoolYear"/>
								</c:otherwise>
							</c:choose>
						</td>
						
						<!-- campus Column -->
						<td style="text-align: center; width: 85px;">
							<c:choose>
								<c:when test="${challenge.students[status.index].newRow}">
									<form:input type="text" path="students[${status.index}].campusId" style="width: 55px;" maxLength="3"/>
								</c:when>
								<c:otherwise>
									<span>${challenge.students[status.index].campusId}</span>
									<form:hidden path="students[${status.index}].campusId"/>
								</c:otherwise>
							</c:choose>
						</td>
						
						<!-- student id-->
						<td style="text-align: center; width: 85px;">
							<c:choose>
								<c:when test="${challenge.students[status.index].newRow}">
									<form:input type="text" path="students[${status.index}].studentId" style="width: 75px;" maxLength="6"/>
								</c:when>
								<c:otherwise>
									<span>${challenge.students[status.index].studentId}</span>
									<form:hidden path="students[${status.index}].studentId"/>
								</c:otherwise>
							</c:choose>
						</td>
						
						<!-- student name Column -->
						<td style="text-align: left; width: 180px;">
							<form:input type="text" path="students[${status.index}].name" maxLength="45"/>
							<form:hidden path="students[${status.index}].origName"/>
						</td>
						
						<!-- Entry date -->
						<td style="text-align: center; width: 180px;">
							<form:input type="text" path="students[${status.index}].entryDate" style="text-align: center; width: 85px;" maxLength="10"/>
							<form:hidden path="students[${status.index}].origEntryDate"/>
						</td>
											
						<!-- Grade Column -->
						<td style="text-align: center; width: 180px;">
							<form:input type="text" path="students[${status.index}].grade" style="text-align: center; width: 55px;" maxLength="2"/>
							<form:hidden path="students[${status.index}].origGrade"/>
						</td>
						
						<form:hidden path="students[${status.index}].newRow"/>
					</tr>
				</c:forEach>

				</tbody>
			</table>
			
			<div>
				<a href="javascript:;" id="addLink" onclick="$('#AddButton').click();">Add</a>
				
				<a href="javascript:;" id="saveLink" onclick="$('#SaveButton').click();">Save</a>
			</div>						
	
		</c:if>
		</div>
		
    </form:form>
    
    <footer>
    	<b>By Priyanka Srivastava</b>
    </footer>
   
 </body>
</html>