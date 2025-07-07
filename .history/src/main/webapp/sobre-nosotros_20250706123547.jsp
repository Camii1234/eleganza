<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Sobre Nosotros - Boutique Eleganza" />
        <meta name="author" content="Boutique Eleganza" />
        <title>Sobre Nosotros - Boutique Eleganza</title>
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
                            <li class="nav-item"><a class="nav-link active" href="sobre-nosotros.jsp">Sobre Nosotros</a></li>
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
            <!-- Header-->
            <header class="py-5">
                <div class="container px-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-8 col-xxl-6">
                            <div class="text-center">
                                <h1 class="fw-bolder">Sobre Boutique Eleganza</h1>
                                <p class="lead fw-normal text-muted mb-0">Nuestra historia, misión y compromiso con la moda femenina</p>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <!-- About section one-->
            <section class="py-5 bg-light">
                <div class="container px-5 my-5">
                    <div class="row gx-5 align-items-center">
                        <div class="col-lg-6"><img class="img-fluid rounded mb-5 mb-lg-0" src="img/sobrenosotros1.jpg" alt="Boutique Eleganza" /></div>
                        <div class="col-lg-6">
                            <h2 class="fw-bolder">Nuestra Historia</h2>
                            <p class="lead fw-normal text-muted mb-0">Boutique Eleganza nació en 2015 en el corazón de El Poblado, con la visión de ofrecer a las mujeres de Medellín y Antioquia moda exclusiva y de alta calidad. Fundada por un equipo apasionado por el diseño y la elegancia, nuestra boutique se ha convertido en un referente de estilo en la ciudad.</p>
                        </div>
                    </div>
                </div>
            </section>
            <!-- About section two-->
            <section class="py-5">
                <div class="container px-5 my-5">
                    <div class="row gx-5 align-items-center">
                        <div class="col-lg-6 order-first order-lg-last"><img class="img-fluid rounded mb-5 mb-lg-0" src="img/sobrenosotros2.jpg" alt="Misión y Visión" /></div>
                        <div class="col-lg-6">
                            <h2 class="fw-bolder">Nuestra Misión</h2>
                            <p class="lead fw-normal text-muted mb-4">Empoderar a las mujeres a través de la moda, ofreciendo prendas únicas que reflejen su personalidad y estilo. Creemos que cada mujer merece sentirse hermosa, segura y auténtica.</p>
                            <h2 class="fw-bolder">Nuestra Visión</h2>
                            <p class="lead fw-normal text-muted mb-0">Ser la boutique líder en Antioquia, reconocida por la calidad excepcional de nuestros productos, el servicio personalizado y nuestro compromiso con la moda sostenible y ética.</p>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Values section-->
            <section class="py-5 bg-light">
                <div class="container px-5 my-5">
                    <div class="text-center mb-5">
                        <h2 class="fw-bolder">Nuestros Valores</h2>
                        <p class="lead fw-normal text-muted mb-0">Los principios que guían cada decisión en Boutique Eleganza</p>
                    </div>
                    <div class="row gx-5">
                        <div class="col-lg-4 mb-5 mb-lg-0">
                            <div class="text-center">
                                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-heart"></i></div>
                                <h2 class="h4 fw-bolder">Calidad</h2>
                                <p>Seleccionamos cuidadosamente cada prenda, asegurando los más altos estándares de calidad en materiales y confección.</p>
                            </div>
                        </div>
                        <div class="col-lg-4 mb-5 mb-lg-0">
                            <div class="text-center">
                                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-star"></i></div>
                                <h2 class="h4 fw-bolder">Exclusividad</h2>
                                <p>Ofrecemos piezas únicas y ediciones limitadas que no encontrarás en otros lugares, para que tu estilo sea verdaderamente tuyo.</p>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="text-center">
                                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-people"></i></div>
                                <h2 class="h4 fw-bolder">Servicio</h2>
                                <p>Brindamos atención personalizada y asesoría de estilo para que cada cliente encuentre exactamente lo que busca.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Team section-->
            <section class="py-5">
                <div class="container px-5 my-5">
                    <div class="text-center">
                        <h2 class="fw-bolder">Nuestro Equipo</h2>
                        <p class="lead fw-normal text-muted mb-5">Conoce a las personas que hacen posible la magia de Boutique Eleganza</p>
                    </div>
                    <div class="row gx-5 row-cols-1 row-cols-sm-2 row-cols-xl-4 justify-content-center">
                        <div class="col mb-5 mb-xl-0">
                            <div class="text-center">
                                <img class="img-fluid rounded-circle mb-4 px-4" src="img/sobrenosotros4.jpg" alt="Ana López" />
                                <h5 class="fw-bolder">Ana López</h5>
                                <div class="fst-italic text-muted">Asesora de Moda</div>
                            </div>
                        </div>
                        <div class="col mb-5 mb-sm-0">
                            <div class="text-center">
                                <img class="img-fluid rounded-circle mb-4 px-4" src="img/sobrenosotros5.jpg" alt="Carmen Silva" />
                                <h5 class="fw-bolder">Carmen Silva</h5>
                                <div class="fst-italic text-muted">Gerente de Ventas</div>
                            </div>
                        </div>
                        <div class="col mb-5">
                            <div class="text-center">
                                <img class="img-fluid rounded-circle mb-4 px-4" src="img/sobrenosotros6.jpg" alt="Laura Gómez" />
                                <h5 class="fw-bolder">Laura Gómez</h5>
                                <div class="fst-italic text-muted">Coordinadora de Compras</div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Call to action-->
            <aside class="py-5 bg-primary bg-gradient rounded-3 text-white">
                <div class="container px-5 my-5">
                    <div class="text-center">
                        <h2 class="display-4 fw-bolder mb-4">¡Descubre tu estilo único!</h2>
                        <p class="lead mb-5">Visita nuestra boutique en El Poblado o explora nuestra colección en línea. Te esperamos para ayudarte a encontrar las piezas perfectas para tu guardarropa.</p>
                        <div class="d-grid gap-3 d-sm-flex justify-content-sm-center">
                            <a class="btn btn-outline-light btn-lg px-5 py-3 me-sm-3 fs-6 fw-bolder" href="productos.jsp">Ver Productos</a>
                            <a class="btn btn-light btn-lg px-5 py-3 fs-6 fw-bolder" href="contacto.jsp">Contáctanos</a>
                        </div>
                    </div>
                </div>
            </aside>
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
