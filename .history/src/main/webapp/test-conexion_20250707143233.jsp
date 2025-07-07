<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.dao.ConexionBD"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Conexión BD</title>
</head>
<body>
    <h1>Test de Conexión a Base de Datos</h1>
    
    <%
    try {
        // Probar conexión
        Connection conn = ConexionBD.getConnection();
        
        if (conn != null) {
            out.println("<p style='color: green;'>✅ Conexión exitosa a la base de datos!</p>");
            
            // Verificar estructura de tabla usuario
            PreparedStatement ps = conn.prepareStatement("DESCRIBE usuario");
            ResultSet rs = ps.executeQuery();
            
            out.println("<h3>Estructura de la tabla usuario:</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>Campo</th><th>Tipo</th><th>Null</th><th>Key</th><th>Default</th><th>Extra</th></tr>");
            
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("Field") + "</td>");
                out.println("<td>" + rs.getString("Type") + "</td>");
                out.println("<td>" + rs.getString("Null") + "</td>");
                out.println("<td>" + rs.getString("Key") + "</td>");
                out.println("<td>" + rs.getString("Default") + "</td>");
                out.println("<td>" + rs.getString("Extra") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            rs.close();
            ps.close();
            conn.close();
        } else {
            out.println("<p style='color: red;'>❌ Error: No se pudo conectar a la base de datos</p>");
        }
        
    } catch (Exception e) {
        out.println("<p style='color: red;'>❌ Error: " + e.getMessage() + "</p>");
        e.printStackTrace();
    }
    %>
    
    <br>
    <a href="registro.jsp">← Volver al registro</a>
</body>
</html>
