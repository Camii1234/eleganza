package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.camila.eleganza.dao.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test-db")
public class TestDBServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Test de Base de Datos</h2>");
        
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn != null) {
                out.println("<p>✅ Conexión establecida exitosamente</p>");
                
                // Verificar estructura de la tabla usuario
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "usuario", null);
                
                if (tables.next()) {
                    out.println("<p>✅ Tabla 'usuario' encontrada</p>");
                    out.println("<h3>Estructura de la tabla usuario:</h3>");
                    out.println("<ul>");
                    
                    ResultSet columns = metaData.getColumns(null, null, "usuario", null);
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String dataType = columns.getString("TYPE_NAME");
                        int columnSize = columns.getInt("COLUMN_SIZE");
                        boolean nullable = columns.getBoolean("NULLABLE");
                        
                        out.println("<li>" + columnName + " - " + dataType + "(" + columnSize + ") - " + 
                                   (nullable ? "NULL" : "NOT NULL") + "</li>");
                    }
                    out.println("</ul>");
                    
                } else {
                    out.println("<p>❌ Tabla 'usuario' no encontrada</p>");
                }
                
            } else {
                out.println("<p>❌ No se pudo establecer conexión</p>");
            }
        } catch (SQLException e) {
            out.println("<p>❌ Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("</body></html>");
    }
}
