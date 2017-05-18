package com.nal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import com.nal.common.Logs;
import com.nal.common.Config;

public class MySQLDao implements MySQL {

	private Connection conn = null;
	private String sqlQuery = "";

	/*
	 * Connection to server MySQL
	 */
	public void connection() {
		Logs.logMessage("Connecting to database...");
		try {
			Class.forName(Config.CLASS_JDBC).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			this.conn = DriverManager.getConnection(Config.URL + Config.DB_NAME, Config.USER_NAME, Config.PASSWORD);
			Logs.logMessage("Connection to DB "+ Config.DB_NAME +" successful!");
		} catch (SQLException ex) {
			// handle any errors
			Logs.logError("SQLException: " + ex.getMessage());
			Logs.logError("SQLState: " + ex.getSQLState());
			Logs.logError("VendorError: " + ex.getErrorCode());
		}
	}

	/*
	 * Create a string sql query
	 */
	public String buildQuery(String strQuery) {
		this.sqlQuery = strQuery;
		return this.sqlQuery;
	}

	/*
	 * create statement object 
	 */
	private Statement statement() throws SQLException {
		Statement stmt = null;
		stmt =  this.conn.createStatement();
		
		return stmt;
    }
	/**
	 * Select records from table with string SQL which is build by buildQuery
	 */
	public ResultSet select() {
		Logs.logMessage("Select records with SQL string: " + this.sqlQuery);
		ResultSet rs = null;
		try {
			rs = this.statement().executeQuery(this.sqlQuery);
		} catch (SQLException ex) {
			// handle any errors
			Logs.logError("SQLException: " + ex.getMessage());
			Logs.logError("SQLState: " + ex.getSQLState());
			Logs.logError("VendorError: " + ex.getErrorCode());
		}

		return rs;
	}
	/**
	 * execute a SQL string by status update
	 */
	private void executeUpdate() {
		try {
			this.statement().executeUpdate(this.sqlQuery);
		} catch (SQLException ex) {
			// handle any errors
			Logs.logError("SQLException: " + ex.getMessage());
			Logs.logError("SQLState: " + ex.getSQLState());
			Logs.logError("VendorError: " + ex.getErrorCode());
		}
	}
	/*
	 * Inser a row to table
	 */
	public void insert() {
		Logs.logMessage("*Insert a record*");
		this.executeUpdate();
	}

	public void update() {
		Logs.logMessage("*Update a record*");
		this.executeUpdate();

	}

	public void delete() {
		Logs.logMessage("Delete a record");
		this.executeUpdate();

	}

	public void disconnect() {
		try {
			this.statement().close();
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
