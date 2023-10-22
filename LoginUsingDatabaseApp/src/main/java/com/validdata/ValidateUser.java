package com.validdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/validate")
public class ValidateUser extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		String username=request.getParameter("user");
		String password=request.getParameter("pass");
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");	
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revision", "root", "Shreya@22");
	         
			if(conn!=null) 
			{
				PreparedStatement stmt = conn.prepareStatement("SELECT username, password FROM RegisterHere WHERE username = ? AND password = ?");
				stmt.setString(1, username);
				stmt.setString(2, password);
				
				ResultSet rs= stmt.executeQuery();
		       
		       if(rs.next())
		       {
		    	 if(rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
		    	 RequestDispatcher r=request.getRequestDispatcher("welcome.html");
		 			r.forward(request,response);
		 		}		    		 
		    	 }
		       else
		       {
		    	 
		   			RequestDispatcher r=request.getRequestDispatcher("Login.html");
		   			r.include(request,response);
		   			out.println("<h1>Invalid Username and Password </h1>");
		       }
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
