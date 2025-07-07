<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="com.camila.eleganza.dao.ConexionBD"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verificación de Base de Datos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .result { padding: 15px; margin: 10px 0; border-radius: 5px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .info { background-color: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>Verificación de Base de Datos</h1>
    
    <%
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn != null) {
                out.println("<div class='success'>");
                out.println("<h3>✅ Conexión establecida exitosamente</h3>");
                out.println("<p>URL: " + conn.getMetaData().getURL() + "</p>");
                out.println("<p>Driver: " + conn.getMetaData().getDriverName() + "</p>");
                out.println("</div>");
                
                // Verificar si la tabla usuario existe
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "usuario", null);
                
                if (tables.next()) {
                    out.println("<div class='success'>");
                    out.println("<h3>✅ Tabla 'usuario' encontrada</h3>");
                    out.println("</div>");
                    
                    // Mostrar estructura de la tabla
                    out.println("<div class='info'>");
                    out.println("<h3>Estructura de la tabla 'usuario':</h3>");
                    out.println("<table>");
                    out.println("<tr><th>Columna</th><th>Tipo</th><th>Tamaño</th><th>Nullable</th><th>Clave</th></tr>");
                    
                    ResultSet columns = metaData.getColumns(null, null, "usuario", null);
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String dataType = columns.getString("TYPE_NAME");
                        int columnSize = columns.getInt("COLUMN_SIZE");
                        boolean nullable = columns.getBoolean("NULLABLE");
                        
                        out.println("<tr>");
                        out.println("<td>" + columnName + "</td>");
                        out.println("<td>" + dataType + "</td>");
                        out.println("<td>" + columnSize + "</td>");
                        out.println("<td>" + (nullable ? "SI" : "NO") + "</td>");
                        out.println("<td>-</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    out.println("</div>");
                    
                    // Verificar claves primarias
                    out.println("<div class='info'>");
                    out.println("<h3>Claves Primarias:</h3>");
                    ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, "usuario");
                    while (primaryKeys.next()) {
                        out.println("<p>Columna: " + primaryKeys.getString("COLUMN_NAME") + "</p>");
                    }
                    out.println("</div>");
                    
                    // Probar inserción simple
                    out.println("<div class='info'>");
                    out.println("<h3>Probando inserción simple...</h3>");
                    
                    String testSql = "INSERT INTO usuario (idUsuario, nombre, correo, telefono, calle, ciudad, codigoPostal, pais, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    try (PreparedStatement ps = conn.prepareStatement(testSql)) {
                        ps.setInt(1, 88888);
                        ps.setString(2, "Test User");
                        ps.setString(3, "test@example.com");
                        ps.setString(4, "123456789");
                        ps.setString(5, "Test Street");
                        ps.setString(6, "Test City");
                        ps.setString(7, "12345");
                        ps.setString(8, "Test Country");
                        ps.setString(9, "testpassword");
                        
                        int result = ps.executeUpdate();
                        
                        if (result > 0) {
                            out.println("<p>✅ Inserción exitosa. Filas afectadas: " + result + "</p>");
                            
                            // Eliminar el registro de prueba
                            String deleteSql = "DELETE FROM usuario WHERE idUsuario = 88888";
                            try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
                                deletePs.executeUpdate();
                                out.println("<p>✅ Registro de prueba eliminado</p>");
                            }
                        } else {
                            out.println("<p>❌ No se insertó ninguna fila</p>");
                        }
                    } catch (SQLException e) {
                        out.println("<p>❌ Error en inserción: " + e.getMessage() + "</p>");
                        out.println("<p>Código de error: " + e.getErrorCode() + "</p>");
                        out.println("<p>Estado SQL: " + e.getSQLState() + "</p>");
                    }
                    out.println("</div>");
                    
                } else {
                    out.println("<div class='error'>");
                    out.println("<h3>❌ Tabla 'usuario' no encontrada</h3>");
                    out.println("<p>La tabla 'usuario' no existe en la base de datos</p>");
                    out.println("</div>");
                }
                
            } else {
                out.println("<div class='error'>");
                out.println("<h3>❌ No se pudo establecer conexión</h3>");
                out.println("</div>");
            }
        } catch (SQLException e) {
            out.println("<div class='error'>");
            out.println("<h3>❌ Error de conexión:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<p>Código de error: " + e.getErrorCode() + "</p>");
            out.println("<p>Estado SQL: " + e.getSQLState() + "</p>");
            out.println("</div>");
        }
    %>
    
    <p><a href="test-directo.jsp">Volver a prueba directa</a></p>
</body>
</html>
