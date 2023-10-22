package com.gaurav;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/del")
public class DeleteServ extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int userid = Integer.parseInt(request.getParameter("userid").trim()); // Get the user ID to be deleted
        
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUDAPP", "root", "Shreya@22");
            if (conn != null) {
                // Create a prepared statement for deleting the record with the given ID
                PreparedStatement stmt = conn.prepareStatement("delete from register where rid=" + userid);
                int value = stmt.executeUpdate(); // Execute the delete query
                if (value > 0) {
                    // If deletion is successful, forward to the view page
                    RequestDispatcher r = request.getRequestDispatcher("view");
                    r.forward(request, response);
                } else {
                    out.println("<h1>Some Problem is there ...</h1>");
                }
            } else {
                out.println("Database is Not Connected");
            }
        } catch (Exception ex) {
            out.println("Error is " + ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This method is not used in the code, but it's included for consistency
        doGet(request, response);
    }
}
