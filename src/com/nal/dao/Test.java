package com.nal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.nal.dao.MySQLDao;
import com.nal.common.Config;
import com.nal.common.Logs;
import com.nal.common.Printer;


public class Test {
	public int currentPage =1;
	public static void main(String[] args) {
		MySQLDao db = new MySQLDao();
		db.connection();
		//INSERT INTO employee(name, birthday, gender, address, phone, email) VALUES('testtest','2012-10-12',1,'errerew','3343423','dgggg')
		//db.buildQuery("INSERT INTO test(name, age) VALUES('Nguyen Van A', 20)");
		//db.insert();
		//db.buildQuery("DELETE FROM test WHERE id=2");
		//db.delete();
		db.buildQuery("SELECT * FROM test");
		ResultSet rs = db.select();
		if(rs == null) 
		{
			System.out.println("Result is null");
			return;
		}
		Printer printer = new Printer();
		String[] header = {"id", "name", "age"};
		int[]    colsWidth = {10, 50, 20};
		int tblWidth = 80;
		int size = 0;
		try {
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
			}else {
				printer.beginRow = (currentPage -1) * Config.NUM_PER_PAGE;
				printer.endRow   = printer.beginRow + size;
				printer.drawTable(rs, header, colsWidth, tblWidth);
				return;
			}
			printer.beginRow = (currentPage -1) * Config.NUM_PER_PAGE;
			printer.endRow   = printer.beginRow + Config.NUM_PER_PAGE;
			printer.drawTable(rs, header, colsWidth, tblWidth);
			printer.drawNavigation((int)totalPage, currentPage);
			
			Scanner keyboard = new Scanner(System.in);
			int key = 0;
			while( key != 27){
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
				}else if(key != 27) {
					Logs.logMessage("Key invalid");
				}
			}
			
			keyboard.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
