package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;

public class DatabaseConnection 
{
	private DatabaseConnection()
	{		
		final String DatabaseName = "blood_bank";
		final String User = "root", Pass = "sdaproject";
		final String url = "jdbc:mysql://localhost/" + DatabaseName;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			DatabaseLink = DriverManager.getConnection(url, User, Pass);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private DatabaseConnection(DatabaseConnection db) {}
	
	
	public static Connection DatabaseLink = null;
	
	private static String UserName;
	
	public static Connection getConnection()
	{
		if (DatabaseLink == null)
			new DatabaseConnection();
		return DatabaseLink;
	}
	
	public static String getUserName() {
		return UserName;
	}
	
	public static void setUserName(String userName) {
		UserName = userName;
	}
	/*
	 * Gets a specific column from person table.
	 * Overloads exist which take tablename and key,value as parameter.
	 * */
	public static String getSpecificColumn(String column) throws SQLException
	{
		if (DatabaseLink != null)
		{
			Connection conn = DatabaseLink;
			String query = "Select " + column + " from person where UserName = '" + getUserName() + "'";
			Statement st = conn.createStatement();
	         try (ResultSet resultSet = st.executeQuery(query)) 
	         {
	                if (resultSet.next()) 
	                {
	                    return resultSet.getString(column);
	                }
	                return "";
	         }
	         catch (SQLException e) 
	        {
	            e.printStackTrace(); // Log or handle the exception as needed
	        }
	    }
		return "";
	}
	public static String getSpecificColumn(String table, String column, String key, String value) throws SQLException
	{
		if (DatabaseLink != null)
		{
			Connection conn = DatabaseLink;
			String query = "Select " + column + " from " + table + " where " + key + " = '" + value + "'";
			Statement st = conn.createStatement();
	         try (ResultSet resultSet = st.executeQuery(query)) 
	         {
	                if (resultSet.next()) 
	                {
	                    return resultSet.getString(column);
	                }
	                return "";
	         }
	         catch (SQLException e) 
	        {
	            e.printStackTrace(); // Log or handle the exception as needed
	        }
	    }
		return "";
	}
	 public static int calculateAge() throws SQLException {
	        int age = -1; // Default value if something goes wrong or username not found
	        
	        	Connection connection = DatabaseLink;
	        
	            String query = "SELECT DOB FROM person WHERE UserName = ?";
	            
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                preparedStatement.setString(1, getUserName());

	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                        LocalDate dob = resultSet.getDate("DOB").toLocalDate();
	                        LocalDate currentDate = LocalDate.now();

	                        // Calculate age using java.time.Period
	                        Period period = Period.between(dob, currentDate);
	                        age = period.getYears();
	                    }
	                }
	            } 

	        return age;
	    }

	}