<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Boutique de ropa femenina en Medellín, Antioquia. Moda exclusiva y elegante." />
        <meta name="author" content="Boutique Eleganza" />
        <title>Boutique Eleganza - Moda Femenina Exclusiva</title>
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
            <!-- Include Navigation -->
            <jsp:include page="includes/navbar.jsp" />
            
            <!-- Mostrar mensaje de perfil eliminado -->
            <% 
            String mensaje = request.getParameter("mensaje");
            if ("perfil_eliminado".equals(mensaje)) { 
            %>
                <div class="alert alert-success alert-dismissible fade show m-3" role="alert">
                    <i class="bi bi-check-circle"></i> 
                    <strong>Perfil eliminado exitosamente.</strong> Lamentamos verte partir. Esperamos verte pronto nuevamente.
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>
            
            <!-- Header-->
            <header class="bg-dark py-5">
                <div class="container px-5">
                    <div class="row gx-5 align-items-center justify-content-center">
                        <div class="col-lg-8 col-xl-7 col-xxl-6">
                            <div class="my-5 text-center text-xl-start">
                                <h1 class="display-5 fw-bolder text-white mb-2">Boutique Eleganza</h1>
                                <p class="lead fw-normal text-white-50 mb-4">Descubre la moda femenina más exclusiva y elegante en el corazón de Medellín, Antioquia. Ropa única para mujeres que valoran el estilo y la calidad.</p>
                                <div class="d-grid gap-3 d-sm-flex justify-content-sm-center justify-content-xl-start">
                                    <a class="btn btn-pink btn-lg px-4 me-sm-3" href="productos.jsp" style="background-color:#fd6eb4 !important; color:#fff !important; border-color:#fd6eb4 !important; font-family:'Playfair Display',serif !important;">Ver Productos</a>
                                    <a class="btn btn-outline-light btn-lg px-4" href="sobre-nosotros.jsp">Conoce Más</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-5 col-xxl-6 d-none d-xl-block text-center"><img class="img-fluid rounded-3 my-5" src="img/inicio.jpg" alt="Boutique Eleganza" /></div>
                    </div>
                </div>
            </header>
            <!-- Features section-->
            <section class="py-5" id="features">
                <div class="container px-5 my-5">
                    <div class="row gx-5">
                        <div class="col-lg-4 mb-5 mb-lg-0"><h2 class="fw-bolder mb-0">¿Por qué elegir Boutique Eleganza?</h2></div>
                        <div class="col-lg-8">
                            <div class="row gx-5 row-cols-1 row-cols-md-2">
                                <div class="col mb-5 h-100">
                                    <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-gem"></i></div>
                                    <h2 class="h5">Calidad Premium</h2>
                                    <p class="mb-0">Seleccionamos cuidadosamente cada prenda, utilizando solo materiales de la más alta calidad para garantizar durabilidad y comodidad.</p>
                                </div>
                                <div class="col mb-5 h-100">
                                    <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-palette"></i></div>
                                    <h2 class="h5">Diseño Exclusivo</h2>
                                    <p class="mb-0">Nuestras piezas son únicas y están diseñadas para mujeres que buscan destacar con estilo y elegancia en cada ocasión.</p>
                                </div>
                                <div class="col mb-5 mb-md-0 h-100">
                                    <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-heart"></i></div>
                                    <h2 class="h5">Atención Personalizada</h2>
                                    <p class="mb-0">Nuestro equipo de asesoras de moda te ayudará a encontrar el look perfecto que realce tu personalidad y figura.</p>
                                </div>
                                <div class="col h-100">
                                    <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-truck"></i></div>
                                    <h2 class="h5">Envío Rápido</h2>
                                    <p class="mb-0">Recibe tus prendas favoritas en la comodidad de tu hogar con nuestro servicio de envío express a toda Colombia.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Testimonial section-->
            <div class="py-5 bg-light">
                <div class="container px-5 my-5">
                    <div class="row gx-5 justify-content-center">
                        <div class="col-lg-10 col-xl-7">
                            <div class="text-center">
                                <div class="fs-4 mb-4 fst-italic">"Boutique Eleganza me ha transformado completamente. Cada prenda que compro aquí me hace sentir elegante y segura. La calidad es excepcional y el servicio al cliente es incomparable."</div>
                                <div class="d-flex align-items-center justify-content-center">
                                    <img class="rounded-circle me-3" src="https://dummyimage.com/40x40/ced4da/6c757d?text=MC" alt="María Camila" />
                                    <div class="fw-bold">
                                        María Camila González
                                        <span class="fw-bold text-primary mx-1">/</span>
                                        Cliente VIP
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                    <!-- Call to action-->
                    <aside class="bg-primary bg-gradient rounded-3 p-4 p-sm-5 mt-5">
                        <div class="d-flex align-items-center justify-content-between flex-column flex-xl-row text-center text-xl-start">
                            <div class="mb-4 mb-xl-0">
                                <div class="fs-3 fw-bold text-white">Nuevas colecciones, directamente para ti.</div>
                                <div class="text-white-50">Regístrate en nuestra boutique y recibe las últimas tendencias y ofertas exclusivas.</div>
                            </div>
                            <div class="ms-xl-4">
                                <div class="input-group mb-2">
                                    <input class="form-control" type="text" placeholder="Tu correo electrónico..." aria-label="Correo electrónico" aria-describedby="button-newsletter" />
                                    <a class="btn btn-outline-light" href="registro.jsp" id="button-newsletter">Regístrate</a>
                                </div>
                                <div class="small text-white-50">Respetamos tu privacidad y nunca compartiremos tu información.</div>
                            </div>
                        </div>
                    </aside>
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
