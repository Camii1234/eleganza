package com.camila.eleganza.servlet;

import com.camila.eleganza.model.Carrito;
import com.camila.eleganza.model.Factura;
import com.camila.eleganza.model.Usuario;
import com.camila.eleganza.service.FacturaPDFService;
import com.camila.eleganza.util.SessionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "GenerarFacturaPDFServlet", urlPatterns = {"/generar-factura-pdf"})
public class GenerarFacturaPDFServlet extends HttpServlet {
    
    private final FacturaPDFService facturaPDFService;
    
    public GenerarFacturaPDFServlet() {
        this.facturaPDFService = new FacturaPDFService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Verificar si el usuario está logueado
        if (!SessionManager.isUsuarioLogueado(session)) {
            response.sendRedirect("login.jsp?error=sesion&redirect=carrito.jsp");
            return;
        }
        
        // Obtener usuario y carrito de la sesión
        Usuario usuario = SessionManager.getUsuarioSesion(session);
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        
        if (carrito == null || carrito.estaVacio()) {
            response.sendRedirect("carrito.jsp?error=carrito-vacio");
            return;
        }
        
        try {
            // Crear factura
            Factura factura = new Factura();
            factura.setCliente(usuario);
            factura.setProductos(carrito.getItems());
            factura.setSubtotal(carrito.getSubtotal());
            factura.setEnvio(carrito.getEnvio());
            factura.setDescuento(carrito.getDescuento());
            factura.setImpuestos(carrito.getImpuestos());
            factura.setTotal(carrito.getTotal());
            
            // Generar PDF
            byte[] pdfBytes = facturaPDFService.generarFacturaPDF(factura);
            
            // Configurar response para descarga
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", 
                "attachment; filename=\"Factura_" + factura.getNumeroFactura() + ".pdf\"");
            response.setContentLength(pdfBytes.length);
            
            // Escribir PDF al response
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
            
            System.out.println("Factura PDF generada exitosamente: " + factura.getNumeroFactura());
            
        } catch (Exception e) {
            System.err.println("Error al generar factura PDF: " + e.getMessage());
            e.printStackTrace();
            
            // Redirigir con error
            response.sendRedirect("carrito.jsp?error=pdf-generation");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}
