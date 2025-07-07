<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Registro - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Registro - Boutique Eleganza</title>
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
                                    <li><a class="dropdown-item" href="login.jsp">Iniciar Sesión</a></li>
                                    <li><a class="dropdown-item active" href="registro.jsp">Registrarse</a></li>
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
                        <div class="col-lg-8 col-md-10">
                            <div class="card shadow border-0 rounded-3 overflow-hidden">
                                <div class="card-header bg-primary text-center">
                                    <h3 class="text-white mb-0">Crear Cuenta</h3>
                                </div>
                                <div class="card-body p-5">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-person-plus-fill fs-1 text-primary"></i>
                                        <p class="text-muted">Únete a Boutique Eleganza y disfruta de una experiencia de compra única</p>
                                    </div>
                                    
                                    <!-- Mostrar mensajes de error o éxito -->
                                    <% if (request.getAttribute("error") != null) { %>
                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                            <i class="bi bi-exclamation-triangle"></i> <%= request.getAttribute("error") %>
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                        </div>
                                    <% } %>
                                    
                                    <% if (request.getAttribute("success") != null) { %>
                                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                                            <i class="bi bi-check-circle"></i> <%= request.getAttribute("success") %>
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                        </div>
                                    <% } %>
                                    
                                    <form id="registroForm" action="registro" method="post">
                                        <div class="row">
                                            <!-- Información Personal -->
                                            <div class="col-md-6">
                                                <h5 class="text-primary mb-3"><i class="bi bi-person"></i> Información Personal</h5>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="number" class="form-control" id="documento" name="documento" placeholder="Número de identificación" required>
                                                    <label for="documento">Número de identificación</label>
                                                </div>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre completo" required>
                                                    <label for="nombre">Nombre completo</label>
                                                </div>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="email" class="form-control" id="correo" name="correo" placeholder="correo@ejemplo.com" required>
                                                    <label for="correo">Correo electrónico</label>
                                                </div>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="tel" class="form-control" id="telefono" name="telefono" placeholder="Teléfono" required>
                                                    <label for="telefono">Teléfono</label>
                                                </div>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
                                                    <label for="password">Contraseña</label>
                                                </div>
                                            </div>
                                            
                                            <!-- Información de Dirección -->
                                            <div class="col-md-6">
                                                <h5 class="text-primary mb-3"><i class="bi bi-geo-alt"></i> Información de Dirección</h5>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="text" class="form-control" id="calle" name="calle" placeholder="Dirección" required>
                                                    <label for="calle">Dirección</label>
                                                </div>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="text" class="form-control" id="ciudad" name="ciudad" placeholder="Ciudad" required>
                                                    <label for="ciudad">Ciudad</label>
                                                </div>
                                                
                                                <div class="form-floating mb-3">
                                                    <input type="text" class="form-control" id="codigoPostal" name="codigoPostal" placeholder="Código postal" required>
                                                    <label for="codigoPostal">Código postal</label>
                                                </div>
                                                
                                                <div class="form-floating mb-3">
                                                    <select class="form-select" id="pais" name="pais" required>
                                                        <option value="">Selecciona un país</option>
                                                        <option value="Colombia">Colombia</option>
                                                        <option value="México">México</option>
                                                        <option value="Argentina">Argentina</option>
                                                        <option value="Chile">Chile</option>
                                                        <option value="Perú">Perú</option>
                                                        <option value="Ecuador">Ecuador</option>
                                                        <option value="Venezuela">Venezuela</option>
                                                        <option value="Bolivia">Bolivia</option>
                                                        <option value="Paraguay">Paraguay</option>
                                                        <option value="Uruguay">Uruguay</option>
                                                        <option value="España">España</option>
                                                        <option value="Estados Unidos">Estados Unidos</option>
                                                        <option value="Otro">Otro</option>
                                                    </select>
                                                    <label for="pais">País</label>
                                                </div>
                                                
                                                <div class="form-check mb-3">
                                                    <input class="form-check-input" type="checkbox" id="acceptTerms" required>
                                                    <label class="form-check-label" for="acceptTerms">
                                                        Acepto los <a href="#" class="text-decoration-none">términos y condiciones</a> y la <a href="#" class="text-decoration-none">política de privacidad</a>
                                                    </label>
                                                </div>
                                                
                                                <div class="form-check mb-3">
                                                    <input class="form-check-input" type="checkbox" id="newsletter">
                                                    <label class="form-check-label" for="newsletter">
                                                        Quiero recibir ofertas y novedades por correo electrónico
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="d-grid mt-4">
                                            <button class="btn btn-primary btn-lg" type="submit">
                                                <i class="bi bi-person-plus"></i> Crear Cuenta
                                            </button>
                                        </div>
                                    </form>
                                </div>
                                <div class="card-footer bg-light text-center">
                                    <p class="mb-0">¿Ya tienes una cuenta? 
                                        <a href="login.jsp" class="text-decoration-none fw-bold">Inicia sesión aquí</a>
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
        
        <!-- Script para validación del formulario -->
        <script>
            document.getElementById('registroForm').addEventListener('submit', function(e) {
                const password = document.getElementById('password').value;
                
                if (password.length < 6) {
                    e.preventDefault();
                    alert('La contraseña debe tener al menos 6 caracteres.');
                    return false;
                }
                
                return true;
            });
        </script>
    </body>
</html>
