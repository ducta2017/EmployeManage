package com.nal.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.nal.common.Config;
import com.nal.common.Logs;
import com.nal.common.Printer;
import com.nal.ultil.Validate;
import com.nal.dao.MySQLDao;
/**
 * perform add new, update, delete information Employee
 */
public class Employee implements EmployeeInterface{
	private MySQLDao db = null;
	public Employee() {
		this.db = new MySQLDao();
		this.db.connection();
	}
	public void listEmployees() {
		//MySQLDao db = new MySQLDao();
		//db.connection();
		//INSERT INTO employee(name, birthday, gender, address, phone, email) VALUES('testtest','2012-10-12',1,'errerew','3343423','dgggg')
		//db.buildQuery("INSERT INTO test(name, age) VALUES('Nguyen Van A', 20)");
		//db.insert();
		//db.buildQuery("DELETE FROM test WHERE id=2");
		//db.delete();
		this.db.buildQuery("SELECT * FROM employee WHERE status = 1");
		ResultSet rs = this.db.select();
		if(rs == null) 
		{
			System.out.println("Result is null");
			return;
		}
		// initial header of table
		Printer printer = new Printer();
		String[] header = {"id", "name", "birthday", "gender", "address", "phone", "email", "status"};
		int[]    colsWidth = {10, 30, 20, 20, 30, 20, 20, 10};
		int tblWidth = 160;
		int size = 0;
		try {
			// create total pages
			rs.last();
			size = rs.getRow();
			rs.beforeFirst();
			int totalPage = 1;
			int currentPage = 1;
			int oddPage = 0;
			if(size > Config.NUM_PER_PAGE){
				totalPage = (int)(size/Config.NUM_PER_PAGE);
				oddPage = size % Config.NUM_PER_PAGE;
				if( oddPage != 0) {
					totalPage = totalPage + 1;
				}
			// if not page navigator will print all records one page
			}else {
				printer.beginRow = (currentPage -1) * Config.NUM_PER_PAGE;
				printer.endRow   = printer.beginRow + size;
				printer.drawTable(rs, header, colsWidth, tblWidth);
				return;
			}
			// print table with page navigator
			printer.beginRow = (currentPage -1) * Config.NUM_PER_PAGE;
			printer.endRow   = printer.beginRow + Config.NUM_PER_PAGE;
			printer.drawTable(rs, header, colsWidth, tblWidth);
			printer.drawNavigation((int)totalPage, currentPage);
			Logs.logMessage("Select '0' to exit!");
			// Select page
			Scanner keyboard = new Scanner(System.in);
			int key = -1;
			while( key != 0){
				key = keyboard.nextInt();
				currentPage = key;
				if(key >0 && key <= totalPage) {
					
					printer.beginRow = (currentPage -1) * Config.NUM_PER_PAGE;
					if(currentPage == totalPage && oddPage > 0 ) {
						printer.endRow   = printer.beginRow + oddPage;
					}else {
						printer.endRow   = printer.beginRow + Config.NUM_PER_PAGE;
					}
					
					printer.drawTable(rs, header, colsWidth, tblWidth);
					printer.drawNavigation((int)totalPage, currentPage);
					Logs.logMessage("Select '0' to exit!");
				}else if(key != 0) {
					Logs.logMessage("Key invalid");
				}
			}
			
			//keyboard.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// add a employee to table employee
	public void addEmployee() {
		Scanner kbAddEmployee = new Scanner(System.in);
		Validate validate = new Validate();
		String input = "y";
		while(input == "y") {
			// Input name
			Scanner scannerInput = new Scanner(System.in);
			// Initial input value
			String name = "";
			String birthday = "";
			int gender = -1;
			String address = "";
			String phone = "";
			String email = "";
			int status = -1;
			
			while(name == "") {
				Logs.logMessage("Please input Name( 6~50 characters): ");
				name = scannerInput.nextLine();
				if(validate.validateName(name) ) {
					break;
				}else {
					Logs.logMessage("Invalid name!");
					name = ""; continue;
				}
			}
			
			// input birthday
			while(birthday == "") {
				Logs.logMessage("Please input Birthday( format dd/mm/yyyy): ");
				birthday = scannerInput.next();
				if(validate.validateBirthday(birthday) ) {
					break;
				}else {
					Logs.logMessage("Invalid Birthday!");
					birthday = ""; continue;
				}
			}
			// input gender
			while(gender == -1) {
				Logs.logMessage("Please input Gender( 0, 1 or  2): ");
				gender = scannerInput.nextInt();
				if(validate.validateGender(gender) ) {
					break;
				}else {
					Logs.logMessage("Invalid Gender!");
					gender = -1 ; continue;
				}
			}
			// input Address
			Logs.logMessage("Please input Address: ");
			address = scannerInput.next();
			
			// input Phone
			while(phone == "") {
				Logs.logMessage("Please input Phone( Phone of Vietnan): ");
				phone = scannerInput.next();
				if(validate.validatePhone(phone) ) {
					break;
				}else {
					Logs.logMessage("Invalid Phone!");
					phone = ""; continue;
				}
			}
			// input Email
			while(email == "") {
				Logs.logMessage("Please input Email: ");
				email = scannerInput.next();
				if(validate.validateEmail(email) ) {
					break;
				}else {
					Logs.logMessage("Invalid Email!");
					email = ""; continue;
				}
			}
			// input Status(default 1)
			while(status == -1) {
				Logs.logMessage("Please input Status( 0 or 1 - default 1): ");
				status = scannerInput.nextInt();
				if(validate.validateStatus(status) ) {
					break;
				}else {
					Logs.logMessage("Invalid Gender!");
					status = -1; continue;
				}
			}

			String strQuery = "INSERT INTO employee(name, birthday, gender, address, phone, email) VALUES('"+name+"','"+ birthday +"',"+gender+",'"+address+"','"+ phone +"','"+ email +"')";
			this.db.buildQuery(strQuery);
			this.db.insert();
			
			Logs.logMessage("Continue add new Employee(y or n)?");
			input = kbAddEmployee.next();
		}
		//kbAddEmployee.close();

	}
	// Edit a Employee
	public void editEmployee() {
		Scanner scannerInput = new Scanner(System.in);
		Validate validate = new Validate();
		Logs.logMessage("Please input id of record which you want to edit: ");
		String strId = scannerInput.next();
		int id = Integer.parseInt(strId);
		this.db.buildQuery("SELECT * FROM employee WHERE status = 1 AND id="+ id);
		ResultSet rs = this.db.select();
		try {
			
			if(!rs.next()){
				Logs.logMessage("Not found record with id "+ id);
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Initial input value
		String name = "";
		String birthday = "";
		int gender = -1;
		String address = "";
		String phone = "";
		String email = "";
		int status = -1;
		
		while(name == "") {
			Logs.logMessage("Please input Name( 6~50 characters): ");
			name = scannerInput.nextLine();
			if(validate.validateName(name) ) {
				break;
			}else {
				Logs.logMessage("Invalid name!");
				name = ""; continue;
			}
		}
		
		// input birthday
		while(birthday == "") {
			Logs.logMessage("Please input Birthday( format dd/mm/yyyy): ");
			birthday = scannerInput.next();
			if(validate.validateBirthday(birthday) ) {
				break;
			}else {
				Logs.logMessage("Invalid Birthday!");
				birthday = ""; continue;
			}
		}
		// input gender
		while(gender == -1) {
			Logs.logMessage("Please input Gender( 0, 1 or  2): ");
			gender = scannerInput.nextInt();
			if(validate.validateGender(gender) ) {
				break;
			}else {
				Logs.logMessage("Invalid Gender!");
				gender = -1 ; continue;
			}
		}
		// input Address
		Logs.logMessage("Please input Address: ");
		address = scannerInput.next();
		
		// input Phone
		while(phone == "") {
			Logs.logMessage("Please input Phone( Phone of Vietnan): ");
			phone = scannerInput.next();
			if(validate.validatePhone(phone) ) {
				break;
			}else {
				Logs.logMessage("Invalid Phone!");
				phone = ""; continue;
			}
		}
		// input Email
		while(email == "") {
			Logs.logMessage("Please input Email: ");
			email = scannerInput.next();
			if(validate.validateEmail(email) ) {
				break;
			}else {
				Logs.logMessage("Invalid Email!");
				email = ""; continue;
			}
		}
		// input Status(default 1)
		while(status == -1) {
			Logs.logMessage("Please input Status( 0 or 1 - default 1): ");
			status = scannerInput.nextInt();
			if(validate.validateStatus(status) ) {
				break;
			}else {
				Logs.logMessage("Invalid Gender!");
				status = -1; continue;
			}
		}
		
		String strQuery = "UPDATE employee SET name='"+ name +"',"
				+ "birthday='"+birthday+"',"
				+ "gender="+gender+","
				+ "address='"+address+"',"
				+ "phone='"+phone+"',"
				+ "email='"+email+"',"
				+ "status="+status;
		strQuery += " WHERE id="+id;
		this.db.buildQuery(strQuery);
		this.db.insert();
		
	}
	
	// Delete a Employee
	public void deleteEmployee() {
		Scanner scannerInput = new Scanner(System.in);
		Logs.logMessage("Please input id of record which you want to delete: ");
		int id = scannerInput.nextInt();
		String strQuery = "UPDATE employee SET status=0 WHERE id="+ id;
		this.db.buildQuery(strQuery);
		this.db.update();
		Logs.logMessage("Deleted the row with id: "+ id);
		
	}
	// Employee get set entity

}
