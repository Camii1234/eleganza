# Funcionalidad de Factura PDF - Boutique Eleganza

## Descripción
Se ha implementado una funcionalidad completa para generar facturas PDF elegantes cuando el usuario presiona el botón "Generar Factura PDF" en el carrito de compras.

## Archivos Creados/Modificados

### 1. Nuevos Archivos
- **`src/main/java/com/camila/eleganza/model/Factura.java`**: Modelo de datos para la factura
- **`src/main/java/com/camila/eleganza/service/FacturaPDFService.java`**: Servicio para generar PDF con iText
- **`src/main/java/com/camila/eleganza/servlet/GenerarFacturaPDFServlet.java`**: Servlet para manejar la generación de PDF

### 2. Archivos Modificados
- **`pom.xml`**: Agregadas dependencias de iText para generar PDF
- **`src/main/java/com/camila/eleganza/model/Carrito.java`**: Agregado campo y método para impuestos (IVA 19%)
- **`src/main/webapp/carrito.jsp`**: Integración con el servlet y mejoras en la UI

## Características del PDF

### Diseño Elegante
- **Colores corporativos**: Azul Eleganza (#0D6EFD) y Dorado (#FFC107)
- **Tipografía profesional**: Fuentes claras y legibles
- **Layout estructurado**: Secciones bien definidas y organizadas

### Contenido Completo
1. **Encabezado de la empresa**
   - Logo y nombre "BOUTIQUE ELEGANZA"
   - Subtítulo "Moda Femenina de Alta Calidad"
   - Información de contacto completa

2. **Información de la factura**
   - Número de factura único (formato: ELG-timestamp)
   - Fecha y hora de emisión
   - Estado de la factura
   - Método de pago

3. **Datos del cliente**
   - Nombre completo
   - Email y teléfono
   - Dirección de envío completa

4. **Detalle de productos**
   - Tabla con todos los productos del carrito
   - Información: nombre, talla, cantidad, precio unitario, subtotal
   - Categoría de cada producto

5. **Resumen financiero**
   - Subtotal
   - Envío
   - Descuentos (si aplican)
   - IVA (19%)
   - Total final

6. **Pie de página**
   - Mensaje de agradecimiento
   - Políticas de devolución
   - Información de contacto para soporte
   - Timestamp de generación

## Funcionalidades Implementadas

### Validaciones
- Verificación de usuario logueado
- Validación de carrito no vacío
- Manejo de errores con mensajes informativos

### Seguridad
- Autenticación requerida para generar factura
- Validación de sesión activa
- Protección contra acceso no autorizado

### Experiencia de Usuario
- Confirmación antes de generar PDF
- Mensajes de estado (éxito/error)
- Descarga automática del archivo PDF
- Alertas auto-cerradas después de 5 segundos

## Cómo Usar

### Para el Usuario
1. Agregar productos al carrito
2. Ir a la página del carrito
3. Verificar que esté logueado
4. Hacer clic en "Generar Factura PDF"
5. Confirmar en el diálogo que aparece
6. El PDF se descargará automáticamente

### Para el Desarrollador
1. Compilar el proyecto: `./mvnw clean compile`
2. Ejecutar el servidor
3. La funcionalidad está disponible en `/generar-factura-pdf`

## Estructura del PDF

```
BOUTIQUE ELEGANZA
Moda Femenina de Alta Calidad
Contacto: Medellín, Colombia • Tel: +57 (4) 123-4567

FACTURA DE VENTA                    MÉTODO DE PAGO
Número: ELG-1704672000000           Efectivo/Transferencia
Fecha: 8 de julio de 2025           Hora: 10:30:45
Estado: Pendiente

INFORMACIÓN DEL CLIENTE
Nombre: [Nombre del usuario]        Dirección de Envío:
Email: [Email del usuario]          [Dirección completa]
Teléfono: [Teléfono]               [Ciudad, Código Postal]

DETALLE DE PRODUCTOS
# | Producto | Talla | Cantidad | Precio Unit. | Descuento | Subtotal
--|----------|-------|----------|--------------|-----------|----------
1 | Vestido  | M     | 2        | $150,000     | $0        | $300,000

RESUMEN DE TOTALES
Subtotal:          $300,000
Envío:             $15,000
IVA (19%):         $57,000
TOTAL:             $372,000

¡Gracias por su compra!
Política de devoluciones: 30 días
```

## Configuración de Impuestos

- **IVA**: 19% (estándar Colombia)
- **Descuentos**: 5% automático para compras > $200,000
- **Envío**: $15,000 (fijo)

## Personalización

Para personalizar el PDF, modificar:
- `FacturaPDFService.java`: Colores, fuentes, layout
- `Factura.java`: Campos adicionales
- `GenerarFacturaPDFServlet.java`: Lógica de negocio

## Dependencias Agregadas

```xml
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext7-core</artifactId>
    <version>7.2.5</version>
    <type>pom</type>
</dependency>
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>kernel</artifactId>
    <version>7.2.5</version>
</dependency>
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>layout</artifactId>
    <version>7.2.5</version>
</dependency>
```

## Notas Importantes

1. **Requisitos**: El usuario debe estar logueado para generar la factura
2. **Formato**: PDF/A compatible para archivado
3. **Tamaño**: Formato A4 estándar
4. **Idioma**: Español (Colombia)
5. **Moneda**: Pesos colombianos (COP)

La funcionalidad está completamente implementada y lista para usar. El PDF generado es profesional y digno de una boutique de alta calidad.
