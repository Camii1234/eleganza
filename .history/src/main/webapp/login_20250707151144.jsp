<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Iniciar Sesión - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Iniciar Sesión - Boutique Eleganza</title>
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
                                <a class="nav-link dropdown-toggle active" id="navbarDropdownUser" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Mi Cuenta</a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownUser">
                                    <li><a class="dropdown-item active" href="login.jsp">Iniciar Sesión</a></li>
                                    <li><a class="dropdown-item" href="registro.jsp">Registrarse</a></li>
                                    <li><a class="dropdown-item" href="perfil.jsp">Mi Perfil</a></li>
                                    <li><a class="dropdown-item" href="admin.jsp">Administración</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- Page content-->
            <section class="py-5">
                <div class="container px-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-6 col-md-8">
                            <div class="card shadow border-0 rounded-3 overflow-hidden">
                                <div class="card-header bg-primary text-center">
                                    <h3 class="text-white mb-0">Iniciar Sesión</h3>
                                </div>
                                <div class="card-body p-5">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-person-circle fs-1 text-primary"></i>
                                        <p class="text-muted">Accede a tu cuenta de Boutique Eleganza</p>
                                    </div>
                                    
                                    <!-- Mostrar mensaje de registro exitoso -->
                                    <% 
                                    String registro = request.getParameter("registro");
                                    if ("exitoso".equals(registro)) { 
                                    %>
                                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                                            <i class="bi bi-check-circle"></i> ¡Registro exitoso! Ya puedes iniciar sesión con tu cuenta.
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                        </div>
                                    <% } %>
                                    
                                    <!-- Mostrar mensaje de logout exitoso -->
                                    <% 
                                    String logout = request.getParameter("logout");
                                    if ("exitoso".equals(logout)) { 
                                    %>
                                        <div class="alert alert-info alert-dismissible fade show" role="alert">
                                            <i class="bi bi-info-circle"></i> Has cerrado sesión exitosamente.
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
                                    
                                    <form method="post" action="login">
                                        <div class="form-floating mb-3">
                                            <input type="email" class="form-control" id="email" name="email" placeholder="correo@ejemplo.com" required>
                                            <label for="email">Correo electrónico</label>
                                        </div>
                                        <div class="form-floating mb-3">
                                            <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
                                            <label for="password">Contraseña</label>
                                        </div>
                                        <div class="d-grid mb-3">
                                            <button class="btn btn-primary btn-lg" type="submit">
                                                <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                                            </button>
                                        </div>
                                        <div class="text-center">
                                            <a href="#!" class="text-decoration-none">¿Olvidaste tu contraseña?</a>
                                        </div>
                                    </form>
                                </div>
                                <div class="card-footer bg-light text-center">
                                    <p class="mb-0">¿No tienes una cuenta? 
                                        <a href="registro.jsp" class="text-decoration-none fw-bold">Regístrate aquí</a>
                                    </p>
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