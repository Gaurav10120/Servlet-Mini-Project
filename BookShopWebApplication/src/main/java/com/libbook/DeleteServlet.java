package com.libbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int id = Integer.parseInt(request.getParameter("id"));

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "Shreya@22");

            if (conn != null) {
                stmt = conn.prepareStatement("DELETE FROM BOOKDATA WHERE BID = ?");
                stmt.setInt(1, id);

                int count = stmt.executeUpdate();
                if (count == 1) {
                    out.println("<h2>Record is Deleted Successfully</h2>");
                } else {
                    out.println("<h2>Record is not deleted Successfully</h2>");
                }
            }
        } catch (Exception ex) {
            out.println("Error is = " + ex);
        } 
        
        
        out.println("<a href='home.html'>Home</a>");
        out.println("<br>");
        out.println("<a href='bookList'>Book List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
