<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>
<%@page import="com.camila.eleganza.model.Producto"%>
<%@page import="com.camila.eleganza.dao.ProductoDAO"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%
    // Obtener ID del producto desde parámetro
    String idParam = request.getParameter("id");
    Producto producto = null;
    String imagenBase64 = null;
    List<String> tallasDisponibles = null;
    
    if (idParam != null && !idParam.trim().isEmpty()) {
        try {
            int id = Integer.parseInt(idParam);
            ProductoDAO productoDAO = new ProductoDAO();
            producto = productoDAO.getProductoById(id);
            
            if (producto != null) {
                // Procesar imagen si existe
                if (producto.getImagen() != null && producto.getImagen().length > 0) {
                    imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(producto.getImagen());
                }
                
                // Obtener tallas disponibles
                tallasDisponibles = productoDAO.getTallas();
            }
            
        } catch (NumberFormatException e) {
            System.err.println("ID de producto inválido: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Si no hay producto, redirigir a productos
    if (producto == null) {
        response.sendRedirect("productos.jsp");
        return;
    }
    
    // Configurar imagen por defecto si no existe
    String imagenSrc = imagenBase64 != null ? imagenBase64 : 
        "https://dummyimage.com/600x700/ced4da/6c757d?text=" + producto.getNombre().replace(" ", "+");
    
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="<%= producto.getNombre() %> - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title><%= producto.getNombre() %> - Boutique Eleganza</title>
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
            <!-- Product section-->
            <section class="py-5">
                <div class="container px-5 my-5">
                    <!-- Breadcrumb-->
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="productos.jsp">Productos</a></li>
                            <li class="breadcrumb-item active" aria-current="page"><%= producto.getNombre() %></li>
                        </ol>
                    </nav>
                    <!-- Product details-->
                    <div class="row gx-4 gx-lg-5 align-items-center">
                        <div class="col-md-6">
                            <div class="position-relative">
                                <img class="card-img-top mb-5 mb-md-0 rounded shadow" 
                                     src="<%= imagenSrc %>" 
                                     alt="<%= producto.getNombre() %>" 
                                     style="width: 100%; height: 600px; object-fit: contain; background-color: #f8f9fa; border: 1px solid #dee2e6;" />
                                <% if (producto.getStock() <= 0) { %>
                                    <div class="position-absolute top-0 end-0 m-3">
                                        <span class="badge bg-danger">Sin Stock</span>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="small mb-1">Categoría: <%= producto.getCategoria() %></div>
                            <h1 class="display-5 fw-bolder"><%= producto.getNombre() %></h1>
                            <div class="fs-5 mb-5">
                                <span class="text-success fw-bold">
                                    <%= String.format("$%,.0f", producto.getPrecio()) %>
                                </span>
                            </div>
                            <p class="lead">
                                <%= producto.getDescripcion() != null ? producto.getDescripcion() : 
                                    "Producto de alta calidad disponible en Boutique Eleganza. Perfecta para cualquier ocasión especial." %>
                            </p>
                            
                            <form action="carrito" method="post" id="addToCartForm">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="productoId" value="<%= producto.getIdProducto() %>">
                                
                                <div class="d-flex align-items-center mb-4">
                                    <div class="me-3">
                                        <label for="size" class="form-label fw-bold">Talla:</label>
                                        <select class="form-select" id="size" name="talla" required style="width: auto; min-width: 120px;">
                                            <option value="">Seleccionar talla</option>
                                            <% if (tallasDisponibles != null && !tallasDisponibles.isEmpty()) { %>
                                                <% for (String talla : tallasDisponibles) { %>
                                                    <option value="<%= talla %>" <%= talla.equals(producto.getTalla()) ? "selected" : "" %>>
                                                        <%= talla %>
                                                    </option>
                                                <% } %>
                                            <% } else { %>
                                                <option value="<%= producto.getTalla() %>"><%= producto.getTalla() %></option>
                                            <% } %>
                                        </select>
                                    </div>
                                    <div>
                                        <label for="quantity" class="form-label fw-bold">Cantidad:</label>
                                        <input class="form-control text-center" id="quantity" name="cantidad" type="number" 
                                               value="1" min="1" max="<%= producto.getStock() %>" required 
                                               style="width: 80px;" />
                                    </div>
                                </div>
                                
                                <div class="mb-3">
                                    <% if (producto.getStock() > 0) { %>
                                        <small class="text-muted">
                                            <i class="bi bi-check-circle-fill text-success"></i> 
                                            <%= producto.getStock() %> disponibles en stock
                                        </small>
                                    <% } else { %>
                                        <small class="text-danger">
                                            <i class="bi bi-x-circle-fill"></i> 
                                            Sin stock disponible
                                        </small>
                                    <% } %>
                                </div>
                                
                                <div class="d-flex gap-2 flex-wrap">
                                    <% if (producto.getStock() > 0) { %>
                                        <button class="btn btn-primary btn-lg flex-shrink-0" type="submit">
                                            <i class="bi bi-cart-fill me-1"></i>
                                            Agregar al Carrito
                                        </button>
                                    <% } else { %>
                                        <button class="btn btn-secondary btn-lg flex-shrink-0" type="button" disabled>
                                            <i class="bi bi-cart-x me-1"></i>
                                            Sin Stock
                                        </button>
                                    <% } %>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- Product details tabs-->
                    <div class="row gx-4 gx-lg-5 mt-5">
                        <div class="col-12">
                            <ul class="nav nav-tabs" id="productTab" role="tablist">
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link active" id="description-tab" data-bs-toggle="tab" data-bs-target="#description" type="button" role="tab" aria-controls="description" aria-selected="true">Descripción</button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="details-tab" data-bs-toggle="tab" data-bs-target="#details" type="button" role="tab" aria-controls="details" aria-selected="false">Detalles</button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="care-tab" data-bs-toggle="tab" data-bs-target="#care" type="button" role="tab" aria-controls="care" aria-selected="false">Cuidados</button>
                                </li>
                            </ul>
                            <div class="tab-content" id="productTabContent">
                                <div class="tab-pane fade show active" id="description" role="tabpanel" aria-labelledby="description-tab">
                                    <div class="p-4">
                                        <h5>Descripción del Producto</h5>
                                        <p>
                                            <%= producto.getDescripcion() != null ? producto.getDescripcion() : 
                                                "Este hermoso producto ha sido diseñado pensando en la mujer moderna que busca sofisticación y estilo." %>
                                        </p>
                                        <ul>
                                            <li>Diseño moderno y sofisticado</li>
                                            <li>Materiales de alta calidad</li>
                                            <li>Perfecto para cualquier ocasión</li>
                                            <li>Disponible en talla <%= producto.getTalla() %></li>
                                            <li>Stock disponible: <%= producto.getStock() %> unidades</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="details" role="tabpanel" aria-labelledby="details-tab">
                                    <div class="p-4">
                                        <h5>Detalles del Producto</h5>
                                        <table class="table table-borderless">
                                            <tr><td><strong>Nombre:</strong></td><td><%= producto.getNombre() %></td></tr>
                                            <tr><td><strong>Categoría:</strong></td><td><%= producto.getCategoria() %></td></tr>
                                            <tr><td><strong>Precio:</strong></td><td><%= String.format("$%,.0f", producto.getPrecio()) %></td></tr>
                                            <tr><td><strong>Talla:</strong></td><td><%= producto.getTalla() %></td></tr>
                                            <tr><td><strong>Stock:</strong></td><td><%= producto.getStock() %> unidades</td></tr>
                                            <tr><td><strong>Estado:</strong></td><td><%= producto.getEstado() %></td></tr>
                                            <tr><td><strong>ID Producto:</strong></td><td>#<%= producto.getIdProducto() %></td></tr>
                                        </table>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="care" role="tabpanel" aria-labelledby="care-tab">
                                    <div class="p-4">
                                        <h5>Instrucciones de Cuidado</h5>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <ul>
                                                    <li><i class="bi bi-water text-primary"></i> Lavar según las instrucciones de la etiqueta</li>
                                                    <li><i class="bi bi-x-circle text-danger"></i> No usar blanqueador</li>
                                                    <li><i class="bi bi-thermometer-low text-info"></i> Planchar a temperatura baja</li>
                                                </ul>
                                            </div>
                                            <div class="col-md-6">
                                                <ul>
                                                    <li><i class="bi bi-circle text-warning"></i> Lavar en seco si es necesario</li>
                                                    <li><i class="bi bi-arrow-down-circle text-secondary"></i> Colgar para secar</li>
                                                    <li><i class="bi bi-shield-check text-success"></i> Guardar en lugar adecuado</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
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
        
        <script>
            // Validación del formulario
            document.getElementById('addToCartForm').addEventListener('submit', function(e) {
                const talla = document.getElementById('size').value;
                const cantidad = document.getElementById('quantity').value;
                
                if (!talla) {
                    e.preventDefault();
                    alert('Por favor selecciona una talla');
                    return;
                }
                
                if (!cantidad || cantidad < 1) {
                    e.preventDefault();
                    alert('Por favor ingresa una cantidad válida');
                    return;
                }
            });
        </script>
    </body>
</html>
