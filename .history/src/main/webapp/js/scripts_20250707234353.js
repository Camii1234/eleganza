/*!
* Start Bootstrap - Modern Business v5.0.7 (https://startbootstrap.com/template-overviews/modern-business)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-modern-business/blob/master/LICENSE)
*/

// Carrito de compras - Funcionalidades principales
document.addEventListener('DOMContentLoaded', function() {
    // Inicializar funcionalidades del carrito
    initCarritoFunctionality();
    
    // Actualizar contador del carrito en la navbar
    updateCartCount();
});

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
                actualizarCantidad(productoId, talla, cantidadActual + 1);
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

// Función para mostrar/ocultar indicador de carga
function toggleLoading(show) {
    const buttons = document.querySelectorAll('.btn-increase, .btn-decrease, .btn-remove, #btn-vaciar-carrito');
    buttons.forEach(btn => {
        btn.disabled = show;
        if (show) {
            btn.classList.add('disabled');
            const originalText = btn.innerHTML;
            btn.setAttribute('data-original-text', originalText);
            btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Procesando...';
        } else {
            btn.classList.remove('disabled');
            const originalText = btn.getAttribute('data-original-text');
            if (originalText) {
                btn.innerHTML = originalText;
                btn.removeAttribute('data-original-text');
            }
        }
    });
}

// Función para actualizar cantidad
function actualizarCantidad(productoId, talla, nuevaCantidad) {
    if (nuevaCantidad < 0) return;
    
    console.log(`Actualizando cantidad: ProductoId=${productoId}, Talla=${talla}, Cantidad=${nuevaCantidad}`);
    
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
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Response data:', data);
        if (data.success) {
            mostrarMensaje(data.message || 'Cantidad actualizada exitosamente', 'success');
            // Recargar la página después de un breve delay
            setTimeout(() => {
                location.reload();
            }, 1000);
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
    
    console.log(`Eliminando producto: ProductoId=${productoId}, Talla=${talla}`);
    
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
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Response data:', data);
        if (data.success) {
            mostrarMensaje(data.message || 'Producto eliminado exitosamente', 'success');
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
    
    console.log('Vaciando carrito...');
    
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
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Response data:', data);
        if (data.success) {
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

// Función para actualizar el contador del carrito
function updateCartCount() {
    fetch('carrito', {
        method: 'POST',
        body: new FormData().append('action', 'count'),
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
    .then(response => response.json())
    .then(data => {
        const cartCountElements = document.querySelectorAll('.cart-count');
        cartCountElements.forEach(element => {
            element.textContent = data.count || 0;
        });
    })
    .catch(error => {
        console.error('Error al actualizar contador del carrito:', error);
    });
}