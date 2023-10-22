package com.libbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish a database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "Shreya@22");
            
            if (conn != null) {
                // Prepare an SQL statement to select book information
                PreparedStatement stmt = conn.prepareStatement("SELECT BID, BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA");
                
                // Execute the SQL query and retrieve the results
                ResultSet rs = stmt.executeQuery();
                
                // Generate an HTML table to display book information
                out.println("<table border='1' align='center'>");
                out.println("<tr>");
                out.println("<th>Book Id</th>");
                out.println("<th>Book Name</th>");
                out.println("<th>Book Edition</th>");
                out.println("<th>Book Price</th>");
                out.println("<th>Edit</th>");
                out.println("<th>Delete</th>");
                out.println("</tr>");
                
                // Loop through the result set and populate the table
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt(1) + "</td>");
                    out.println("<td>" + rs.getString(2) + "</td>");
                    out.println("<td>" + rs.getString(3) + "</td>");
                    out.println("<td>" + rs.getFloat(4) + "</td>");
                    out.println("<td><a href='editScreen?id=" + rs.getInt(1) + "'>Edit</a></td>");
                    out.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a></td>");
                    out.println("</tr>");
                }
                
                out.println("</table>");
            }
        } catch (Exception ex) {
            // Handle exceptions and display an error message
            out.println("Error is = " + ex);
        } 
            
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Delegate POST requests to the doGet method
        doGet(req, res);
    }
}
