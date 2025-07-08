# Funcionalidades del Carrito - Boutique Eleganza

## Descripción

Este documento explica las funcionalidades implementadas para el manejo del carrito de compras en la aplicación Boutique Eleganza.

## Funcionalidades Implementadas

### 1. Eliminar Producto del Carrito

**Función:** `eliminarProducto(productoId, talla)`

**Descripción:** Permite eliminar un producto específico del carrito de compras.

**Parámetros:**
- `productoId`: ID del producto a eliminar
- `talla`: Talla del producto a eliminar

**Proceso:**
1. Muestra una confirmación al usuario
2. Envía una solicitud POST al servlet con action='remove'
3. El servlet llama al método `eliminarItem()` del carrito
4. Retorna una respuesta JSON con el resultado
5. Recarga la página si es exitoso

### 2. Vaciar Carrito

**Función:** `vaciarCarrito()`

**Descripción:** Elimina todos los productos del carrito de compras.

**Proceso:**
1. Muestra una confirmación al usuario
2. Envía una solicitud POST al servlet con action='clear'
3. El servlet llama al método `vaciarCarrito()` del carrito
4. Retorna una respuesta JSON con el resultado
5. Recarga la página si es exitoso

### 3. Actualizar Cantidad

**Función:** `actualizarCantidad(productoId, talla, nuevaCantidad)`

**Descripción:** Modifica la cantidad de un producto en el carrito.

**Parámetros:**
- `productoId`: ID del producto
- `talla`: Talla del producto
- `nuevaCantidad`: Nueva cantidad deseada

**Proceso:**
1. Envía una solicitud POST al servlet con action='update'
2. El servlet llama al método `actualizarCantidad()` del carrito
3. Si la cantidad es 0 o menor, elimina el producto
4. Retorna una respuesta JSON con el resultado
5. Recarga la página si es exitoso

## Arquitectura del Sistema

### Modelo de Datos

#### Clase `Carrito`
- **Atributos:**
  - `List<ItemCarrito> items`: Lista de productos en el carrito
  - `double subtotal`: Subtotal de la compra
  - `double envio`: Costo de envío
  - `double descuento`: Descuento aplicado
  - `double total`: Total de la compra

- **Métodos principales:**
  - `agregarItem(ItemCarrito item)`: Agrega un producto al carrito
  - `eliminarItem(int idProducto, String talla)`: Elimina un producto específico
  - `actualizarCantidad(int idProducto, String talla, int nuevaCantidad)`: Actualiza la cantidad
  - `vaciarCarrito()`: Elimina todos los productos
  - `calcularTotales()`: Recalcula subtotal, descuento y total

#### Clase `ItemCarrito`
- **Atributos:**
  - `int idProducto`: ID del producto
  - `String nombre`: Nombre del producto
  - `double precio`: Precio unitario
  - `String talla`: Talla seleccionada
  - `String categoria`: Categoría del producto
  - `int cantidad`: Cantidad seleccionada
  - `byte[] imagen`: Imagen del producto
  - `String descripcion`: Descripción del producto
  - `int stock`: Stock disponible

- **Métodos principales:**
  - `getSubtotal()`: Calcula precio × cantidad
  - `esMismoProducto(int idProducto, String talla)`: Verifica si es el mismo producto

### Controlador (Servlet)

#### Clase `CarritoServlet`
- **Mapeo:** `/carrito`
- **Métodos HTTP:** GET, POST

**Acciones soportadas:**
- `add`: Agregar producto al carrito
- `update`: Actualizar cantidad de un producto
- `remove`: Eliminar producto del carrito
- `clear`: Vaciar todo el carrito
- `count`: Obtener cantidad total de productos

**Respuestas JSON:**
```json
{
  "success": true|false,
  "total": number,
  "message": "mensaje opcional"
}
```

### Vista (JSP)

#### Archivo `carrito.jsp`
- **Muestra:** Lista de productos en el carrito
- **Funcionalidades JavaScript:**
  - Event listeners para botones
  - Llamadas AJAX al servlet
  - Mensajes de confirmación
  - Indicadores de carga
  - Mensajes de éxito/error

## Pruebas

### Archivo `test-carrito.jsp`
Página de pruebas que permite verificar las funcionalidades del carrito:
- Test de eliminación de productos
- Test de actualización de cantidades
- Test de vaciado del carrito

### Cómo usar las pruebas
1. Accede a `http://localhost:8080/eleganza/test-carrito.jsp`
2. Usa los botones de test para probar cada funcionalidad
3. Verifica que las respuestas sean correctas

## Instalación y Configuración

### Requisitos
- Java 11 o superior
- Maven 3.6 o superior
- Servidor de aplicaciones (Tomcat 10 o superior)

### Compilación
```bash
mvnw clean compile
```

### Deployment
```bash
mvnw clean package
```

## Seguridad

### Medidas implementadas
1. **Validación de parámetros**: Verificación de parámetros obligatorios
2. **Encoding UTF-8**: Manejo correcto de caracteres especiales
3. **Validación de sesión**: Verificación de carrito en sesión
4. **Respuestas estructuradas**: Uso de JSON para respuestas AJAX

### Consideraciones
- Los datos del carrito se almacenan en la sesión del usuario
- No se persisten los datos del carrito en base de datos
- Se requiere JavaScript habilitado para las funcionalidades AJAX

## Posibles Mejoras

1. **Persistencia**: Guardar carrito en base de datos
2. **Optimización**: Actualización parcial sin recargar página
3. **Validación**: Verificación de stock en tiempo real
4. **Autenticación**: Vincular carrito a usuario autenticado
5. **Animaciones**: Efectos visuales para mejor UX
