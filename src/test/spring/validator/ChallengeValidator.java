package test.spring.validator;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import test.spring.model.Challenge;

@Component
public class ChallengeValidator implements Validator{

	@Override
	public boolean supports(Class<?> object) {
		return Challenge.class.equals(object);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Challenge challenge = (Challenge) object;
		
		//validate import file	
		if(challenge.getImportFile() == null || challenge.getImportFile().trim().length() == 0) {
			errors.rejectValue("importFile", "", "File is a required field.");
			return;
		} else if(challenge.getFile().getSize() <= 0) {
			errors.rejectValue("importFile", "", "Please select a file to import.");
			return;
		} else {
        	try {
				@SuppressWarnings("unused")
				InputStream fileStream = challenge.getFile().getInputStream();
			} catch (IOException e) {
				errors.rejectValue("importFile", "", "File could not be opened. Please make another selection.");
				e.printStackTrace();
			}
		}
	}

}
