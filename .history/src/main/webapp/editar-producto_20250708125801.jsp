<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.dao.ProductoDAO"%>
<%@page import="com.camila.eleganza.model.Producto"%>
<%@page import="java.util.Base64"%>

<%
    String idStr = request.getParameter("id");
    if (idStr == null || idStr.trim().isEmpty()) {
        response.sendRedirect("admin.jsp?mensaje=error&texto=ID de producto no válido");
        return;
    }
    
    int productoId = Integer.parseInt(idStr);
    ProductoDAO productoDAO = new ProductoDAO();
    Producto producto = productoDAO.getProductoById(productoId);
    
    if (producto == null) {
        response.sendRedirect("admin.jsp?mensaje=error&texto=Producto no encontrado");
        return;
    }
    
    // Preparar la imagen para mostrar
    String imagenSrc = "https://dummyimage.com/200x200/ced4da/6c757d?text=Sin+Imagen";
    if (producto.getImagen() != null && producto.getImagen().length > 0) {
        String imagenBase64 = Base64.getEncoder().encodeToString(producto.getImagen());
        imagenSrc = "data:image/jpeg;base64," + imagenBase64;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Editar Producto - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Editar Producto - Boutique Eleganza</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
        <!-- Forzar fuente Playfair Display en toda la página -->
        <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
        <style>
            * { font-family: 'Playfair Display', serif !important; }
        </style>
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
                                <a class="nav-link dropdown-toggle" id="navbarDropdownUser" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Mi Cuenta</a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownUser">
                                    <li><a class="dropdown-item" href="login.jsp">Iniciar Sesión</a></li>
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
                    <!-- Page title-->
                    <div class="row justify-content-center">
                        <div class="col-lg-8 col-xxl-6">
                            <div class="text-center my-5">
                                <h1 class="fw-bolder mb-3">Editar Producto</h1>
                                <p class="lead fw-normal text-muted mb-4">Modifica la información de: <%= producto.getNombre() %></p>
                            </div>
                        </div>
                    </div>
                    <!-- Form-->
                    <div class="row justify-content-center">
                        <div class="col-lg-8">
                            <div class="card shadow border-0 rounded-3 overflow-hidden">
                                <div class="card-header bg-primary">
                                    <h5 class="text-white mb-0">Información del Producto</h5>
                                </div>
                                <div class="card-body p-5">
                                    <form action="EditarProductoServlet" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="id" value="<%= producto.getIdProducto() %>">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="nombre" class="form-label">Nombre del Producto *</label>
                                                <input type="text" class="form-control" id="nombre" name="nombre" value="<%= producto.getNombre() %>" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="precio" class="form-label">Precio *</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">$</span>
                                                    <input type="number" class="form-control" id="precio" name="precio" value="<%= producto.getPrecio() %>" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="categoria" class="form-label">Categoría *</label>
                                                <select class="form-select" id="categoria" name="categoria" required>
                                                    <option value="">Seleccionar categoría</option>
                                                    <option value="vestidos" <%= "vestidos".equals(producto.getCategoria()) ? "selected" : "" %>>Vestidos</option>
                                                    <option value="blusas" <%= "blusas".equals(producto.getCategoria()) ? "selected" : "" %>>Blusas</option>
                                                    <option value="pantalones" <%= "pantalones".equals(producto.getCategoria()) ? "selected" : "" %>>Pantalones</option>
                                                    <option value="faldas" <%= "faldas".equals(producto.getCategoria()) ? "selected" : "" %>>Faldas</option>
                                                    <option value="chaquetas" <%= "chaquetas".equals(producto.getCategoria()) ? "selected" : "" %>>Chaquetas</option>
                                                    <option value="accesorios" <%= "accesorios".equals(producto.getCategoria()) ? "selected" : "" %>>Accesorios</option>
                                                    <option value="calzado" <%= "calzado".equals(producto.getCategoria()) ? "selected" : "" %>>Calzado</option>
                                                    <option value="ropa-interior" <%= "ropa-interior".equals(producto.getCategoria()) ? "selected" : "" %>>Ropa Interior</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="stock" class="form-label">Stock Disponible *</label>
                                                <input type="number" class="form-control" id="stock" name="stock" min="0" value="<%= producto.getStock() %>" required>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="talla" class="form-label">Talla *</label>
                                                <select class="form-select" id="talla" name="talla" required>
                                                    <option value="">Seleccionar talla</option>
                                                    <option value="XS" <%= "XS".equals(producto.getTalla()) ? "selected" : "" %>>XS</option>
                                                    <option value="S" <%= "S".equals(producto.getTalla()) ? "selected" : "" %>>S</option>
                                                    <option value="M" <%= "M".equals(producto.getTalla()) ? "selected" : "" %>>M</option>
                                                    <option value="L" <%= "L".equals(producto.getTalla()) ? "selected" : "" %>>L</option>
                                                    <option value="XL" <%= "XL".equals(producto.getTalla()) ? "selected" : "" %>>XL</option>
                                                    <option value="XXL" <%= "XXL".equals(producto.getTalla()) ? "selected" : "" %>>XXL</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="estado" class="form-label">Estado *</label>
                                                <select class="form-select" id="estado" name="estado" required>
                                                    <option value="">Seleccionar estado</option>
                                                    <option value="Activo" <%= "Activo".equals(producto.getEstado()) ? "selected" : "" %>>Activo</option>
                                                    <option value="Inactivo" <%= "Inactivo".equals(producto.getEstado()) ? "selected" : "" %>>Inactivo</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="imagen-actual" class="form-label">Imagen Actual</label>
                                            <div class="mb-2">
                                                <img src="<%= imagenSrc %>" alt="<%= producto.getNombre() %>" class="img-thumbnail" style="max-width: 200px; height: auto;">
                                            </div>
                                            <label for="imagen" class="form-label">Cambiar Imagen</label>
                                            <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                                            <div class="form-text">Formatos aceptados: JPG, PNG, GIF. Tamaño máximo: 5MB (dejar vacío para mantener imagen actual)</div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="descripcion" class="form-label">Descripción</label>
                                            <textarea class="form-control" id="descripcion" name="descripcion" rows="4" placeholder="Descripción detallada del producto..."><%= producto.getDescripcion() != null ? producto.getDescripcion() : "" %></textarea>
                                        </div>
                                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                            <a href="admin.jsp" class="btn btn-secondary me-md-2">Cancelar</a>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="bi bi-check-circle"></i> Guardar Cambios
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
