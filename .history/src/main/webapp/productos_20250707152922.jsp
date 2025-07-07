<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>
<%@page import="com.camila.eleganza.model.Producto"%>
<%@page import="com.camila.eleganza.dao.ProductoDAO"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%
    ProductoDAO productoDAO = new ProductoDAO();
    List<Producto> productos = null;
    
    try {
        // Aplicar filtros si se enviaron
        String categoriaFiltro = request.getParameter("categoria");
        String ordenFiltro = request.getParameter("orden");
        
        if (categoriaFiltro != null || ordenFiltro != null) {
            if (categoriaFiltro != null && !categoriaFiltro.isEmpty() && ordenFiltro != null && !ordenFiltro.isEmpty()) {
                productos = productoDAO.getProductosConFiltros(categoriaFiltro, ordenFiltro);
            } else if (categoriaFiltro != null && !categoriaFiltro.isEmpty()) {
                productos = productoDAO.getProductosByCategoria(categoriaFiltro);
            } else if (ordenFiltro != null && !ordenFiltro.isEmpty()) {
                productos = productoDAO.getProductosOrdenadosPorPrecio(ordenFiltro);
            } else {
                productos = productoDAO.getAllProductos();
            }
        } else {
            productos = productoDAO.getAllProductos();
        }
        
        // Si productos es null o está vacío, inicializar con lista vacía
        if (productos == null) {
            productos = new java.util.ArrayList<>();
        }
        
    } catch (Exception e) {
        System.err.println("Error al obtener productos: " + e.getMessage());
        e.printStackTrace();
        productos = new java.util.ArrayList<>();
    }
    
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Productos - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Productos - Boutique Eleganza</title>
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
            
            <!-- Header-->
            <header class="bg-dark py-5">
                <div class="container px-5 my-5">
                    <div class="text-center text-white">
                        <h1 class="display-4 fw-bolder">Nuestra Colección</h1>
                        <p class="lead fw-normal text-white-50 mb-0">Descubre la moda femenina más exclusiva</p>
                    </div>
                </div>
            </header>
            <!-- Section-->
            <section class="py-5">
                <div class="container px-5">
                    <!-- Filters-->
                    <div class="row mb-4">
                        <div class="col-lg-3">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="mb-0">Filtros</h5>
                                </div>
                                <div class="card-body">
                                    <form method="GET" action="productos.jsp">
                                        <div class="mb-3">
                                            <label for="categoria" class="form-label">Categoría</label>
                                            <select class="form-select" id="categoria" name="categoria">
                                                <option value="">Todas las categorías</option>
                                                <%
                                                    try {
                                                        List<String> categorias = productoDAO.getCategorias();
                                                        String categoriaSeleccionada = request.getParameter("categoria");
                                                        for (String cat : categorias) {
                                                %>
                                                <option value="<%= cat %>" 
                                                    <%= (categoriaSeleccionada != null && categoriaSeleccionada.equals(cat)) ? "selected" : "" %>>
                                                    <%= cat %>
                                                </option>
                                                <%      }
                                                    } catch (Exception e) {
                                                        System.err.println("Error al obtener categorías: " + e.getMessage());
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="precio" class="form-label">Precio</label>
                                            <select class="form-select" id="precio" name="orden">
                                                <option value="">Sin ordenar</option>
                                                <option value="ASC" <%= "ASC".equals(request.getParameter("orden")) ? "selected" : "" %>>Precio: menor a mayor</option>
                                                <option value="DESC" <%= "DESC".equals(request.getParameter("orden")) ? "selected" : "" %>>Precio: mayor a menor</option>
                                            </select>
                                        </div>
                                        <button type="submit" class="btn btn-primary w-100">Aplicar Filtros</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-9">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h5 class="mb-0">Mostrando <%= productos.size() %> productos</h5>
                            </div>
                            <!-- Products grid-->
                            <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                                <%
                                    for (Producto producto : productos) {
                                        String imagenSrc = "https://dummyimage.com/450x300/ced4da/6c757d?text=" + producto.getNombre().replace(" ", "+");
                                        
                                        // Si hay imagen en la base de datos, usarla
                                        if (producto.getImagen() != null && producto.getImagen().length > 0) {
                                            String imagenBase64 = Base64.getEncoder().encodeToString(producto.getImagen());
                                            imagenSrc = "data:image/jpeg;base64," + imagenBase64;
                                        }
                                %>
                                <div class="col mb-5">
                                    <div class="card h-100">
                                        <img class="card-img-top" src="<%= imagenSrc %>" alt="<%= producto.getNombre() %>" style="height: 300px; object-fit: cover;" />
                                        <div class="card-body p-4">
                                            <div class="text-center">
                                                <h5 class="fw-bolder"><%= producto.getNombre() %></h5>
                                                <span class="text-success fw-bold">
                                                    <%= String.format("$%,.0f", producto.getPrecio()) %>
                                                </span>
                                                <% if (producto.getStock() > 0) { %>
                                                    <div class="small text-muted">Stock: <%= producto.getStock() %></div>
                                                <% } else { %>
                                                    <div class="small text-danger">Sin stock</div>
                                                <% } %>
                                            </div>
                                        </div>
                                        <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                            <div class="text-center">
                                                <a class="btn btn-outline-primary mt-auto" 
                                                   href="producto.jsp?id=<%= producto.getIdProducto() %>">
                                                   Ver Producto
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% } %>
                                
                                <% if (productos.isEmpty()) { %>
                                <div class="col-12">
                                    <div class="text-center py-5">
                                        <h4 class="text-muted">No se encontraron productos</h4>
                                        <p>Prueba cambiando los filtros de búsqueda</p>
                                    </div>
                                </div>
                                <% } %>
                            </div>
                            <!-- Pagination-->
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item">
                                        <a class="page-link" href="#" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
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
