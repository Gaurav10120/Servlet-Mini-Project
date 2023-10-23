package com.libbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "Shreya@22");

            if (conn != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                String bookName = request.getParameter("bookName");
                String bookEdition = request.getParameter("bookEdition");

                // Validate bookPrice parameter
                String bookPriceParam = request.getParameter("bookPrice");
                if (bookPriceParam != null && !bookPriceParam.isEmpty()) {
                    try {
                        float bookPrice = Float.parseFloat(bookPriceParam);
                        stmt = conn.prepareStatement("UPDATE BOOKDATA SET BOOKNAME=?, BOOKEDITION=?, BOOKPRICE=? WHERE BID=?");
                        stmt.setString(1, bookName);
                        stmt.setString(2, bookEdition);
                        stmt.setFloat(3, bookPrice);
                        stmt.setInt(4, id);

                        int value = stmt.executeUpdate();
                        if (value > 0) {
                            // If deletion is successful, forward to the view page
                            RequestDispatcher r = request.getRequestDispatcher("bookList");
                            r.forward(request, response);
                        } else {
                            out.println("<h1>Some Problem is there ...</h1>");
                        }
                    } catch (NumberFormatException ex) {
                        out.println("<h2>Invalid bookPrice value: It must be a valid number.</h2>");
                    }
                } else {
                    out.println("<h2>Invalid bookPrice value: It is missing or empty.</h2>");
                }
            } else {
                out.println("<h2>Failed to connect to the database</h2>");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            out.println("Error is = " + ex);
        } finally {
            // Close resources (stmt, conn) in a finally block
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Delegate POST requests to the doGet method
        doGet(req, res);
    }
}
