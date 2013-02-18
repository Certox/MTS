package me.minetime.MTS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL {
    public static String user = "";
    public static String pass = "";
    public static String host = "";
    public static String data = "";

    public static Connection connection;

    public static void close() {
	try {
	    if (connection != null) {
		connection.close();
	    }
	} catch (Exception ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public static boolean connect() {
	boolean b = false;
	

	try {
	    // Connect
	    Class.forName("com.mysql.jdbc.Driver");
	    connection = DriverManager.getConnection("jdbc:mysql://" + MTS.MySQL_host + ":3306/" + MTS.MySQL_db,
		    MTS.MySQL_user, MTS.MySQL_pass);

	    // Create Statement and query
	    Statement stmt = connection.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT * FROM `list_users` WHERE `name`='Batschkoto';");

	    while (rs.next()) {
		b = true;
	    }

	    rs.close();
	    stmt.close();
	} catch (Exception ex) {
	    System.out.println(ex.getMessage());
	    b = false;
	}

	return b;
    }

    public static void Update(String qry) {
	try {
	    Statement stmt = connection.createStatement();
	    stmt.executeUpdate(qry);

	    stmt.close();
	} catch (Exception ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public static ResultSet Query(String qry) {
	ResultSet rs = null;

	try {
	    Statement stmt = connection.createStatement();
	    rs = stmt.executeQuery(qry);

	} catch (Exception ex) {
	    System.out.println(ex.getMessage());
	}

	return rs;
    }
}
