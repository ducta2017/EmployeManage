package com.nal.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class Printer {
	
	public int beginRow = 1;
	public int endRow =1;
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
	
	//Draw a record to a row
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
	public void drawTable(ResultSet rs, String[] header, int[] colsWidth, int tblWidth) {
		// Draw header
		clearScreen();
		Printer.line("*", tblWidth+1);
		Printer.drawRow(header, colsWidth);
		Printer.line("*", tblWidth+1);
		// draw body
		
		try {
			
			rs.beforeFirst();
			
			while(rs.next()) {
				int currentRow = rs.getRow();
				if(currentRow > this.beginRow && currentRow <= this.endRow) {
					String[] arr = new String[header.length];
					for (int i=0; i < header.length; i++) {
						arr[i] = rs.getString(header[i]);
					}
					if(!rs.isFirst()) {
						Printer.line("-", tblWidth+1);
					}
					Printer.drawRow(arr, colsWidth);
				}
			}
			line("-", tblWidth+1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// Draw navigator
	public void drawNavigation(int numPage, int currentPage) {
		String nav = "Page: ";
		for(int i = 1; i <=numPage; i++) {
			if(i == currentPage) {
				nav +="["+ i +"] ";
			}else {
				nav += i +" ";
			}
			
		}
		textln(nav);
	}
	
	// clear screen console
	public static void clearScreen() {  
		for (int i = 0; i < 5; ++i) System.out.println();
	}  
}
