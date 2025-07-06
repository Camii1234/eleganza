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
                        <div class="col-lg-8">
                            <div class="card shadow border-0 rounded-3 overflow-hidden">
                                <div class="card-header bg-primary text-center">
                                    <h3 class="text-white mb-0">Crear Cuenta</h3>
                                </div>
                                <div class="card-body p-5">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-person-plus-fill fs-1 text-primary"></i>
                                        <p class="text-muted">Únete a Boutique Eleganza y disfruta de beneficios exclusivos</p>
                                    </div>
                                    <form>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="nombre" class="form-label">Nombre completo *</label>
                                                <input type="text" class="form-control" id="nombre" name="nombre" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="correo" class="form-label">Correo electrónico *</label>
                                                <input type="email" class="form-control" id="correo" name="correo" required>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="telefono" class="form-label">Teléfono *</label>
                                                <input type="tel" class="form-control" id="telefono" name="telefono" placeholder="+57 300 123 4567" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="pais" class="form-label">País *</label>
                                                <select class="form-select" id="pais" name="pais" required>
                                                    <option value="">Seleccionar país</option>
                                                    <option value="colombia">Colombia</option>
                                                    <option value="venezuela">Venezuela</option>
                                                    <option value="ecuador">Ecuador</option>
                                                    <option value="panama">Panamá</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="ciudad" class="form-label">Ciudad *</label>
                                                <input type="text" class="form-control" id="ciudad" name="ciudad" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="codigoPostal" class="form-label">Código Postal</label>
                                                <input type="text" class="form-control" id="codigoPostal" name="codigoPostal">
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="calle" class="form-label">Dirección (Calle) *</label>
                                            <input type="text" class="form-control" id="calle" name="calle" placeholder="Carrera, calle, número de casa" required>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="password" class="form-label">Contraseña *</label>
                                                <input type="password" class="form-control" id="password" name="password" required>
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
                                        <div class="form-check mb-3">
                                            <input class="form-check-input" type="checkbox" id="terms" required>
                                            <label class="form-check-label" for="terms">
                                                Acepto los <a href="#!" class="text-decoration-none">términos y condiciones</a> y la <a href="#!" class="text-decoration-none">política de privacidad</a> *
                                            </label>
                                        </div>
                                        <div class="form-check mb-4">
                                            <input class="form-check-input" type="checkbox" id="newsletter">
                                            <label class="form-check-label" for="newsletter">
                                                Quiero recibir promociones y novedades por correo electrónico
                                            </label>
                                        </div>
                                        <div class="d-grid mb-3">
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
    </body>
</html>