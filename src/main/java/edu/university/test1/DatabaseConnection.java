package edu.university.test1;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static Connection getConnection() {
        String url = "jdbc:postgresql://cejlvtufmo8r9j.cluster-czz5s0kz4scl.eu-west-1.rds.amazonaws.com:5432/dvmrebm9u2chs";
        String user = "u63vh4hq40c7fu";
        String password = "pc884b70502ddd19d1391628caceefd52e90e5ee99b59a2790a7a824c4289b40a";

        try {
            Class.forName("org.postgresql.Driver"); // Load the driver
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            return null;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DatabaseConnection</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DatabaseConnection at " + request.getContextPath() + "</h1>");

            try (Connection conn = getConnection()) {
                if (conn != null) {
                    out.println("<h2>Database Connection Successful!</h2>");

                    // Get database information
                    DatabaseMetaData dbMetaData = conn.getMetaData();
                    out.println("Database Name: " + dbMetaData.getDatabaseProductName() + "<br>");
                    out.println("Database Version: " + dbMetaData.getDatabaseProductVersion() + "<br>");
                } else {
                    out.println("<h2>Database Connection Failed</h2>");
                }
            } catch (SQLException e) {
                out.println("<h2>Database Connection Error: " + e.getMessage() + "</h2>");
            }

            out.println("</body>");
            out.println("</html>");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
