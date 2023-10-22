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

@WebServlet("/finalupdt")
public class FinalUpdate extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Display the HTML form for updating data
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Update Student</title></head>");
        out.println("<body>");

        // Your HTML form should include fields for "name," "email," "contact," and "id"
        out.println("<form method='post' action='finalupdt'>");
        out.println("Name: <input type='text' name='name' /><br>");
        out.println("Email: <input type='text' name='email' /><br>");
        out.println("Contact: <input type='text' name='contact' /><br>");
        out.println("<input type='hidden' name='id' value='123' />"); // Replace with the actual ID
        out.println("<input type='submit' name='s' value='Update' />");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String btn = request.getParameter("s");

        if (btn != null) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            int userid = Integer.parseInt(request.getParameter("id"));

            try {
                // Register the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish a database connection
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUDAPP", "root", "Shreya@22");

                if (conn != null) {
                    // Create a prepared statement for updating the record
                    PreparedStatement stmt = conn.prepareStatement("UPDATE register SET name=?, email=?, contact=? WHERE rid=?");
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.setString(3, contact);
                    stmt.setInt(4, userid);

                    int value = stmt.executeUpdate(); // Execute the update query
                    if (value > 0) {
                        // If update is successful, forward to the view page
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
    }
}
