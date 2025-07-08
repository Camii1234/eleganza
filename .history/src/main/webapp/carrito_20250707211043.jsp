<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>
<%@page import="com.camila.eleganza.model.Carrito"%>
<%@page import="com.camila.eleganza.model.ItemCarrito"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%
    // Obtener el carrito de la sesión
    Carrito carrito = (Carrito) session.getAttribute("carrito");
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
                            <li class="nav-item"><a class="nav-link active" href="carrito.jsp"><i class="bi bi-cart"></i> Carrito</a></li>
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
                                        <div class="<%= i < items.size() - 1 ? "border-bottom" : "" %> p-4">
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
                                                                   id="cantidad-<%= item.getIdProducto() %>-<%= item.getTalla() %>">
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
                                            <span>Subtotal (<%= carrito.getCantidadTotal() %> productos):</span>
                                            <span><%= String.format("$%,.0f", carrito.getSubtotal()) %></span>
                                        </div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Envío:</span>
                                            <span><%= String.format("$%,.0f", carrito.getEnvio()) %></span>
                                        </div>
                                        <% if (carrito.getDescuento() > 0) { %>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Descuento:</span>
                                            <span class="text-success">-<%= String.format("$%,.0f", carrito.getDescuento()) %></span>
                                        </div>
                                        <% } %>
                                        <hr>
                                        <div class="d-flex justify-content-between mb-4">
                                            <strong>Total:</strong>
                                            <strong class="text-primary"><%= String.format("$%,.0f", carrito.getTotal()) %></strong>
                                        </div>
                                        <div class="mb-3">
                                            <label for="coupon" class="form-label">Código de descuento</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="coupon" placeholder="Ingresa tu código">
                                                <button class="btn btn-outline-secondary" type="button">Aplicar</button>
                                            </div>
                                        </div>
                                        <div class="d-grid">
                                            <button class="btn btn-primary btn-lg">
                                                <i class="bi bi-credit-card"></i> Proceder al Pago
                                            </button>
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
            // Función para mostrar mensajes temporales
            function mostrarMensaje(mensaje, tipo = 'info') {
                // Crear el elemento del mensaje
                const messageDiv = document.createElement('div');
                const alertClass = tipo === 'success' ? 'success' : 'info';
                messageDiv.className = 'alert alert-' + alertClass + ' alert-dismissible fade show position-fixed';
                messageDiv.style.cssText = 'top: 20px; right: 20px; z-index: 1055; min-width: 300px;';
                messageDiv.innerHTML = 
                    mensaje +
                    '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>';
                
                // Agregar al body
                document.body.appendChild(messageDiv);
                
                // Eliminar automáticamente después de 3 segundos
                setTimeout(() => {
                    if (messageDiv.parentNode) {
                        messageDiv.parentNode.removeChild(messageDiv);
                    }
                }, 3000);
            }
            
            // Función para mostrar/ocultar indicador de carga
            function toggleLoading(show) {
                const buttons = document.querySelectorAll('.btn-increase, .btn-decrease, .btn-remove, #btn-vaciar-carrito');
                buttons.forEach(btn => {
                    btn.disabled = show;
                    if (show) {
                        btn.classList.add('disabled');
                    } else {
                        btn.classList.remove('disabled');
                    }
                });
            }
            
            // Función para actualizar cantidad
            function actualizarCantidad(productoId, talla, nuevaCantidad) {
                if (nuevaCantidad < 0) return;
                
                toggleLoading(true);
                
                const formData = new FormData();
                formData.append('action', 'update');
                formData.append('productoId', productoId);
                formData.append('talla', talla);
                formData.append('cantidad', nuevaCantidad);
                
                fetch('carrito', {
                    method: 'POST',
                    body: formData,
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest'
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        location.reload();
                    } else {
                        toggleLoading(false);
                        mostrarMensaje(data.message || 'Error al actualizar la cantidad', 'error');
                    }
                })
                .catch(error => {
                    toggleLoading(false);
                    console.error('Error:', error);
                    mostrarMensaje('Error al actualizar la cantidad', 'error');
                });
            }
            
            // Función para eliminar producto
            function eliminarProducto(productoId, talla) {
                if (!confirm('¿Estás seguro de que quieres eliminar este producto del carrito?')) {
                    return;
                }
                
                toggleLoading(true);
                
                const formData = new FormData();
                formData.append('action', 'remove');
                formData.append('productoId', productoId);
                formData.append('talla', talla);
                
                fetch('carrito', {
                    method: 'POST',
                    body: formData,
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest'
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        // Mostrar mensaje de éxito si está disponible
                        if (data.message) {
                            mostrarMensaje(data.message, 'success');
                        }
                        // Recargar la página después de un breve delay
                        setTimeout(() => {
                            location.reload();
                        }, 1000);
                    } else {
                        toggleLoading(false);
                        mostrarMensaje(data.message || 'Error al eliminar el producto', 'error');
                    }
                })
                .catch(error => {
                    toggleLoading(false);
                    console.error('Error:', error);
                    mostrarMensaje('Error al eliminar el producto', 'error');
                });
            }
            
            // Función para vaciar carrito
            function vaciarCarrito() {
                if (!confirm('¿Estás seguro de que quieres vaciar todo el carrito?')) {
                    return;
                }
                
                toggleLoading(true);
                
                const formData = new FormData();
                formData.append('action', 'clear');
                
                fetch('carrito', {
                    method: 'POST',
                    body: formData,
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest'
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        // Mostrar mensaje de éxito
                        mostrarMensaje(data.message || 'Carrito vaciado exitosamente', 'success');
                        // Recargar la página después de un breve delay
                        setTimeout(() => {
                            location.reload();
                        }, 1000);
                    } else {
                        toggleLoading(false);
                        mostrarMensaje(data.message || 'Error al vaciar el carrito', 'error');
                    }
                })
                .catch(error => {
                    toggleLoading(false);
                    console.error('Error:', error);
                    mostrarMensaje('Error al vaciar el carrito', 'error');
                });
            }
            
            // Event listeners
            document.addEventListener('DOMContentLoaded', function() {
                // Botones de aumentar cantidad
                document.querySelectorAll('.btn-increase').forEach(button => {
                    button.addEventListener('click', function() {
                        const productoId = this.dataset.productoId;
                        const talla = this.dataset.talla;
                        const cantidadActual = parseInt(this.dataset.cantidad);
                        const stock = parseInt(this.dataset.stock);
                        
                        if (cantidadActual < stock) {
                            actualizarCantidad(productoId, talla, cantidadActual + 1);
                        } else {
                            alert('No hay más stock disponible');
                        }
                    });
                });
                
                // Botones de disminuir cantidad
                document.querySelectorAll('.btn-decrease').forEach(button => {
                    button.addEventListener('click', function() {
                        const productoId = this.dataset.productoId;
                        const talla = this.dataset.talla;
                        const cantidadActual = parseInt(this.dataset.cantidad);
                        
                        if (cantidadActual > 1) {
                            actualizarCantidad(productoId, talla, cantidadActual - 1);
                        } else {
                            // Si la cantidad es 1, preguntar si quiere eliminar el producto
                            if (confirm('¿Quieres eliminar este producto del carrito?')) {
                                eliminarProducto(productoId, talla);
                            }
                        }
                    });
                });
                
                // Botones de eliminar
                document.querySelectorAll('.btn-remove').forEach(button => {
                    button.addEventListener('click', function() {
                        const productoId = this.dataset.productoId;
                        const talla = this.dataset.talla;
                        eliminarProducto(productoId, talla);
                    });
                });
                
                // Botón de vaciar carrito
                const btnVaciarCarrito = document.getElementById('btn-vaciar-carrito');
                if (btnVaciarCarrito) {
                    btnVaciarCarrito.addEventListener('click', vaciarCarrito);
                }
            });
        </script>
    </body>
</html>
