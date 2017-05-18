package com.nal.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class Printer {
	
	public static void line(String s, int n) {
		System.out.println(String.join("", Collections.nCopies(n, s)));
	}
	
	public static void text(String s) {
		System.out.print(s);
	}
	
	public static void text(int s) {
		System.out.print(String.valueOf(s));
	}
	
	public static void textln(String s) {
		System.out.println(s);
	}
	
	public static void textln(int s) {
		System.out.println(String.valueOf(s));
	}
	
	//Draw a record to a arow
	public static void drawRow(String[] header, int[] colsWidth) {
		String headerString = "";
		for (int i=0; i < header.length; i++) {
			headerString += "|";
			int len = header[i].length();
			int width = colsWidth[i]-1 - len;
			int beginWidth = (int)(width/2);
			int endWidth = width - beginWidth;
			headerString += String.join("", Collections.nCopies(beginWidth, " "));
			headerString += header[i];
			headerString += String.join("", Collections.nCopies(endWidth, " "));
			if(i == header.length -1) {
				headerString += "|";
			}
		}
		textln(headerString);
	}
	
	// Draw table when get data from a table
	public static void drawTable(ResultSet rs, String[] header, int[] colsWidth, int tblWidth) {
		// Draw header
		line("*", tblWidth+1);
		drawRow(header, colsWidth);
		line("*", tblWidth+1);
		// draw body
		try {
			while(rs.next()) {
				String[] arr = new String[header.length];
				for (int i=0; i < header.length; i++) {
					arr[i] = rs.getString(header[i]);
				}
				if(!rs.isFirst()) {
					line("-", tblWidth+1);
				}
				drawRow(arr, colsWidth);
			}
			line("-", tblWidth+1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
