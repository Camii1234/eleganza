/*!
* Start Bootstrap - Modern Business v5.0.7 (https://startbootstrap.com/template-overviews/modern-business)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-modern-business/blob/master/LICENSE)
*/

// Carrito de compras - Funcionalidades del lado del cliente
let carritoLocal = {
    items: [],
    subtotal: 0,
    envio: 15000,
    descuento: 0,
    total: 0
};

// Inicializar cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    // Cargar carrito desde localStorage
    cargarCarritoDesdeStorage();
    
    // Inicializar funcionalidades del carrito
    initCarritoFunctionality();
    
    // Actualizar contador del carrito en la navbar
    actualizarContadorCarrito();
    
    // Si estamos en la página del carrito, renderizar los productos
    if (window.location.pathname.includes('carrito.jsp')) {
        renderizarCarrito();
    }
});

// Función para cargar carrito desde localStorage
function cargarCarritoDesdeStorage() {
    const carritoGuardado = localStorage.getItem('eleganza_carrito');
    if (carritoGuardado) {
        carritoLocal = JSON.parse(carritoGuardado);
    }
}

// Función para guardar carrito en localStorage
function guardarCarritoEnStorage() {
    localStorage.setItem('eleganza_carrito', JSON.stringify(carritoLocal));
}

// Función para inicializar las funcionalidades del carrito
function initCarritoFunctionality() {
    // Botones de aumentar cantidad
    document.querySelectorAll('.btn-increase').forEach(button => {
        button.addEventListener('click', function() {
            const productoId = this.dataset.productoId;
            const talla = this.dataset.talla;
            const cantidadActual = parseInt(this.dataset.cantidad);
            const stock = parseInt(this.dataset.stock);
            
            if (cantidadActual < stock) {
                actualizarCantidadProducto(productoId, talla, cantidadActual + 1);
            } else {
                mostrarMensaje('No hay más stock disponible', 'warning');
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
                actualizarCantidadProducto(productoId, talla, cantidadActual - 1);
            } else {
                // Si la cantidad es 1, preguntar si quiere eliminar el producto
                if (confirm('¿Quieres eliminar este producto del carrito?')) {
                    eliminarProductoDelCarrito(productoId, talla);
                }
            }
        });
    });
    
    // Botones de eliminar
    document.querySelectorAll('.btn-remove').forEach(button => {
        button.addEventListener('click', function() {
            const productoId = this.dataset.productoId;
            const talla = this.dataset.talla;
            eliminarProductoDelCarrito(productoId, talla);
        });
    });
    
    // Botón de vaciar carrito
    const btnVaciarCarrito = document.getElementById('btn-vaciar-carrito');
    if (btnVaciarCarrito) {
        btnVaciarCarrito.addEventListener('click', vaciarCarritoCompleto);
    }
}

// Función para actualizar cantidad de un producto
function actualizarCantidadProducto(productoId, talla, nuevaCantidad) {
    const item = carritoLocal.items.find(item => 
        item.idProducto == productoId && item.talla === talla
    );
    
    if (item) {
        item.cantidad = nuevaCantidad;
        calcularTotales();
        guardarCarritoEnStorage();
        
        // Actualizar la interfaz
        const cantidadInput = document.getElementById(`cantidad-${productoId}-${talla}`);
        if (cantidadInput) {
            cantidadInput.value = nuevaCantidad;
        }
        
        // Actualizar los data attributes de los botones
        const btnIncrease = document.querySelector(`[data-producto-id="${productoId}"][data-talla="${talla}"].btn-increase`);
        const btnDecrease = document.querySelector(`[data-producto-id="${productoId}"][data-talla="${talla}"].btn-decrease`);
        
        if (btnIncrease) btnIncrease.dataset.cantidad = nuevaCantidad;
        if (btnDecrease) btnDecrease.dataset.cantidad = nuevaCantidad;
        
        actualizarResumenPedido();
        actualizarContadorCarrito();
        mostrarMensaje('Cantidad actualizada correctamente', 'success');
    }
}

// Función para eliminar un producto del carrito
function eliminarProductoDelCarrito(productoId, talla) {
    if (!confirm('¿Estás seguro de que quieres eliminar este producto del carrito?')) {
        return;
    }
    
    carritoLocal.items = carritoLocal.items.filter(item => 
        !(item.idProducto == productoId && item.talla === talla)
    );
    
    calcularTotales();
    guardarCarritoEnStorage();
    
    // Eliminar el elemento del DOM
    const cartItem = document.querySelector(`[data-producto-id="${productoId}"][data-talla="${talla}"].cart-item`);
    if (cartItem) {
        cartItem.remove();
    }
    
    // Si no quedan productos, mostrar mensaje de carrito vacío
    if (carritoLocal.items.length === 0) {
        location.reload();
    }
    
    actualizarResumenPedido();
    actualizarContadorCarrito();
    mostrarMensaje('Producto eliminado del carrito', 'success');
}

