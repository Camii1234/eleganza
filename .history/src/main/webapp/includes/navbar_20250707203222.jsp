<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.model.Usuario"%>
<%@page import="com.camila.eleganza.model.Carrito"%>
<%@page import="com.camila.eleganza.util.SessionManager"%>
<%
    // Verificar si el usuario está logueado
    Usuario usuarioLogueado = SessionManager.getUsuarioSesion(session);
    boolean isLoggedIn = SessionManager.isUsuarioLogueado(session);
    boolean isAdmin = SessionManager.isAdmin(session);
    
    // Obtener el carrito de la sesión
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    int cantidadCarrito = (carrito != null) ? carrito.getCantidadTotal() : 0;
    
    // Obtener la página actual para marcar como activa
    String currentPage = request.getRequestURI();
    String contextPath = request.getContextPath();
    if (currentPage.startsWith(contextPath)) {
        currentPage = currentPage.substring(contextPath.length());
    }
%>
<html lang="es">
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark" lang="es">
    <div class="container px-5">
        <a class="navbar-brand" href="index.jsp">Boutique Eleganza</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link <%= "/index.jsp".equals(currentPage) || "/".equals(currentPage) ? "active" : "" %>" href="index.jsp">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= "/productos.jsp".equals(currentPage) ? "active" : "" %>" href="productos.jsp">Productos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= "/sobre-nosotros.jsp".equals(currentPage) ? "active" : "" %>" href="sobre-nosotros.jsp">Sobre Nosotros</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= "/contacto.jsp".equals(currentPage) ? "active" : "" %>" href="contacto.jsp">Contacto</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="carrito.jsp"><i class="bi bi-cart"></i> Carrito</a>
                </li>
                <li class="nav-item dropdown">
                    <% if (isLoggedIn) { %>
                        <a class="nav-link dropdown-toggle <%= "/perfil.jsp".equals(currentPage) || "/admin.jsp".equals(currentPage) ? "active" : "" %>" id="navbarDropdownUser" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle"></i> <%= usuarioLogueado.getNombre() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownUser">
                            <li><a class="dropdown-item <%= "/perfil.jsp".equals(currentPage) ? "active" : "" %>" href="perfil.jsp">Mi Perfil</a></li>
                            <% if (isAdmin) { %>
                                <li><a class="dropdown-item <%= "/admin.jsp".equals(currentPage) ? "active" : "" %>" href="admin.jsp">Administración</a></li>
                            <% } %>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="logout">
                                <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                            </a></li>
                        </ul>
                    <% } else { %>
                        <a class="nav-link dropdown-toggle" id="navbarDropdownUser" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Mi Cuenta</a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownUser">
                            <li><a class="dropdown-item" href="login.jsp">Iniciar Sesión</a></li>
                            <li><a class="dropdown-item" href="registro.jsp">Registrarse</a></li>
                        </ul>
                    <% } %>
                </li>
            </ul>
        </div>
    </div>
</nav>
</html>