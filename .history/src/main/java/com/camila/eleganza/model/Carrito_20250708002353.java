package com.camila.eleganza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<ItemCarrito> items;
    private double subtotal;
    private double envio;
    private double descuento;
    private double impuestos;
    private double total;
    
    // Constructor
    public Carrito() {
        this.items = new ArrayList<>();
        this.subtotal = 0.0;
        this.envio = 15000.0; // Costo fijo de envío
        this.descuento = 0.0;
        this.impuestos = 0.0;
        this.total = 0.0;
    }
    
    // Agregar item al carrito
    public void agregarItem(ItemCarrito item) {
        // Verificar si el producto ya existe en el carrito
        for (ItemCarrito itemExistente : items) {
            if (itemExistente.esMismoProducto(item.getIdProducto(), item.getTalla())) {
                // Si existe, actualizar la cantidad
                itemExistente.setCantidad(itemExistente.getCantidad() + item.getCantidad());
                calcularTotales();
                return;
            }
        }
        // Si no existe, agregar nuevo item
        items.add(item);
        calcularTotales();
    }
    
    // Eliminar item del carrito
    public void eliminarItem(int idProducto, String talla) {
        items.removeIf(item -> item.esMismoProducto(idProducto, talla));
        calcularTotales();
    }
    
    // Actualizar cantidad de un item
    public void actualizarCantidad(int idProducto, String talla, int nuevaCantidad) {
        for (ItemCarrito item : items) {
            if (item.esMismoProducto(idProducto, talla)) {
                if (nuevaCantidad <= 0) {
                    eliminarItem(idProducto, talla);
                } else {
                    item.setCantidad(nuevaCantidad);
                    calcularTotales();
                }
                return;
            }
        }
    }
    
    // Vaciar carrito
    public void vaciarCarrito() {
        items.clear();
        calcularTotales();
    }
    
    // Calcular totales
    private void calcularTotales() {
        subtotal = 0.0;
        for (ItemCarrito item : items) {
            subtotal += item.getSubtotal();
        }
        
        // Aplicar descuento si el subtotal es mayor a cierto monto
        if (subtotal > 200000) {
            descuento = subtotal * 0.05; // 5% de descuento
        } else {
            descuento = 0.0;
        }
        
        // Calcular impuestos (IVA 19% en Colombia)
        impuestos = (subtotal - descuento) * 0.19;
        
        total = subtotal + envio - descuento + impuestos;
    }
    
    // Obtener cantidad total de items
    public int getCantidadTotal() {
        return items.stream().mapToInt(ItemCarrito::getCantidad).sum();
    }
    
    // Verificar si el carrito está vacío
    public boolean estaVacio() {
        return items.isEmpty();
    }
    
    // Getters y Setters
    public List<ItemCarrito> getItems() {
        return items;
    }
    
    public void setItems(List<ItemCarrito> items) {
        this.items = items;
        calcularTotales();
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public double getEnvio() {
        return envio;
    }
    
    public void setEnvio(double envio) {
        this.envio = envio;
        calcularTotales();
    }
    
    public double getDescuento() {
        return descuento;
    }
    
    public void setDescuento(double descuento) {
        this.descuento = descuento;
        calcularTotales();
    }
    
    public double getTotal() {
        return total;
    }
    
    @Override
    public String toString() {
        return "Carrito{" +
                "items=" + items.size() +
                ", subtotal=" + subtotal +
                ", envio=" + envio +
                ", descuento=" + descuento +
                ", total=" + total +
                '}';
    }
}
