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
            <!-- Include Navigation -->
            <jsp:include page="includes/navbar.jsp" />
            
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
                                        <button class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#eliminarPerfilModal">
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
                                    <form method="post" action="cambiarPassword">
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
        
        <!-- Modal para confirmar eliminación de perfil -->
        <div class="modal fade" id="eliminarPerfilModal" tabindex="-1" aria-labelledby="eliminarPerfilModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-danger">
                        <h5 class="modal-title text-white" id="eliminarPerfilModalLabel">
                            <i class="bi bi-exclamation-triangle"></i> Confirmar Eliminación de Perfil
                        </h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="alert alert-warning">
                            <i class="bi bi-exclamation-triangle"></i>
                            <strong>¡Atención!</strong> Esta acción no se puede deshacer.
                        </div>
                        <p>Al eliminar tu perfil:</p>
                        <ul>
                            <li>Se eliminarán todos tus datos personales</li>
                            <li>Se eliminarán todos tus pedidos e historial</li>
                            <li>No podrás recuperar tu cuenta</li>
                        </ul>
                        <p><strong>¿Estás seguro de que deseas eliminar tu perfil permanentemente?</strong></p>
                        
                        <form method="post" action="eliminarPerfil" id="eliminarPerfilForm">
                            <div class="mb-3">
                                <label for="confirmacion" class="form-label">
                                    Para confirmar, escribe <strong>ELIMINAR</strong> en el campo de abajo:
                                </label>
                                <input type="text" class="form-control" id="confirmacion" name="confirmacion" required 
                                       placeholder="Escribe ELIMINAR para confirmar">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="bi bi-x-circle"></i> Cancelar
                        </button>
                        <button type="submit" form="eliminarPerfilForm" class="btn btn-danger">
                            <i class="bi bi-trash"></i> Eliminar Perfil Permanentemente
                        </button>
                    </div>
                </div>
            </div>
        </div>
        
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
