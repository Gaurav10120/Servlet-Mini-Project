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

@WebServlet("/update")
public class UpdateServ extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // HTML Page
        out.println("<html>");
        out.println("<head><title>Add New Student</title>");
        out.println("<link rel='stylesheet' href='Css/style.css'/>");
        out.println("</head>");
        out.println("<body>");

        // Include a navigation bar
        RequestDispatcher r = request.getRequestDispatcher("navigation.html");
        r.include(request, response);

        out.println("<br><br>");
        int userid = Integer.parseInt(request.getParameter("id")); // Get the user ID for updating
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");

        out.println("<div class='container'>");
        out.println("<div class='formcontrol'>");
        out.println("<form name='frm' action='finalupdt' method='POST'>");

        // Create a hidden input field to send the user ID to the update servlet
        out.println("<input type='hidden' name='id' value='" + userid + "' class='control' placeholder='Enter Name'/><br><br>");

        out.println("<input type='text' name='name' value='" + name + "' class='control' placeholder='Enter Name'/><br><br>");
        out.println("<input type='text' name='email' value='" + email + "' class='control' placeholder='Enter Email'/><br><br>");
        out.println("<input type='text' name='contact' value='" + contact + "' class='control' placeholder='Enter Contact'/><br><br>");
        out.println("<input type='submit' name='s' value='Update Student' class='control'/><br><br>");
        out.println("</form>");

        out.println("</div>");
        out.println("</div>");

        String btn = request.getParameter("s");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Call the doGet method for handling both GET and POST requests
        doGet(request, response);
    }
}
