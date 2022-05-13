package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {
	
	private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// this sample 1
	
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "");
			
			//For testing
			System.out.print("Successfully connected");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//Read function
	public String readCustomer()
	{ 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				return "Error while connecting to the database for reading."; 
			 } 
			 
			 // Prepare the html table to be displayed
			 
			 output = "<table border='1' class='table'><thead class='thead-dark'><th>Customer Name</th>" +"<th>Customer Address</th><th>Customer NIC</th>"+ "<th>Customer Email</th>" + "<th>Customer Phone Number</th>" + "<th>Update</th><th>Remove</th></thead>"; 
			 String query = "select * from custom"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 	
				 // Retrieve from database using column names
				 String cID = Integer.toString(rs.getInt("cID")); 
				 String customerName = rs.getString("customerName"); 
				 String customerAddress = rs.getString("customerAddress"); 
				 String customerNIC = rs.getString("customerNIC"); 
				 String customerEmail = rs.getString("customerEmail");
				 String customerPNO = rs.getString("customerPNO"); 
				 
				 // Add a row into the html table
				 output += "<tr><td>"+ customerName + "</td>";
				 output += "<td>" + customerAddress + "</td>"; 
				 output += "<td>" + customerNIC + "</td>";
				 output += "<td>" + customerEmail + "</td>"; 
				 output += "<td>" + customerPNO + "</td>";
//				 output += "<td>" + cID + "</td>";
				 
				 // Buttons
				 output += 
				   "<td><input name='btnUpdate' type='button' value='Update' " + "class='btnUpdate btn btn-secondary' data-cid='" + cID + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-cid='" + cID + "'>"+"</td>"
				 + "</form></td></tr>";
				 				 
			 } 
			 
			con.close(); 
			// Complete the html table
			output += "</table>"; 
			 } 
		catch (Exception e) 
		 { 
			 output = "Error while reading the customers."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}	
	
	//Insert function
	public String insertCustomer(String customerName, String customerAddress, String customerNIC, String customerEmail, String customerPNO)
	{ 
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into custom(`cID`,`customerName`,`customerAddress`,`customerNIC`,`customerEmail`,`customerPNO`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, customerAddress);
			preparedStmt.setString(4, customerNIC);
			preparedStmt.setString(5, customerEmail);
			preparedStmt.setString(6, customerPNO);
			// execute the statement
			preparedStmt.execute();
			con.close();
			 
			 //call the readItems() method and get the updated grid and embed the HTML into the JSON object.
			 String newCus = readCustomer();
			 output = "{\"status\":\"success\", \"data\": \"" + newCus + "\"}";
		 } 
		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the customer.\"}";
			 System.err.println(e.getMessage()); 
		 } 
		return output; 
	}
		
	//Delete function
	public String deleteCustomer(String cID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
		
			// create a prepared statement
			String query = "delete from custom where cID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(cID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newCus = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" + newCus + "\"}";
			
		}
		
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the customer.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//Update function
	public String updateCustomer(String cID, String customerName, String customerAddress, String customerNIC, String customerEmail, String customerPNO)
	{
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE custom SET customerName=?,customerAddress=?,customerNIC=?,customerEmail=?,customerPNO=?" + "WHERE cID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, customerName);
			preparedStmt.setString(2, customerAddress);
			preparedStmt.setString(3, customerNIC);
			preparedStmt.setString(4, customerEmail);
			preparedStmt.setString(5, customerPNO);
			preparedStmt.setInt(6, Integer.parseInt(cID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newCus = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" + newCus + "\"}";
		
		}
		
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the customer.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
