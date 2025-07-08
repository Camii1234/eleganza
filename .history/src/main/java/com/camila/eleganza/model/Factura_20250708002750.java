package com.camila.eleganza.model;

import java.util.Date;
import java.util.List;

public class Factura {
    private String numeroFactura;
    private Date fechaFactura;
    private Usuario cliente;
    private List<ItemCarrito> productos;
    private double subtotal;
    private double impuestos;
    private double descuento;
    private double envio;
    private double total;
    private String metodoPago;
    private String estado;
    
    public Factura() {
        this.fechaFactura = new Date();
        this.numeroFactura = generarNumeroFactura();
        this.metodoPago = "Efectivo/Transferencia";
        this.estado = "Pendiente";
    }
    
    private String generarNumeroFactura() {
        // Generar número de factura único basado en timestamp
        return "ELG-" + System.currentTimeMillis();
    }
    
    // Getters y setters
    public String getNumeroFactura() {
        return numeroFactura;
    }
    
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    
    public Date getFechaFactura() {
        return fechaFactura;
    }
    
    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
    
    public Usuario getCliente() {
        return cliente;
    }
    
    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }
    
    public List<ItemCarrito> getProductos() {
        return productos;
    }
    
    public void setProductos(List<ItemCarrito> productos) {
        this.productos = productos;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public double getImpuestos() {
        return impuestos;
    }
    
    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }
    
    public double getDescuento() {
        return descuento;
    }
    
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
    public double getEnvio() {
        return envio;
    }
    
    public void setEnvio(double envio) {
        this.envio = envio;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
