package com.libbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Retrieve input parameters from the request
        String bookname = request.getParameter("bookName");
        String bookEdition = request.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(request.getParameter("bookPrice"));

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish a database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "Shreya@22");

            if (conn != null) {
                // Prepare an SQL statement for inserting a new book record
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO BOOKDATA (BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES (?, ?, ?)");

                // Set the parameters for the SQL statement
                stmt.setString(1, bookname);
                stmt.setString(2, bookEdition);
                stmt.setFloat(3, bookPrice);

                // Execute the SQL statement and retrieve the result
                int value = stmt.executeUpdate();

                // Check the result and display a corresponding message
                if (value > 0) {
                    out.println("<h2>Record Is Registered Successfully</h2>");
                } else {
                    out.println("<h2>Record Is Not Registered Successfully</h2>");
                }
            } else {
                out.println("Not Connected");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            // Handle exceptions and display an error message
            out.println("Error is = " + ex);
        }

        // Provide links for navigation
        out.println("<a href='home.html'>Home</a>");
        out.println("<br>");
        out.println("<a href='bookList'>Book List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Delegate POST requests to the doGet method
        doGet(req, res);
    }
}
