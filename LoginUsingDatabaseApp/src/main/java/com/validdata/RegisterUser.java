package com.validdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/reg")
public class RegisterUser extends HttpServlet {
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		
		
		String name= request.getParameter("name");
		String email= request.getParameter("email");
		String contact= request.getParameter("contact");
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");	
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revision", "root", "Shreya@22");
	         
			if(conn!=null) 
			{
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO RegisterHere (name, email, contact, username, password) VALUES (?, ?, ?, ?, ?)");
				stmt.setString(1, name);
				stmt.setString(2, email);
				stmt.setString(3, contact);
				stmt.setString(4, username);
				stmt.setString(5, password);

			 	int value=stmt.executeUpdate();		
			 	if(value>0) {
			 		RequestDispatcher r=request.getRequestDispatcher("welcome.html");
		 			r.forward(request,response);			 	}
			 	else {
			 		RequestDispatcher r=request.getRequestDispatcher("Login.html");
		   			r.include(request,response);			 	}
			 	}
			else
			{
				out.println("Not Connected");
			}
		}
		catch(Exception ex)
		{
			out.println("Error is = "+ex);
		}
		

		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
