package com.libbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "Shreya@22");
            
            if (conn != null) {
                int id = Integer.parseInt(request.getParameter("id"));

                // Prepare a SQL statement to fetch book details by ID
                stmt = conn.prepareStatement("SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA WHERE BID = ?");
                stmt.setInt(1, id);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    // Display a form to edit book details
                	out.println("<form action='editurl?id=" + id + "' method='post'>");
                	out.println("<table align='center'>");
                	out.println("<tr>");
                	out.println("<td style='color: blue;'>Book Name</td>");
                	out.println("<td><input type='text' name='bookName' value='" + rs.getString("BOOKNAME") + "'></td>");
                	out.println("</tr>");
                	out.println("<tr>");
                	out.println("<td style='color: green;'>Book Edition</td>");
                	out.println("<td><input type='text' name='bookEdition' value='" + rs.getString("BOOKEDITION") + "'></td>");
                	out.println("</tr>");
                	out.println("<tr>");
                	out.println("<td style='color: red;'>Book Price</td>");
                	out.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat("BOOKPRICE") + "'></td>");
                	out.println("</tr>");
                	out.println("<tr>");
                	out.println("<td><input type='submit' value='Edit'></td>");
                	out.println("<td><input type='reset' value='Cancel'></td>");
                	out.println("</tr>");
                	out.println("</table>");
                	out.println("</form>");

                } else {
                    out.println("<h2>Record not found.</h2>");
                }
            } else {
                out.println("<h2>Failed to connect to the database</h2>");
            }
        } catch (ClassNotFoundException ex) {
            out.println("Error loading the database driver: " + ex.getMessage());
        } catch (SQLException ex) {
            out.println("SQL error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            out.println("Invalid ID format.");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                out.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Delegate POST requests to the doGet method
        doGet(req, res);
    }
}
