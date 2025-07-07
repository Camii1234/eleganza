<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.model.Usuario"%>
<%@page import="com.camila.eleganza.util.SessionManager"%>
<%
    // Obtener usuario de la sesión (ya verificado por el filtro)
    Usuario usuarioLogueado = SessionManager.getUsuarioSesion(session);
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Mi Perfil - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Mi Perfil - Boutique Eleganza</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
    </head>
    <body class="d-flex flex-column h-100">
        <main class="flex-shrink-0">
            <!-- Navigation-->
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container px-5">
                    <a class="navbar-brand" href="index.jsp">Boutique Eleganza</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                            <li class="nav-item"><a class="nav-link" href="index.jsp">Inicio</a></li>
                            <li class="nav-item"><a class="nav-link" href="productos.jsp">Productos</a></li>
                            <li class="nav-item"><a class="nav-link" href="sobre-nosotros.jsp">Sobre Nosotros</a></li>
                            <li class="nav-item"><a class="nav-link" href="contacto.jsp">Contacto</a></li>
                            <li class="nav-item"><a class="nav-link" href="carrito.jsp"><i class="bi bi-cart"></i> Carrito</a></li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle active" id="navbarDropdownUser" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-person-circle"></i> <%= usuarioLogueado.getNombre() %>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownUser">
                                    <li><a class="dropdown-item active" href="perfil.jsp">Mi Perfil</a></li>
                                    <li><a class="dropdown-item" href="admin.jsp">Administración</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="logout">Cerrar Sesión</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- Page content-->
            <section class="py-5">
                <div class="container px-5">
                    <!-- Page title-->
                    <div class="row justify-content-center">
                        <div class="col-lg-8 col-xxl-6">
                            <div class="text-center my-5">
                                <h1 class="fw-bolder mb-3">Mi Perfil</h1>
                                <p class="lead fw-normal text-muted mb-4">Gestiona tu información personal</p>
                            </div>
                        </div>
                    </div>
                    <!-- Profile content-->
                    <div class="row">
                        <!-- Profile sidebar-->
                        <div class="col-lg-4 mb-5">
                            <div class="card shadow border-0 rounded-3">
                                <div class="card-body text-center p-4">
                                    <div class="mb-3">
                                        <i class="bi bi-person-circle fs-1 text-primary"></i>
                                    </div>
                                    <h5 class="fw-bolder"><%= usuarioLogueado.getNombre() %></h5>
                                    <p class="text-muted mb-3"><%= usuarioLogueado.getCorreo() %></p>
                                    <div class="d-grid gap-2">
                                        <button class="btn btn-outline-primary">
                                            <i class="bi bi-camera"></i> Cambiar Foto
                                        </button>
                                        <button class="btn btn-outline-danger">
                                            <i class="bi bi-trash"></i> Eliminar Perfil
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Profile forms-->
                        <div class="col-lg-8">
                            <!-- Personal information form-->
                            <div class="card shadow border-0 rounded-3 mb-4">
                                <div class="card-header bg-primary">
                                    <h5 class="text-white mb-0">Información Personal</h5>
                                </div>
                                <div class="card-body p-4">
                                    <!-- Mostrar mensaje de éxito -->
                                    <% 
                                    String success = (String) request.getAttribute("success");
                                    if (success != null) { 
                                    %>
                                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                                            <i class="bi bi-check-circle"></i> <%= success %>
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                        </div>
                                    <% } %>
                                    
                                    <!-- Mostrar mensaje de error -->
                                    <% 
                                    String error = (String) request.getAttribute("error");
                                    if (error != null) { 
                                    %>
                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                            <i class="bi bi-exclamation-circle"></i> <%= error %>
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                        </div>
                                    <% } %>
                                    
                                    <form method="post" action="actualizarPerfil">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="nombre" class="form-label">Nombre completo *</label>
                                                <input type="text" class="form-control" id="nombre" name="nombre" value="<%= usuarioLogueado.getNombre() %>" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="correo" class="form-label">Correo electrónico *</label>
                                                <input type="email" class="form-control" id="correo" name="correo" value="<%= usuarioLogueado.getCorreo() %>" required>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="telefono" class="form-label">Teléfono *</label>
                                                <input type="tel" class="form-control" id="telefono" name="telefono" value="<%= usuarioLogueado.getTelefono() != null ? usuarioLogueado.getTelefono() : "" %>" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="pais" class="form-label">País *</label>
                                                <select class="form-select" id="pais" name="pais" required>
                                                    <option value="colombia" <%= "colombia".equals(usuarioLogueado.getPais()) ? "selected" : "" %>>Colombia</option>
                                                    <option value="venezuela" <%= "venezuela".equals(usuarioLogueado.getPais()) ? "selected" : "" %>>Venezuela</option>
                                                    <option value="ecuador" <%= "ecuador".equals(usuarioLogueado.getPais()) ? "selected" : "" %>>Ecuador</option>
                                                    <option value="panama" <%= "panama".equals(usuarioLogueado.getPais()) ? "selected" : "" %>>Panamá</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="ciudad" class="form-label">Ciudad *</label>
                                                <input type="text" class="form-control" id="ciudad" name="ciudad" value="<%= usuarioLogueado.getCiudad() != null ? usuarioLogueado.getCiudad() : "" %>" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="codigoPostal" class="form-label">Código Postal</label>
                                                <input type="text" class="form-control" id="codigoPostal" name="codigoPostal" value="<%= usuarioLogueado.getCodigoPostal() != null ? usuarioLogueado.getCodigoPostal() : "" %>">
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="calle" class="form-label">Dirección (Calle) *</label>
                                            <input type="text" class="form-control" id="calle" name="calle" value="<%= usuarioLogueado.getCalle() != null ? usuarioLogueado.getCalle() : "" %>" required>
                                        </div>
                                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="bi bi-check-circle"></i> Guardar Cambios
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <!-- Change password form-->
                            <div class="card shadow border-0 rounded-3">
                                <div class="card-header bg-warning">
                                    <h5 class="text-dark mb-0">Cambiar Contraseña</h5>
                                </div>
                                <div class="card-body p-4">
                                    <form>
                                        <div class="mb-3">
                                            <label for="currentPassword" class="form-label">Contraseña actual *</label>
                                            <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="newPassword" class="form-label">Nueva contraseña *</label>
                                                <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="confirmPassword" class="form-label">Confirmar contraseña *</label>
                                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                                            </div>
                                        </div>
                                        <div class="alert alert-info">
                                            <small>
                                                <i class="bi bi-info-circle"></i>
                                                La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas y números.
                                            </small>
                                        </div>
                                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                            <button type="submit" class="btn btn-warning">
                                                <i class="bi bi-key"></i> Cambiar Contraseña
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <!-- Footer-->
        <footer class="bg-dark py-4 mt-auto">
            <div class="container px-5">
                <div class="row align-items-center justify-content-between flex-column flex-sm-row">
                    <div class="col-auto"><div class="small m-0 text-white">Copyright &copy; Boutique Eleganza 2024</div></div>
                    <div class="col-auto">
                        <a class="link-light small" href="#!">Privacidad</a>
                        <span class="text-white mx-1">&middot;</span>
                        <a class="link-light small" href="#!">Términos</a>
                        <span class="text-white mx-1">&middot;</span>
                        <a class="link-light small" href="contacto.jsp">Contacto</a>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>