// Función para vaciar el carrito completo
function vaciarCarritoCompleto() {
    if (!confirm('¿Estás seguro de que quieres vaciar todo el carrito?')) {
        return;
    }
    
    carritoLocal.items = [];
    calcularTotales();
    guardarCarritoEnStorage();
    
    mostrarMensaje('Carrito vaciado correctamente', 'success');
    
    // Recargar la página para mostrar el carrito vacío
    setTimeout(() => {
        location.reload();
    }, 1000);
}

// Función para calcular totales
function calcularTotales() {
    carritoLocal.subtotal = 0;
    
    carritoLocal.items.forEach(item => {
        carritoLocal.subtotal += item.precio * item.cantidad;
    });
    
    // Aplicar descuento si el subtotal es mayor a 200000
    if (carritoLocal.subtotal > 200000) {
        carritoLocal.descuento = carritoLocal.subtotal * 0.05; // 5% de descuento
    } else {
        carritoLocal.descuento = 0;
    }
    
    carritoLocal.total = carritoLocal.subtotal + carritoLocal.envio - carritoLocal.descuento;
}

// Función para actualizar el resumen del pedido
function actualizarResumenPedido() {
    const cantidadTotal = carritoLocal.items.reduce((sum, item) => sum + item.cantidad, 0);
    
    // Actualizar subtotal
    const subtotalElement = document.querySelector('.subtotal-amount');
    if (subtotalElement) {
        subtotalElement.textContent = `$${carritoLocal.subtotal.toLocaleString()}`;
    }
    
    // Actualizar cantidad de productos
    const cantidadElements = document.querySelectorAll('.cantidad-productos');
    cantidadElements.forEach(el => {
        el.textContent = cantidadTotal;
    });
    
    // Actualizar descuento
    const descuentoElement = document.querySelector('.descuento-amount');
    if (descuentoElement) {
        descuentoElement.textContent = `$${carritoLocal.descuento.toLocaleString()}`;
    }
    
    // Actualizar total
    const totalElement = document.querySelector('.total-amount');
    if (totalElement) {
        totalElement.textContent = `$${carritoLocal.total.toLocaleString()}`;
    }
}

// Función para actualizar el contador del carrito en la navbar
function actualizarContadorCarrito() {
    const cantidadTotal = carritoLocal.items.reduce((sum, item) => sum + item.cantidad, 0);
    const cartCountElements = document.querySelectorAll('.cart-count, .badge');
    
    cartCountElements.forEach(element => {
        element.textContent = cantidadTotal;
        if (cantidadTotal > 0) {
            element.style.display = 'inline';
        } else {
            element.style.display = 'none';
        }
    });
}

// Función para renderizar el carrito (usado en carrito.jsp)
function renderizarCarrito() {
    if (carritoLocal.items.length === 0) {
        return; // JSP ya maneja el caso de carrito vacío
    }
    
    actualizarResumenPedido();
    actualizarContadorCarrito();
}

// Función para mostrar mensajes temporales
function mostrarMensaje(mensaje, tipo = 'info') {
    // Crear el elemento del mensaje
    const messageDiv = document.createElement('div');
    let alertClass = '';
    let iconClass = '';
    
    switch(tipo) {
        case 'success':
            alertClass = 'alert-success';
            iconClass = 'bi-check-circle';
            break;
        case 'error':
            alertClass = 'alert-danger';
            iconClass = 'bi-exclamation-triangle';
            break;
        case 'warning':
            alertClass = 'alert-warning';
            iconClass = 'bi-exclamation-triangle';
            break;
        default:
            alertClass = 'alert-info';
            iconClass = 'bi-info-circle';
    }
    
    messageDiv.className = `alert ${alertClass} alert-dismissible fade show position-fixed`;
    messageDiv.style.cssText = 'top: 20px; right: 20px; z-index: 1055; min-width: 300px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);';
    messageDiv.innerHTML = 
        `<i class="bi ${iconClass} me-2"></i>` +
        mensaje +
        '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>';
    
    // Agregar al body
    document.body.appendChild(messageDiv);
    
    // Eliminar automáticamente después de 5 segundos
    setTimeout(() => {
        if (messageDiv.parentNode) {
            messageDiv.parentNode.removeChild(messageDiv);
        }
    }, 5000);
}

// Función para agregar producto al carrito (desde otras páginas)
function agregarAlCarrito(producto) {
    const existeItem = carritoLocal.items.find(item => 
        item.idProducto == producto.idProducto && item.talla === producto.talla
    );
    
    if (existeItem) {
        existeItem.cantidad += producto.cantidad;
    } else {
        carritoLocal.items.push(producto);
    }
    
    calcularTotales();
    guardarCarritoEnStorage();
    actualizarContadorCarrito();
    
    mostrarMensaje('Producto agregado al carrito', 'success');
}

// Función para limpiar carrito al cerrar sesión
function limpiarCarritoAlCerrarSesion() {
    localStorage.removeItem('eleganza_carrito');
    carritoLocal = {
        items: [],
        subtotal: 0,
        envio: 15000,
        descuento: 0,
        total: 0
    };
}