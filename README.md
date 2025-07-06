# Boutique Eleganza - Configuración de Base de Datos

## Requisitos
- MySQL Server instalado y ejecutándose
- Java 23
- Maven (incluido mvnw)

## Configuración de Base de Datos

### 1. Crear la base de datos
```sql
CREATE DATABASE bdboutique;
```

### 2. Ejecutar el script de inicialización
Ejecuta el archivo `database/init.sql` en tu cliente MySQL:
```bash
mysql -u root -p < database/init.sql
```

### 3. Configurar credenciales
Las credenciales de la base de datos están en:
`src/main/java/com/camila/eleganza/dao/ConexionBD.java`

Valores actuales:
- URL: `jdbc:mysql://localhost:3306/bdboutique`
- Usuario: `root`
- Contraseña: `macacapi123.`

**Actualiza estas credenciales según tu configuración de MySQL.**

## Estructura de la tabla producto

| Campo | Tipo | Descripción |
|-------|------|-------------|
| idProducto | INT AUTO_INCREMENT | ID único del producto |
| nombre | VARCHAR(255) | Nombre del producto |
| precio | DECIMAL(10,2) | Precio del producto |
| talla | VARCHAR(50) | Talla del producto |
| categoria | VARCHAR(100) | Categoría (vestidos, blusas, pantalones, faldas, conjuntos) |
| stock | INT | Cantidad en inventario |
| estado | VARCHAR(50) | Estado del producto (activo/inactivo) |
| imagen | LONGBLOB | Imagen del producto (opcional) |
| descripcion | TEXT | Descripción del producto |

## Compilar y ejecutar

### 1. Compilar el proyecto
```bash
./mvnw clean compile
```

### 2. Generar WAR
```bash
./mvnw package
```

### 3. Probar conexión (opcional)
```bash
./mvnw exec:java -Dexec.mainClass="com.camila.eleganza.test.TestConexion"
```

### 4. Probar productos (opcional)
```bash
./mvnw exec:java -Dexec.mainClass="com.camila.eleganza.test.TestProductos"
```

## Endpoints

- `GET /productos` - Obtener todos los productos
- `GET /productos?categoria=vestidos` - Obtener productos por categoría

## Páginas

- `/index.jsp` - Página principal
- `/pages/productos.jsp` - Página de productos (usa datos de la BD)

## Características implementadas

✅ Conexión a base de datos MySQL
✅ DAO para productos con métodos CRUD básicos
✅ Servlet para API REST de productos
✅ Conversión de imágenes BLOB a Base64
✅ Filtrado por categoría
✅ Página de productos dinámica
✅ Manejo de errores y estados de carga
