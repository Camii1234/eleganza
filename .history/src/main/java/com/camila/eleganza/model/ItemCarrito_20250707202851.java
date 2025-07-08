package com.camila.eleganza.model;

import java.io.Serializable;

public class ItemCarrito implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int idProducto;
    private String nombre;
    private double precio;
    private String talla;
    private String categoria;
    private int cantidad;
    private byte[] imagen;
    private String descripcion;
    private int stock;
    
    // Constructor vacío
    public ItemCarrito() {}
    
    // Constructor con parámetros
    public ItemCarrito(Producto producto, String talla, int cantidad) {
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.talla = talla;
        this.categoria = producto.getCategoria();
        this.cantidad = cantidad;
        this.imagen = producto.getImagen();
        this.descripcion = producto.getDescripcion();
        this.stock = producto.getStock();
    }
    
    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public String getTalla() {
        return talla;
    }
    
    public void setTalla(String talla) {
        this.talla = talla;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public byte[] getImagen() {
        return imagen;
    }
    
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    // Método para obtener el subtotal del item
    public double getSubtotal() {
        return precio * cantidad;
    }
    
    // Método para verificar si es el mismo producto y talla
    public boolean esMismoProducto(int idProducto, String talla) {
        return this.idProducto == idProducto && this.talla.equals(talla);
    }
    
    @Override
    public String toString() {
        return "ItemCarrito{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", talla='" + talla + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
