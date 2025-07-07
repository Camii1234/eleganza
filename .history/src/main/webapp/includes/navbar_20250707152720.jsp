<%@page import="com.camila.eleganza.model.Usuario"%>
<%@page import="com.camila.eleganza.util.SessionManager"%>
<%
    // Verificar si el usuario está logueado
    Usuario usuarioLogueado = SessionManager.getUsuarioSesion(session);
    boolean isLoggedIn = SessionManager.isUsuarioLogueado(session);
    boolean isAdmin = SessionManager.isAdmin(session);
%>
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container px-5">
        <a class="navbar-brand" href="index.jsp">Boutique Eleganza</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="index.jsp">Inicio</a></li>
                <li class="nav-item"><a class="nav-link" href="productos.jsp">Productos</a></li>
                <li class="nav-item"><a class="nav-link" href="sobre-nosotros.jsp">Sobre Nosotros</a></li>
                <li class="nav-item"><a class="nav-link" href="contacto.jsp">Contacto</a></li>
                <li class="nav-item"><a class="nav-link" href="carrito.jsp"><i class="bi bi-cart"></i> Carrito</a></li>
                <li class="nav-item dropdown">
                    <% if (isLoggedIn) { %>
                        <a class="nav-link dropdown-toggle" id="navbarDropdownUser" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle"></i> <%= usuarioLogueado.getNombre() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownUser">
                            <li><a class="dropdown-item" href="perfil.jsp">Mi Perfil</a></li>
                            <% if (isAdmin) { %>
                                <li><a class="dropdown-item" href="admin.jsp">Administración</a></li>
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
