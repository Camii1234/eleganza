<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>
<%@page import="com.camila.eleganza.dao.ProductoDAO"%>
<%@page import="com.camila.eleganza.dao.UsuarioDAO"%>
<%@page import="com.camila.eleganza.model.Producto"%>
<%@page import="com.camila.eleganza.model.Usuario"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%
    ProductoDAO productoDAO = new ProductoDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    List<Producto> productos = productoDAO.getAllProductosAdmin();
    List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
    
    DecimalFormat formatter = new DecimalFormat("#,###");
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    
    // Obtener el contexto de la aplicación
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Panel de administración - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Administración - Boutique Eleganza</title>
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
                                    <li><a class="dropdown-item active" href="admin.jsp">Administración</a></li>
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
                                <h1 class="fw-bolder mb-3">Panel de Administración</h1>
                                <p class="lead fw-normal text-muted mb-4">Gestiona productos, usuarios y pedidos de Boutique Eleganza</p>
                            </div>
                        </div>
                    </div>

                    <!-- Mensaje de éxito -->
                    <%
                        String mensaje = request.getParameter("mensaje");
                        String texto = request.getParameter("texto");
                        
                        if (mensaje != null && "producto_agregado".equals(mensaje)) {
                    %>
                    <div class="row justify-content-center mb-4">
                        <div class="col-lg-8">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <i class="bi bi-check-circle"></i> ¡Producto agregado exitosamente!
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </div>
                    </div>
                    <%
                        } else if (mensaje != null && "success".equals(mensaje) && texto != null) {
                    %>
                    <div class="row justify-content-center mb-4">
                        <div class="col-lg-8">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <i class="bi bi-check-circle"></i> <%= texto %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </div>
                    </div>
                    <%
                        } else if (mensaje != null && "error".equals(mensaje) && texto != null) {
                    %>
                    <div class="row justify-content-center mb-4">
                        <div class="col-lg-8">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle"></i> <%= texto %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>

                    <!-- Statistics Dashboard-->
                    <div class="row mb-5">
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card bg-primary text-white h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h5 class="card-title">Total Productos</h5>
                                            <h2 class="fw-bold"><%= productos.size() %></h2>
                                        </div>
                                        <div class="align-self-center">
                                            <i class="bi bi-box-seam fs-1"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card bg-success text-white h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h5 class="card-title">Usuarios Registrados</h5>
                                            <h2 class="fw-bold"><%= usuarios.size() %></h2>
                                        </div>
                                        <div class="align-self-center">
                                            <i class="bi bi-people fs-1"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card bg-warning text-white h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h5 class="card-title">Pedidos Pendientes</h5>
                                            <h2 class="fw-bold">12</h2>
                                        </div>
                                        <div class="align-self-center">
                                            <i class="bi bi-clock fs-1"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card bg-info text-white h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h5 class="card-title">Ventas del Mes</h5>
                                            <h2 class="fw-bold">$2.4M</h2>
                                        </div>
                                        <div class="align-self-center">
                                            <i class="bi bi-graph-up fs-1"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Tabs Navigation -->
                    <ul class="nav nav-tabs mb-4" id="adminTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="productos-tab" data-bs-toggle="tab" data-bs-target="#productos" type="button" role="tab" aria-controls="productos" aria-selected="true">
                                <i class="bi bi-box-seam me-2"></i>Productos
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="usuarios-tab" data-bs-toggle="tab" data-bs-target="#usuarios" type="button" role="tab" aria-controls="usuarios" aria-selected="false">
                                <i class="bi bi-people me-2"></i>Usuarios
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="pedidos-tab" data-bs-toggle="tab" data-bs-target="#pedidos" type="button" role="tab" aria-controls="pedidos" aria-selected="false">
                                <i class="bi bi-cart-check me-2"></i>Pedidos
                            </button>
                        </li>
                    </ul>

                    <!-- Tab Content -->
                    <div class="tab-content" id="adminTabsContent">
                        <!-- Productos Tab -->
                        <div class="tab-pane fade show active" id="productos" role="tabpanel" aria-labelledby="productos-tab">
                            <div class="card shadow border-0 rounded-3 overflow-hidden">
                                <div class="card-header bg-primary">
                                    <h5 class="text-white mb-0">Gestión de Productos</h5>
                                </div>
                                <div class="card-body p-4">
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h6 class="mb-0">Lista de Productos</h6>
                                        <a href="agregar-producto.jsp" class="btn btn-success">
                                            <i class="bi bi-plus-circle"></i> Agregar Producto
                                        </a>
                                    </div>
                                    <!-- Products table-->
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Imagen</th>
                                                    <th>Nombre</th>
                                                    <th>Precio</th>
                                                    <th>Talla</th>
                                                    <th>Categoría</th>
                                                    <th>Stock</th>
                                                    <th>Estado</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% 
                                                if (productos != null && !productos.isEmpty()) {
                                                    for (Producto producto : productos) {
                                                        String imagenSrc = "https://dummyimage.com/50x50/ced4da/6c757d";
                                                        if (producto.getImagen() != null && producto.getImagen().length > 0) {
                                                            String imagenBase64 = Base64.getEncoder().encodeToString(producto.getImagen());
                                                            imagenSrc = "data:image/jpeg;base64," + imagenBase64;
                                                        }
                                                        
                                                        String estadoClass = "Activo".equals(producto.getEstado()) ? "bg-success" : "bg-warning";
                                                %>
                                                <tr>
                                                    <td>
                                                        <img src="<%= imagenSrc %>" alt="<%= producto.getNombre() %>" class="rounded" style="width: 50px; height: 50px; object-fit: cover;">
                                                    </td>
                                                    <td><%= producto.getNombre() %></td>
                                                    <td>$<%= formatter.format(producto.getPrecio()) %></td>
                                                    <td><%= producto.getTalla() %></td>
                                                    <td><%= producto.getCategoria() %></td>
                                                    <td><%= producto.getStock() %></td>
                                                    <td>
                                                        <span class="badge <%= estadoClass %>"><%= producto.getEstado() %></span>
                                                    </td>
                                                    <td>
                                                        <a href="editar-producto.jsp?id=<%= producto.getIdProducto() %>" class="btn btn-sm btn-outline-primary">
                                                            <i class="bi bi-pencil"></i> Editar
                                                        </a>
                                                        <button class="btn btn-sm btn-outline-danger" onclick="eliminarProducto('<%= producto.getIdProducto() %>')">
                                                            <i class="bi bi-trash"></i> Eliminar
                                                        </button>
                                                    </td>
                                                </tr>
                                                <% 
                                                    }
                                                } else {
                                                %>
                                                <tr>
                                                    <td colspan="8" class="text-center text-muted">
                                                        <i class="bi bi-box-seam fs-1 d-block mb-2"></i>
                                                        No hay productos registrados
                                                    </td>
                                                </tr>
                                                <% 
                                                }
                                                %>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Usuarios Tab -->
                        <div class="tab-pane fade" id="usuarios" role="tabpanel" aria-labelledby="usuarios-tab">
                            <div class="card shadow border-0 rounded-3 overflow-hidden">
                                <div class="card-header bg-success">
                                    <h5 class="text-white mb-0">Gestión de Usuarios</h5>
                                </div>
                                <div class="card-body p-4">
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h6 class="mb-0">Usuarios Registrados</h6>
                                        <div class="input-group w-auto">
                                            <input type="text" class="form-control" placeholder="Buscar usuario..." id="buscarUsuario">
                                            <button class="btn btn-outline-secondary" type="button" title="Buscar">
                                                <i class="bi bi-search"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <!-- Users table-->
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Nombre</th>
                                                    <th>Correo</th>
                                                    <th>Teléfono</th>
                                                    <th>Ciudad</th>
                                                    <th>País</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% 
                                                if (usuarios != null && !usuarios.isEmpty()) {
                                                    for (Usuario usuario : usuarios) {
                                                %>
                                                <tr>
                                                    <td><%= usuario.getIdUsuario() %></td>
                                                    <td><%= usuario.getNombre() != null ? usuario.getNombre() : "No especificado" %></td>
                                                    <td><%= usuario.getCorreo() != null ? usuario.getCorreo() : "No especificado" %></td>
                                                    <td><%= usuario.getTelefono() != null ? usuario.getTelefono() : "No especificado" %></td>
                                                    <td><%= usuario.getCiudad() != null ? usuario.getCiudad() : "No especificado" %></td>
                                                    <td><%= usuario.getPais() != null ? usuario.getPais() : "No especificado" %></td>
                                                    <td>
                                                        
                                                        <button class="btn btn-sm btn-outline-danger" title="Eliminar usuario" onclick="eliminarUsuario('<%= usuario.getIdUsuario() %>')">
                                                            <i class="bi bi-trash"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                                <% 
                                                    }
                                                } else {
                                                %>
                                                <tr>
                                                    <td colspan="7" class="text-center text-muted">
                                                        <i class="bi bi-people fs-1 d-block mb-2"></i>
                                                        No hay usuarios registrados
                                                    </td>
                                                </tr>
                                                <% 
                                                }
                                                %>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Pedidos Tab -->
                        <div class="tab-pane fade" id="pedidos" role="tabpanel" aria-labelledby="pedidos-tab">
                            <div class="card shadow border-0 rounded-3 overflow-hidden">
                                <div class="card-header bg-warning">
                                    <h5 class="text-white mb-0">Gestión de Pedidos</h5>
                                </div>
                                <div class="card-body p-4">
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h6 class="mb-0">Lista de Pedidos</h6>
                                        <div class="d-flex gap-2">
                                            <label for="estado-pedido-select" class="form-label visually-hidden">Filtrar por estado de pedido</label>
                                            <select id="estado-pedido-select" class="form-select" style="width: auto;" title="Filtrar por estado de pedido">
                                                <option>Todos los estados</option>
                                                <option>Pendiente</option>
                                                <option>Procesando</option>
                                                <option>Enviado</option>
                                                <option>Entregado</option>
                                                <option>Cancelado</option>
                                            </select>
                                        </div>
                                    </div>
                                    <!-- Orders table-->
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>N° Pedido</th>
                                                    <th>Cliente</th>
                                                    <th>Productos</th>
                                                    <th>Total</th>
                                                    <th>Fecha</th>
                                                    <th>Estado</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>#ORD-001</td>
                                                    <td>María Camila González</td>
                                                    <td>Vestido Elegante Rosa, Blusa Casual</td>
                                                    <td>$235,000</td>
                                                    <td>15/10/2024</td>
                                                    <td><span class="badge bg-warning">Pendiente</span></td>
                                                    <td>
                                                        <button class="btn btn-sm btn-outline-info" title="Ver detalles">
                                                            <i class="bi bi-eye"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-success" title="Marcar como procesando">
                                                            <i class="bi bi-check"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-danger" title="Cancelar pedido">
                                                            <i class="bi bi-x"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>#ORD-002</td>
                                                    <td>Ana Sofía Restrepo</td>
                                                    <td>Pantalón Formal Ejecutivo</td>
                                                    <td>$120,000</td>
                                                    <td>14/10/2024</td>
                                                    <td><span class="badge bg-primary">Procesando</span></td>
                                                    <td>
                                                        <button class="btn btn-sm btn-outline-info" title="Ver detalles">
                                                            <i class="bi bi-eye"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-success" title="Marcar como enviado">
                                                            <i class="bi bi-truck"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-danger" title="Cancelar pedido">
                                                            <i class="bi bi-x"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>#ORD-003</td>
                                                    <td>Laura Morales Jiménez</td>
                                                    <td>Falda Midi Elegante, Blusa Casual</td>
                                                    <td>$180,000</td>
                                                    <td>13/10/2024</td>
                                                    <td><span class="badge bg-info">Enviado</span></td>
                                                    <td>
                                                        <button class="btn btn-sm btn-outline-info" title="Ver detalles">
                                                            <i class="bi bi-eye"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-success" title="Marcar como entregado">
                                                            <i class="bi bi-check-circle"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-secondary" title="Tracking">
                                                            <i class="bi bi-geo-alt"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>#ORD-004</td>
                                                    <td>Catalina Mendoza Ruiz</td>
                                                    <td>Vestido Elegante Rosa</td>
                                                    <td>$150,000</td>
                                                    <td>12/10/2024</td>
                                                    <td><span class="badge bg-success">Entregado</span></td>
                                                    <td>
                                                        <button class="btn btn-sm btn-outline-info" title="Ver detalles">
                                                            <i class="bi bi-eye"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-secondary" title="Generar factura">
                                                            <i class="bi bi-file-earmark-pdf"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>#ORD-005</td>
                                                    <td>Isabella Torres Vargas</td>
                                                    <td>Blusa Casual Primavera</td>
                                                    <td>$85,000</td>
                                                    <td>11/10/2024</td>
                                                    <td><span class="badge bg-danger">Cancelado</span></td>
                                                    <td>
                                                        <button class="btn btn-sm btn-outline-info" title="Ver detalles">
                                                            <i class="bi bi-eye"></i>
                                                        </button>
                                                        <button class="btn btn-sm btn-outline-secondary" disabled title="Pedido cancelado">
                                                            <i class="bi bi-x-circle"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
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
            function eliminarProducto(id) {
                console.log('=== INICIO eliminarProducto ===');
                console.log('ID del producto:', id);
                
                if (confirm('¿Está seguro de que desea eliminar este producto?\n\nEsta acción no se puede deshacer y el producto será eliminado permanentemente de la base de datos.')) {
                    // Mostrar mensaje de carga
                    const overlay = document.createElement('div');
                    overlay.innerHTML = '<div class="d-flex justify-content-center align-items-center h-100"><div class="spinner-border text-primary" role="status"><span class="visually-hidden">Eliminando...</span></div></div>';
                    overlay.className = 'position-fixed top-0 start-0 w-100 h-100 bg-white bg-opacity-75 d-flex justify-content-center align-items-center';
                    overlay.style.zIndex = '9999';
                    document.body.appendChild(overlay);
                    
                    // Construir URL
                    const contextPath = '<%= contextPath %>';
                    const url = contextPath + '/EliminarProductoServlet?id=' + encodeURIComponent(id);
                    console.log('Context path:', contextPath);
                    console.log('URL completa:', url);
                    
                    // Hacer petición AJAX
                    console.log('Enviando petición fetch...');
                    fetch(url, {
                        method: 'GET',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }
                    })
                    .then(response => {
                        console.log('Respuesta recibida - Status:', response.status);
                        console.log('Headers:', response.headers);
                        console.log('OK:', response.ok);
                        
                        if (!response.ok) {
                            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
                        }
                        
                        return response.text(); // Primero obtener como texto para debug
                    })
                    .then(text => {
                        console.log('Texto de respuesta:', text);
                        
                        // Intentar parsear como JSON
                        try {
                            const data = JSON.parse(text);
                            console.log('JSON parseado:', data);
                            document.body.removeChild(overlay);
                            
                            if (data.success) {
                                alert('Producto eliminado exitosamente');
                                window.location.reload(); // Recargar la página para actualizar la lista
                            } else {
                                alert('Error al eliminar producto: ' + data.message);
                            }
                        } catch (jsonError) {
                            console.error('Error al parsear JSON:', jsonError);
                            document.body.removeChild(overlay);
                            alert('Error: Respuesta no válida del servidor');
                        }
                    })
                    .catch(error => {
                        console.error('Error en fetch:', error);
                        console.error('Tipo de error:', typeof error);
                        console.error('Mensaje:', error.message);
                        document.body.removeChild(overlay);
                        alert('Error de conexión al eliminar producto: ' + error.message);
                    });
                } else {
                    console.log('Eliminación cancelada por el usuario');
                }
                console.log('=== FIN eliminarProducto ===');
            }
            
            function verDetalleUsuario(id) {
                // Implementar lógica para ver detalles del usuario
                alert('Ver detalles del usuario ID: ' + id);
                // Podrías redirigir a una página de detalles o mostrar un modal
            }
            
            function editarUsuario(id) {
                // Implementar lógica para editar usuario
                window.location.href = 'editar-usuario.jsp?id=' + id;
            }
            
            function eliminarUsuario(id) {
                console.log('=== INICIO eliminarUsuario ===');
                console.log('ID del usuario:', id);
                
                if (confirm('¿Está seguro de que desea eliminar este usuario?\n\nEsta acción no se puede deshacer y el usuario será eliminado permanentemente de la base de datos.')) {
                    // Mostrar mensaje de carga
                    const overlay = document.createElement('div');
                    overlay.innerHTML = '<div class="d-flex justify-content-center align-items-center h-100"><div class="spinner-border text-primary" role="status"><span class="visually-hidden">Eliminando...</span></div></div>';
                    overlay.className = 'position-fixed top-0 start-0 w-100 h-100 bg-white bg-opacity-75 d-flex justify-content-center align-items-center';
                    overlay.style.zIndex = '9999';
                    document.body.appendChild(overlay);
                    
                    // Construir URL
                    const contextPath = '<%= contextPath %>';
                    const url = contextPath + '/EliminarUsuarioServlet?id=' + encodeURIComponent(id);
                    console.log('Context path:', contextPath);
                    console.log('URL completa:', url);
                    
                    // Hacer petición AJAX
                    console.log('Enviando petición fetch...');
                    fetch(url, {
                        method: 'GET',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }
                    })
                    .then(response => {
                        console.log('Respuesta recibida - Status:', response.status);
                        console.log('Headers:', response.headers);
                        console.log('OK:', response.ok);
                        
                        if (!response.ok) {
                            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
                        }
                        
                        return response.text(); // Primero obtener como texto para debug
                    })
                    .then(text => {
                        console.log('Texto de respuesta:', text);
                        
                        // Intentar parsear como JSON
                        try {
                            const data = JSON.parse(text);
                            console.log('JSON parseado:', data);
                            document.body.removeChild(overlay);
                            
                            if (data.success) {
                                alert('Usuario eliminado exitosamente');
                                window.location.reload(); // Recargar la página para actualizar la lista
                            } else {
                                alert('Error al eliminar usuario: ' + data.message);
                            }
                        } catch (jsonError) {
                            console.error('Error al parsear JSON:', jsonError);
                            document.body.removeChild(overlay);
                            alert('Error: Respuesta no válida del servidor');
                        }
                    })
                    .catch(error => {
                        console.error('Error en fetch:', error);
                        console.error('Tipo de error:', typeof error);
                        console.error('Mensaje:', error.message);
                        document.body.removeChild(overlay);
                        alert('Error de conexión al eliminar usuario: ' + error.message);
                    });
                } else {
                    console.log('Eliminación cancelada por el usuario');
                }
                console.log('=== FIN eliminarUsuario ===');
            }
            
            // Funcionalidad de búsqueda de usuarios
            document.getElementById('buscarUsuario').addEventListener('input', function() {
                const searchTerm = this.value.toLowerCase();
                const rows = document.querySelectorAll('#usuarios tbody tr');
                
                rows.forEach(row => {
                    const text = row.textContent.toLowerCase();
                    row.style.display = text.includes(searchTerm) ? '' : 'none';
                });
            });
        </script>
    </body>
</html>
