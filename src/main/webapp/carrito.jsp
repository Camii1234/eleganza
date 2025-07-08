<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>
<%@page import="com.camila.eleganza.model.Carrito"%>
<%@page import="com.camila.eleganza.model.ItemCarrito"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%
    // Obtener el carrito de la sesión (ya creado por CarritoFilter)
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    // El filtro ya garantiza que existe, pero por seguridad:
    if (carrito == null) {
        carrito = new Carrito();
        session.setAttribute("carrito", carrito);
    }
    
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Carrito de Compras - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Carrito de Compras - Boutique Eleganza</title>
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
            
            .alert.position-fixed {
                top: 20px;
                right: 20px;
                z-index: 1055;
                min-width: 300px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
            
            .input-group-max-width {
                max-width: 120px;
            }
            
            .btn:disabled {
                opacity: 0.5;
                cursor: not-allowed;
            }
            
            .cantidad-input {
                font-weight: bold;
                color: #495057;
            }
            
            .btn-increase, .btn-decrease {
                min-width: 35px;
                font-weight: bold;
            }
            
            .btn-remove {
                min-width: 35px;
            }
            
            .spinner-border-sm {
                width: 1rem;
                height: 1rem;
            }
            
            .cart-item {
                transition: all 0.3s ease;
            }
            
            .cart-item:hover {
                background-color: #f8f9fa;
            }
            
            .loading-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 9999;
            }
            
            .loading-spinner {
                color: white;
                font-size: 2rem;
            }
            
        </style>
    </head>
    <body class="d-flex flex-column h-100">
        <main class="flex-shrink-0">
            <!-- Navigation-->
            <%@ include file="includes/navbar.jsp" %>
            
            <!-- Mensajes de error y éxito -->
            <% 
            String error = request.getParameter("error");
            String success = request.getParameter("success");
            
            if (error != null) { 
            %>
                <div class="alert alert-danger alert-dismissible fade show position-fixed" role="alert" style="top: 20px; right: 20px; z-index: 1055; min-width: 300px;">
                    <i class="bi bi-exclamation-circle"></i>
                    <% if ("carrito-vacio".equals(error)) { %>
                        El carrito está vacío. Agrega productos antes de proceder al pago.
                    <% } else if ("pdf-generation".equals(error)) { %>
                        Error al generar la factura PDF. Intenta nuevamente.
                    <% } else if ("sesion".equals(error)) { %>
                        Debes iniciar sesión para proceder al pago.
                    <% } else { %>
                        Ha ocurrido un error inesperado.
                    <% } %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>
            
            <% if (success != null) { %>
                <div class="alert alert-success alert-dismissible fade show position-fixed" role="alert" style="top: 20px; right: 20px; z-index: 1055; min-width: 300px;">
                    <i class="bi bi-check-circle"></i>
                    <% if ("factura-generada".equals(success)) { %>
                        Factura generada exitosamente. El archivo se está descargando.
                    <% } %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>
            
            <!-- Page content-->
            <section class="py-5">
                <div class="container px-5">
                    <!-- Page title-->
                    <div class="row justify-content-center">
                        <div class="col-lg-8 col-xxl-6">
                            <div class="text-center my-5">
                                <h1 class="fw-bolder mb-3">Carrito de Compras</h1>
                                <p class="lead fw-normal text-muted mb-4">
                                    <% if (carrito.estaVacio()) { %>
                                        Tu carrito está vacío
                                    <% } else { %>
                                        Revisa tus productos seleccionados
                                    <% } %>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- Cart content-->
                    <% if (carrito.estaVacio()) { %>
                        <!-- Carrito vacío -->
                        <div class="row justify-content-center">
                            <div class="col-lg-8">
                                <div class="card shadow border-0 rounded-3 mb-4">
                                    <div class="card-body p-5 text-center">
                                        <i class="bi bi-cart-x display-1 text-muted mb-4"></i>
                                        <h4 class="mb-3">Tu carrito está vacío</h4>
                                        <p class="text-muted mb-4">¡Agrega algunos productos increíbles a tu carrito!</p>
                                        <a href="productos.jsp" class="btn btn-primary btn-lg">
                                            <i class="bi bi-shop me-2"></i>Explorar Productos
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <% } else { %>
                        <!-- Carrito con productos -->
                        <div class="row">
                            <!-- Cart items-->
                            <div class="col-lg-8">
                                <div class="card shadow border-0 rounded-3 mb-4">
                                    <div class="card-header bg-primary">
                                        <h5 class="text-white mb-0">Productos en tu carrito (<%= carrito.getCantidadTotal() %>)</h5>
                                    </div>
                                    <div class="card-body p-0">
                                        <% 
                                        List<ItemCarrito> items = carrito.getItems();
                                        for (int i = 0; i < items.size(); i++) {
                                            ItemCarrito item = items.get(i);
                                            String imagenBase64 = null;
                                            
                                            // Procesar imagen si existe
                                            if (item.getImagen() != null && item.getImagen().length > 0) {
                                                imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(item.getImagen());
                                            }
                                            
                                            // Configurar imagen por defecto si no existe
                                            String imagenSrc = imagenBase64 != null ? imagenBase64 : 
                                                "https://dummyimage.com/150x150/ced4da/6c757d?text=" + item.getNombre().replace(" ", "+");
                                        %>
                                        <!-- Cart item -->
                                        <div class="cart-item <%= i < items.size() - 1 ? "border-bottom" : "" %> p-4" data-producto-id="<%= item.getIdProducto() %>" data-talla="<%= item.getTalla() %>">
                                            <div class="row align-items-center">
                                                <div class="col-md-3">
                                                    <img src="<%= imagenSrc %>" alt="<%= item.getNombre() %>" class="img-fluid rounded">
                                                </div>
                                                <div class="col-md-6">
                                                    <h6 class="mb-1"><%= item.getNombre() %></h6>
                                                    <p class="text-muted mb-2">
                                                        Talla: <%= item.getTalla() %> | 
                                                        Categoría: <%= item.getCategoria() %>
                                                    </p>
                                                    <p class="fw-bold text-primary mb-0">
                                                        <%= String.format("$%,.0f", item.getPrecio()) %>
                                                    </p>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="d-flex align-items-center justify-content-between">
                                                        <div class="input-group input-group-max-width">
                                                            <button class="btn btn-outline-secondary btn-decrease" type="button" 
                                                                    data-producto-id="<%= item.getIdProducto() %>" 
                                                                    data-talla="<%= item.getTalla() %>" 
                                                                    data-cantidad="<%= item.getCantidad() %>">-</button>
                                                            <input type="text" class="form-control text-center cantidad-input" 
                                                                   value="<%= item.getCantidad() %>" readonly 
                                                                   title="Cantidad de <%= item.getNombre() %>" 
                                                                   placeholder="Cantidad"
                                                                   id="cantidad-<%= item.getIdProducto() %>-<%= item.getTalla() %>"
                                                                   aria-label="Cantidad">
                                                            <button class="btn btn-outline-secondary btn-increase" type="button"
                                                                    data-producto-id="<%= item.getIdProducto() %>" 
                                                                    data-talla="<%= item.getTalla() %>" 
                                                                    data-cantidad="<%= item.getCantidad() %>"
                                                                    data-stock="<%= item.getStock() %>">+</button>
                                                        </div>
                                                        <button class="btn btn-outline-danger btn-sm ms-2 btn-remove" 
                                                                aria-label="Eliminar producto"
                                                                data-producto-id="<%= item.getIdProducto() %>" 
                                                                data-talla="<%= item.getTalla() %>">
                                                            <i class="bi bi-trash"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <% } %>
                                    </div>
                                    <div class="card-footer bg-light">
                                        <div class="d-flex justify-content-between">
                                            <a href="productos.jsp" class="btn btn-outline-primary">
                                                <i class="bi bi-arrow-left"></i> Continuar Comprando
                                            </a>
                                            <button class="btn btn-outline-danger" id="btn-vaciar-carrito">
                                                <i class="bi bi-trash"></i> Vaciar Carrito
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Order summary-->
                            <div class="col-lg-4">
                                <div class="card shadow border-0 rounded-3">
                                    <div class="card-header bg-success">
                                        <h5 class="text-white mb-0">Resumen del Pedido</h5>
                                    </div>
                                    <div class="card-body p-4">
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Subtotal (<span class="cantidad-productos"><%= carrito.getCantidadTotal() %></span> productos):</span>
                                            <span class="subtotal-amount"><%= String.format("$%,.0f", carrito.getSubtotal()) %></span>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Envío:</span>
                                            <span><%= String.format("$%,.0f", carrito.getEnvio()) %></span>
                                        </div>
                                        <% if (carrito.getDescuento() > 0) { %>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Descuento:</span>
                                            <span class="text-success descuento-amount">-<%= String.format("$%,.0f", carrito.getDescuento()) %></span>
                                        </div>
                                        <% } %>
                                        <% if (carrito.getImpuestos() > 0) { %>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>IVA (19%):</span>
                                            <span class="impuestos-amount"><%= String.format("$%,.0f", carrito.getImpuestos()) %></span>
                                        </div>
                                        <% } %>
                                        <hr>
                                        <div class="d-flex justify-content-between mb-4">
                                            <strong>Total:</strong>
                                            <strong class="text-primary total-amount"><%= String.format("$%,.0f", carrito.getTotal()) %></strong>
                                        </div>
                                        <div class="mb-3">
                                            <label for="coupon" class="form-label">Código de descuento</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="coupon" placeholder="Ingresa tu código">
                                                <button class="btn btn-outline-secondary" type="button">Aplicar</button>
                                            </div>
                                        </div>
                                        <div class="d-grid">
                                            <form action="generar-factura-pdf" method="post" style="display: inline;" onsubmit="return confirmarPago();">
                                                <button type="submit" class="btn btn-primary btn-lg" id="btn-proceder-pago">
                                                    <i class="bi bi-file-earmark-pdf"></i> Generar Factura PDF
                                                </button>
                                            </form>
                                            <small class="text-muted mt-2 text-center">
                                                <i class="bi bi-info-circle"></i> Se generará una factura PDF con todos los detalles de tu compra
                                            </small>
                                        </div>
                                    </div>
                                </div>
                                <!-- Shipping info-->
                                <div class="card shadow border-0 rounded-3 mt-4">
                                    <div class="card-header bg-info">
                                        <h6 class="text-white mb-0">Información de Envío</h6>
                                    </div>
                                    <div class="card-body p-4">
                                        <div class="d-flex align-items-center mb-3">
                                            <i class="bi bi-truck text-primary me-2"></i>
                                            <div>
                                                <small class="fw-bold">Envío estándar</small><br>
                                                <small class="text-muted">3-5 días hábiles</small>
                                            </div>
                                        </div>
                                        <div class="d-flex align-items-center mb-3">
                                            <i class="bi bi-shield-check text-success me-2"></i>
                                            <div>
                                                <small class="fw-bold">Compra segura</small><br>
                                                <small class="text-muted">Protección SSL</small>
                                            </div>
                                        </div>
                                        <div class="d-flex align-items-center">
                                            <i class="bi bi-arrow-clockwise text-warning me-2"></i>
                                            <div>
                                                <small class="fw-bold">Devoluciones</small><br>
                                                <small class="text-muted">30 días para cambios</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <% } %>
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
            function confirmarPago() {
                const nombre = '<%= session.getAttribute("usuarioNombre") != null ? session.getAttribute("usuarioNombre") : "Usuario" %>';
                const total = '<%= String.format("$%,.0f", carrito.getTotal()) %>';
                
                return confirm(`¡Hola ${nombre}!\n\n¿Confirmas que deseas generar la factura por un total de ${total}?\n\nSe descargará un archivo PDF con todos los detalles de tu compra.`);
            }
            
            // Auto-cerrar alertas después de 5 segundos
            setTimeout(function() {
                const alerts = document.querySelectorAll('.alert');
                alerts.forEach(function(alert) {
                    const bsAlert = new bootstrap.Alert(alert);
                    bsAlert.close();
                });
            }, 5000);
        </script>
    </body>
</html>
