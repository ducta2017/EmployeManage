package com.nal.dao;

import java.sql.ResultSet;

public interface MySQL {
	void connection();
	String buildQuery(String strQuery);
	ResultSet select();
	void insert();
	void update();
	void delete();
	void disconnect();
}
