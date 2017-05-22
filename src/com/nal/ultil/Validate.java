package com.nal.ultil;

/**
 * Validation input of employee
 */
public class Validate {
	
	// Validation name
	public boolean validateName(String name) {
		if(name.length() < 6 || name.length() > 50) {
			return false;
		}
		
		return true;
	}
	
	// Validation birthday
	public boolean validateBirthday(String birthday){
		return true;
	}
	
	// Validation gender
	public boolean validateGender(int gender) {
		return true;
	}
	
	// Validation Phone
	public boolean validatePhone( String phone) {
		return true;
	}
	
	// Validation Email 
	public boolean validateEmail(String email) {
		return true;
	}
	
	// Validation Status 
	public boolean validateStatus(int status) {
		return true;
	}
}
