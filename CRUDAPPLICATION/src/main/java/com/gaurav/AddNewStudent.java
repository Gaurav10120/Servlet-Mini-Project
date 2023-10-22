package com.gaurav;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addstudent")
public class AddNewStudent extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// HTML page
		out.println("<html>");
		out.println("<head><title>Add New Student</title>");
		out.println("<link rel='stylesheet' href='Css/style.css'/>");
		out.println("</head>");
		out.println("<body>");
		
		// Include a navigation bar
		RequestDispatcher r = request.getRequestDispatcher("navigation.html");
		r.include(request, response);
		
		out.println("<br><br>");
		out.println("<div class='container'>");
		out.println("<div class='formcontrol'>");
		out.println("<form name='frm' action='' method='POST'>");
		out.println("<input type='text' name='name' value='' class='control' placeholder='Enter Name'/><br><br>");
		out.println("<input type='text' name='email' value='' class='control' placeholder='Enter Email'/><br><br>");
		out.println("<input type='text' name='contact' value='' class='control' placeholder='Enter Contact'/><br><br>");
		out.println("<input type='submit' name='s' value='Add New Student' class='control'/><br><br>");
		out.println("</form>");
		
		// Check if the 'Add New Student' button is clicked
		String button = request.getParameter("s");
		if (button != null) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String contact = request.getParameter("contact");
			try {
				// Register the JDBC driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				// Establish a database connection
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUDAPP", "root", "Shreya@22");
				if (conn != null) {
					// Create a prepared statement for inserting data into the database
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO register VALUES('0',?,?,?)");
					stmt.setString(1, name);
					stmt.setString(2, email);
					stmt.setString(3, contact);
					// Execute the update query
					int value = stmt.executeUpdate();
					if (value > 0) {
						out.println("<h1>Record Saved in Database</h1>");
					} else {
						out.println("<h1>Some Problem Occurred...</h1>");
					}
				} else {
					out.println("Database is Not Connected");
				}
			} catch (Exception ex) {
				out.println("Error is " + ex);
			}
		}
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
