package com.gaurav;

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

@WebServlet("/view")
public class ViewAll extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // HTML Page
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");

        // Include a navigation bar
        RequestDispatcher r = request.getRequestDispatcher("navigation.html");
        r.include(request, response);

        out.println("<br><br>");
        out.println("<div class='container'>");
        out.println("<div class='formcontrol'>");
        out.println("<table>");
        out.println("<tr><th>NAME</th><th>EMAIL</th><th>CONTACT</th><th>DELETE</th><th>UPDATE</th></tr>");
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUDAPP", "root", "Shreya@22");
            if (conn != null) {
                // Create a prepared statement for selecting all records from the 'register' table
                PreparedStatement stmt = conn.prepareStatement("select * from register");
                // Execute the query and retrieve the result set
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("<td>" + rs.getString("contact") + "</td>");

                    // Create DELETE and UPDATE links with appropriate parameters
                    out.println("<td><a href='del?userid=" + rs.getInt("rid") + "'>DELETE</a></td>");
                    out.println("<td><a href='update?id=" + rs.getInt("rid") + "&name=" + rs.getString("name")
                            + "&email=" + rs.getString("email") + "&contact=" + rs.getString("contact") + "'>UPDATE</a></td>");

                    out.println("</tr>");
                }
            } else {
                out.println("Database is Not Connected");
            }
        } catch (Exception ex) {
            out.println("Error is " + ex);
        }
        out.println("</div>");
        out.println("</div>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This method is not used in the code, but it's included for consistency
        doGet(request, response);
    }
}
