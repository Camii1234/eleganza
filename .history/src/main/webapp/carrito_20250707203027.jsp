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
                    <div class="row">
                        <!-- Cart items-->
                        <div class="col-lg-8">
                            <div class="card shadow border-0 rounded-3 mb-4">
                                <div class="card-header bg-primary">
                                    <h5 class="text-white mb-0">Productos en tu carrito (3)</h5>
                                </div>
                                <div class="card-body p-0">
                                    <!-- Cart item 1-->
                                    <div class="border-bottom p-4">
                                        <div class="row align-items-center">
                                            <div class="col-md-3">
                                                <img src="https://dummyimage.com/150x150/ced4da/6c757d?text=Vestido+Elegante" alt="Vestido Elegante" class="img-fluid rounded">
                                            </div>
                                            <div class="col-md-6">
                                                <h6 class="mb-1">Vestido Elegante</h6>
                                                <p class="text-muted mb-2">Talla: M | Color: Negro</p>
                                                <p class="fw-bold text-primary mb-0">$150,000</p>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="d-flex align-items-center justify-content-between">
                                                    <div class="input-group input-group-max-width">
                                                        <button class="btn btn-outline-secondary" type="button">-</button>
                                                        <input type="text" class="form-control text-center" value="1" readonly title="Cantidad de Vestido Elegante" placeholder="Cantidad">
                                                        <button class="btn btn-outline-secondary" type="button">+</button>
                                                    </div>
                                                    <button class="btn btn-outline-danger btn-sm ms-2" aria-label="Eliminar producto">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Cart item 2-->
                                    <div class="border-bottom p-4">
                                        <div class="row align-items-center">
                                            <div class="col-md-3">
                                                <img src="https://dummyimage.com/150x150/ced4da/6c757d?text=Blusa+Casual" alt="Blusa Casual" class="img-fluid rounded">
                                            </div>
                                            <div class="col-md-6">
                                                <h6 class="mb-1">Blusa Casual</h6>
                                                <p class="text-muted mb-2">Talla: S | Color: Blanco</p>
                                                <p class="fw-bold text-primary mb-0">$85,000</p>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="d-flex align-items-center justify-content-between">
                                                    <div class="input-group input-group-max-width">
                                                        <button class="btn btn-outline-secondary" type="button">-</button>
                                                        <input type="text" class="form-control text-center" value="2" readonly title="Cantidad de Blusa Casual" placeholder="Cantidad">
                                                        <button class="btn btn-outline-secondary" type="button">+</button>
                                                    </div>
                                                    <button class="btn btn-outline-danger btn-sm ms-2" aria-label="Eliminar producto">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Cart item 3-->
                                    <div class="p-4">
                                        <div class="row align-items-center">
                                            <div class="col-md-3">
                                                <img src="https://dummyimage.com/150x150/ced4da/6c757d?text=Pantalon+Formal" alt="Pantalón Formal" class="img-fluid rounded">
                                            </div>
                                            <div class="col-md-6">
                                                <h6 class="mb-1">Pantalón Formal</h6>
                                                <p class="text-muted mb-2">Talla: M | Color: Negro</p>
                                                <p class="fw-bold text-primary mb-0">$120,000</p>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="d-flex align-items-center justify-content-between">
                                                    <div class="input-group input-group-max-width">
                                                        <button class="btn btn-outline-secondary" type="button">-</button>
                                                        <input type="text" class="form-control text-center" value="1" readonly title="Cantidad de Pantalón Formal" placeholder="Cantidad">
                                                        <button class="btn btn-outline-secondary" type="button">+</button>
                                                    </div>
                                                    <button class="btn btn-outline-danger btn-sm ms-2" aria-label="Eliminar producto">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-footer bg-light">
                                    <div class="d-flex justify-content-between">
                                        <a href="productos.jsp" class="btn btn-outline-primary">
                                            <i class="bi bi-arrow-left"></i> Continuar Comprando
                                        </a>
                                        <button class="btn btn-outline-danger">
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
                                        <span>Subtotal (4 productos):</span>
                                        <span>$440,000</span>
                                    </div>
                                    <div class="d-flex justify-content-between mb-2">
                                        <span>Envío:</span>
                                        <span>$15,000</span>
                                    </div>
                                    <div class="d-flex justify-content-between mb-2">
                                        <span>Descuento:</span>
                                        <span class="text-success">-$20,000</span>
                                    </div>
                                    <hr>
                                    <div class="d-flex justify-content-between mb-4">
                                        <strong>Total:</strong>
                                        <strong class="text-primary">$435,000</strong>
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
