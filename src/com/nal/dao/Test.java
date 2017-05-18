package com.nal.dao;

import java.sql.ResultSet;

import com.nal.dao.MySQLDao;
import com.nal.common.Printer;

public class Test {
	public static void main(String[] args) {
		MySQLDao db = new MySQLDao();
		db.connection();
		//db.buildQuery("INSERT INTO test VALUES('3', 'ddddddd', 45)");
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
		
		String[] header = {"id", "name", "age"};
		int[]    colsWidth = {10, 50, 20};
		int tblWidth = 80;
		Printer.drawTable(rs, header, colsWidth, tblWidth);
	}
}
