package test.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import test.spring.domain.Student;
import test.spring.model.Challenge;
import test.spring.service.ImportService;
import test.spring.validator.ChallengeValidator;

@Controller
@RequestMapping(value="/challenge")
public class ChallengeController {
	
	@Autowired
	ImportService importService;
	
	@Inject
	private ChallengeValidator challengeValidator;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
	    binder.setValidator(challengeValidator);
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getTestPage(HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		
		Challenge challenge = new Challenge();
		//String test = importService.test();
		
		modelMap.addAttribute("challenge", challenge);		
		
		//hide search window
		modelMap.addAttribute("showSearch", false);
		
		return new ModelAndView("challengeFile", modelMap);
	}
	
	@RequestMapping(method = RequestMethod.POST, params="execute")
	public ModelAndView importFile(@ModelAttribute("challenge") @Valid Challenge challenge, BindingResult result, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		
		if(result.hasErrors()) {
			String errorMessage = getErrorList(result);
			modelMap.addAttribute("errorMessage", errorMessage);
			challenge.setStudents(new ArrayList<Student>());
		} else {
			
			importService.readFromCsv(challenge);
			
			//clear students table before load
			importService.clearStudentInfo();
			
			//load student data into STU_INFO table
			importService.loadStudentInfo(challenge.getStudents());
			challenge.setStudents(new ArrayList<Student>());
			
			//get students info from table
			importService.getStudentsInfo(challenge);
			
			//show search window
			modelMap.addAttribute("showSearch", true);			
		}
		
		modelMap.addAttribute("challenge", challenge);
		return new ModelAndView("challengeFile", modelMap);
	}

	
	@RequestMapping(method = RequestMethod.POST, params="searchStudent")
	public ModelAndView search(@ModelAttribute("challenge") Challenge challenge, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		
		//get student data from STU_INFO table
		importService.searchStudents(challenge);
		
		//show search window
		modelMap.addAttribute("showSearch", true);
				
		modelMap.addAttribute("challenge", challenge);
		return new ModelAndView("challengeFile", modelMap);
	}
	
	@RequestMapping(method = RequestMethod.POST, params="addStudent")
	public ModelAndView addStudent(@ModelAttribute("challenge") Challenge challenge, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		
		//get students list with a new row
		importService.addStudent(challenge);
		
		//show search window
		modelMap.addAttribute("showSearch", true);
				
		modelMap.addAttribute("challenge", challenge);
		return new ModelAndView("challengeFile", modelMap);
	}
	
	@RequestMapping(method = RequestMethod.POST, params="saveStudent")
	public ModelAndView saveStudent(@ModelAttribute("challenge") Challenge challenge, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		
		//get students list with a new row
		importService.saveStudent(challenge);
		
		//show search window
		modelMap.addAttribute("showSearch", true);
		
		String saveMessage = "Save Successful";
		modelMap.addAttribute("saveMessage", saveMessage);
				
		modelMap.addAttribute("challenge", challenge);
		return new ModelAndView("challengeFile", modelMap);
	}
	
	private static String getErrorList(BindingResult errors) {
		String msg = "";
		List<String> errorList = new ArrayList<String>();

		if (errors.hasErrors()) {
			List<ObjectError> data = errors.getAllErrors();
			for (ObjectError row : data) {
				if (!row.getDefaultMessage().equals("") && !errorList.contains(row.getDefaultMessage())) {
					errorList.add(row.getDefaultMessage());
					msg += row.getDefaultMessage() + " </br> ";
				}
			}
		}

		return msg;	// Return a list of screen errors without any duplicate error messages.
	}

}
