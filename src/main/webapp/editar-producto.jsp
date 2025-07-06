<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                                <p class="lead fw-normal text-muted mb-4">Modifica la información del producto</p>
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
                                    <form>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="nombre" class="form-label">Nombre del Producto *</label>
                                                <input type="text" class="form-control" id="nombre" name="nombre" value="Vestido Elegante" required>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="precio" class="form-label">Precio *</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">$</span>
                                                    <input type="number" class="form-control" id="precio" name="precio" value="150000" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="categoria" class="form-label">Categoría *</label>
                                                <select class="form-select" id="categoria" name="categoria" required>
                                                    <option value="">Seleccionar categoría</option>
                                                    <option value="vestidos" selected>Vestidos</option>
                                                    <option value="blusas">Blusas</option>
                                                    <option value="pantalones">Pantalones</option>
                                                    <option value="faldas">Faldas</option>
                                                    <option value="chaquetas">Chaquetas</option>
                                                    <option value="accesorios">Accesorios</option>
                                                    <option value="calzado">Calzado</option>
                                                    <option value="ropa-interior">Ropa Interior</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="stock" class="form-label">Stock Disponible *</label>
                                                <input type="number" class="form-control" id="stock" name="stock" min="0" value="15" required>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="tallas" class="form-label">Tallas Disponibles *</label>
                                                <div class="form-check-group">
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="checkbox" id="tallaXS" value="XS">
                                                        <label class="form-check-label" for="tallaXS">XS</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="checkbox" id="tallaS" value="S" checked>
                                                        <label class="form-check-label" for="tallaS">S</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="checkbox" id="tallaM" value="M" checked>
                                                        <label class="form-check-label" for="tallaM">M</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="checkbox" id="tallaL" value="L" checked>
                                                        <label class="form-check-label" for="tallaL">L</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="checkbox" id="tallaXL" value="XL">
                                                        <label class="form-check-label" for="tallaXL">XL</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="estado" class="form-label">Estado *</label>
                                                <select class="form-select" id="estado" name="estado" required>
                                                    <option value="activo" selected>Activo</option>
                                                    <option value="inactivo">Inactivo</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="imagen-actual" class="form-label">Imagen Actual</label>
                                            <div class="mb-2">
                                                <img src="https://dummyimage.com/200x200/ced4da/6c757d?text=Vestido+Elegante" alt="Producto actual" class="img-thumbnail">
                                            </div>
                                            <label for="imagen" class="form-label">Cambiar Imagen</label>
                                            <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                                            <div class="form-text">Formatos aceptados: JPG, PNG, GIF. Tamaño máximo: 5MB</div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="descripcion" class="form-label">Descripción</label>
                                            <textarea class="form-control" id="descripcion" name="descripcion" rows="4" placeholder="Descripción detallada del producto...">Elegante vestido perfecto para ocasiones especiales. Confeccionado en tela de alta calidad con un diseño moderno y sofisticado.</textarea>
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
