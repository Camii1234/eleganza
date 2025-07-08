package com.camila.eleganza.service;

import com.camila.eleganza.model.Factura;
import com.camila.eleganza.model.ItemCarrito;
import com.camila.eleganza.model.Usuario;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.constants.StandardFonts;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FacturaPDFService {
    
    // Paleta de colores rosa elegante
    private static final DeviceRgb ELEGANZA_ROSE = new DeviceRgb(199, 21, 133); // Rosa profundo elegante
    private static final DeviceRgb ELEGANZA_PINK = new DeviceRgb(236, 72, 153); // Rosa vibrante
    private static final DeviceRgb SOFT_ROSE = new DeviceRgb(251, 207, 232); // Rosa suave
    private static final DeviceRgb LIGHT_ROSE = new DeviceRgb(253, 242, 248); // Rosa muy claro
    private static final DeviceRgb DARK_ROSE = new DeviceRgb(131, 24, 67); // Rosa oscuro
    private static final DeviceRgb CHARCOAL = new DeviceRgb(55, 65, 81); // Gris carbón elegante
    
    private final NumberFormat formatter;
    private final SimpleDateFormat dateFormatter;
    
    public FacturaPDFService() {
        this.formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        this.dateFormatter = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "CO"));
    }
    
    public byte[] generarFacturaPDF(Factura factura) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDocument = new PdfDocument(writer);
             Document document = new Document(pdfDocument, PageSize.A4)) {
            
            // Configurar márgenes
            document.setMargins(40, 40, 40, 40);
            
            // Fuentes elegantes
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontItalic = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
            PdfFont fontTitle = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD); // Fuente serif para títulos
            
            // Encabezado de la empresa
            agregarEncabezadoEmpresa(document, fontTitle, fontBold, fontItalic);
            
            // Información de la factura
            agregarInformacionFactura(document, factura, fontRegular, fontBold);
            
            // Información del cliente
            agregarInformacionCliente(document, factura.getCliente(), fontRegular, fontBold);
            
            // Tabla de productos
            agregarTablaProductos(document, factura, fontRegular, fontBold);
            
            // Totales
            agregarTotales(document, factura, fontRegular, fontBold);
            
            // Pie de página
            agregarPiePagina(document, fontRegular);
            
        } catch (Exception e) {
            throw new IOException("Error al generar el PDF de la factura", e);
        }
        
        return outputStream.toByteArray();
    }
    
    private void agregarEncabezadoEmpresa(Document document, PdfFont fontBold) {
        // Logo y nombre de la empresa
        Paragraph empresaNombre = new Paragraph("BOUTIQUE ELEGANZA")
                .setFont(fontBold)
                .setFontSize(24)
                .setFontColor(ELEGANZA_BLUE)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(5);
        document.add(empresaNombre);
        
        Paragraph empresaSubtitulo = new Paragraph("Moda Femenina de Alta Calidad")
                .setFont(fontBold)
                .setFontSize(12)
                .setFontColor(ELEGANZA_GOLD)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(empresaSubtitulo);
        
        // Información de contacto
        Paragraph contacto = new Paragraph()
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        
        contacto.add("Medellín, Colombia • ")
                .add("Teléfono: +57 (4) 123-4567 • ")
                .add("Email: info@eleganzaboutique.com • ")
                .add("Web: www.eleganzaboutique.com");
        
        document.add(contacto);
        
        // Línea separadora
        document.add(new Paragraph("\n"));
    }
    
    private void agregarInformacionFactura(Document document, Factura factura, PdfFont fontRegular, PdfFont fontBold) {
        // Tabla para factura e información
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);
        
        // Columna izquierda - Factura
        Cell facturaCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.TOP);
        
        Paragraph facturaTitle = new Paragraph("FACTURA DE VENTA")
                .setFont(fontBold)
                .setFontSize(16)
                .setFontColor(ELEGANZA_BLUE)
                .setMarginBottom(10);
        facturaCell.add(facturaTitle);
        
        facturaCell.add(new Paragraph("Número: " + factura.getNumeroFactura())
                .setFont(fontBold)
                .setFontSize(12));
        
        facturaCell.add(new Paragraph("Fecha: " + dateFormatter.format(factura.getFechaFactura()))
                .setFont(fontRegular)
                .setFontSize(10));
        
        facturaCell.add(new Paragraph("Estado: " + factura.getEstado())
                .setFont(fontRegular)
                .setFontSize(10)
                .setFontColor(ColorConstants.GREEN));
        
        infoTable.addCell(facturaCell);
        
        // Columna derecha - Información adicional
        Cell infoCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setTextAlignment(TextAlignment.RIGHT);
        
        infoCell.add(new Paragraph("Método de Pago")
                .setFont(fontBold)
                .setFontSize(12)
                .setMarginBottom(5));
        
        infoCell.add(new Paragraph(factura.getMetodoPago())
                .setFont(fontRegular)
                .setFontSize(10)
                .setMarginBottom(10));
        
        infoCell.add(new Paragraph("Hora de Emisión")
                .setFont(fontBold)
                .setFontSize(10));
        
        infoCell.add(new Paragraph(new SimpleDateFormat("HH:mm:ss").format(new Date()))
                .setFont(fontRegular)
                .setFontSize(10));
        
        infoTable.addCell(infoCell);
        document.add(infoTable);
    }
    
    private void agregarInformacionCliente(Document document, Usuario cliente, PdfFont fontRegular, PdfFont fontBold) {
        // Título
        Paragraph clienteTitle = new Paragraph("INFORMACIÓN DEL CLIENTE")
                .setFont(fontBold)
                .setFontSize(14)
                .setFontColor(ELEGANZA_BLUE)
                .setMarginBottom(10);
        document.add(clienteTitle);
        
        // Tabla de información del cliente
        Table clienteTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);
        
        // Información básica
        Cell basicInfoCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(LIGHT_GRAY)
                .setPadding(10);
        
        basicInfoCell.add(new Paragraph("Nombre: " + cliente.getNombre())
                .setFont(fontBold)
                .setFontSize(11));
        
        basicInfoCell.add(new Paragraph("Email: " + cliente.getCorreo())
                .setFont(fontRegular)
                .setFontSize(10));
        
        basicInfoCell.add(new Paragraph("Teléfono: " + 
                (cliente.getTelefono() != null ? cliente.getTelefono() : "No especificado"))
                .setFont(fontRegular)
                .setFontSize(10));
        
        clienteTable.addCell(basicInfoCell);
        
        // Dirección
        Cell addressCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(LIGHT_GRAY)
                .setPadding(10);
        
        addressCell.add(new Paragraph("Dirección de Envío")
                .setFont(fontBold)
                .setFontSize(11)
                .setMarginBottom(5));
        
        StringBuilder direccion = new StringBuilder();
        if (cliente.getCalle() != null) direccion.append(cliente.getCalle()).append("\n");
        if (cliente.getCiudad() != null) direccion.append(cliente.getCiudad()).append(", ");
        if (cliente.getCodigoPostal() != null) direccion.append(cliente.getCodigoPostal()).append("\n");
        if (cliente.getPais() != null) direccion.append(cliente.getPais());
        
        addressCell.add(new Paragraph(direccion.toString())
                .setFont(fontRegular)
                .setFontSize(10));
        
        clienteTable.addCell(addressCell);
        document.add(clienteTable);
    }
    
    private void agregarTablaProductos(Document document, Factura factura, PdfFont fontRegular, PdfFont fontBold) {
        // Título
        Paragraph productosTitle = new Paragraph("DETALLE DE PRODUCTOS")
                .setFont(fontBold)
                .setFontSize(14)
                .setFontColor(ELEGANZA_BLUE)
                .setMarginBottom(10);
        document.add(productosTitle);
        
        // Tabla de productos
        Table productosTable = new Table(UnitValue.createPercentArray(new float[]{5, 25, 10, 10, 15, 15, 20}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);
        
        // Encabezados
        String[] headers = {"#", "Producto", "Talla", "Cantidad", "Precio Unit.", "Descuento", "Subtotal"};
        for (String header : headers) {
            Cell headerCell = new Cell()
                    .setBackgroundColor(ELEGANZA_BLUE)
                    .setFontColor(ColorConstants.WHITE)
                    .setFont(fontBold)
                    .setFontSize(10)
                    .setPadding(8)
                    .setTextAlignment(TextAlignment.CENTER);
            headerCell.add(new Paragraph(header));
            productosTable.addHeaderCell(headerCell);
        }
        
        // Productos
        int contador = 1;
        for (ItemCarrito item : factura.getProductos()) {
            // Número
            productosTable.addCell(new Cell()
                    .setFont(fontRegular)
                    .setFontSize(9)
                    .setPadding(6)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(String.valueOf(contador++))));
            
            // Producto
            productosTable.addCell(new Cell()
                    .setFont(fontRegular)
                    .setFontSize(9)
                    .setPadding(6)
                    .add(new Paragraph(item.getNombre()))
                    .add(new Paragraph("Categoría: " + item.getCategoria())
                            .setFontSize(8)
                            .setFontColor(DARK_GRAY)));
            
            // Talla
            productosTable.addCell(new Cell()
                    .setFont(fontRegular)
                    .setFontSize(9)
                    .setPadding(6)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(item.getTalla())));
            
            // Cantidad
            productosTable.addCell(new Cell()
                    .setFont(fontRegular)
                    .setFontSize(9)
                    .setPadding(6)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(String.valueOf(item.getCantidad()))));
            
            // Precio unitario
            productosTable.addCell(new Cell()
                    .setFont(fontRegular)
                    .setFontSize(9)
                    .setPadding(6)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .add(new Paragraph(String.format("$%,.0f", item.getPrecio()))));
            
            // Descuento
            productosTable.addCell(new Cell()
                    .setFont(fontRegular)
                    .setFontSize(9)
                    .setPadding(6)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .add(new Paragraph("$0")));
            
            // Subtotal
            double subtotalItem = item.getPrecio() * item.getCantidad();
            productosTable.addCell(new Cell()
                    .setFont(fontBold)
                    .setFontSize(9)
                    .setPadding(6)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .add(new Paragraph(String.format("$%,.0f", subtotalItem))));
        }
        
        document.add(productosTable);
    }
    
    private void agregarTotales(Document document, Factura factura, PdfFont fontRegular, PdfFont fontBold) {
        // Tabla de totales
        Table totalesTable = new Table(UnitValue.createPercentArray(new float[]{60, 40}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);
        
        // Celda vacía para alinear totales a la derecha
        totalesTable.addCell(new Cell().setBorder(Border.NO_BORDER));
        
        // Celda con totales
        Cell totalesCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(LIGHT_GRAY)
                .setPadding(15);
        
        totalesCell.add(new Paragraph("RESUMEN DE TOTALES")
                .setFont(fontBold)
                .setFontSize(12)
                .setFontColor(ELEGANZA_BLUE)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));
        
        // Subtotal
        Table resumenTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
                .setWidth(UnitValue.createPercentValue(100));
        
        resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph("Subtotal:").setFont(fontRegular).setFontSize(10)));
        resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph(String.format("$%,.0f", factura.getSubtotal()))
                        .setFont(fontRegular).setFontSize(10).setTextAlignment(TextAlignment.RIGHT)));
        
        // Envío
        resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph("Envío:").setFont(fontRegular).setFontSize(10)));
        resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph(String.format("$%,.0f", factura.getEnvio()))
                        .setFont(fontRegular).setFontSize(10).setTextAlignment(TextAlignment.RIGHT)));
        
        // Descuentos
        if (factura.getDescuento() > 0) {
            resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                    .add(new Paragraph("Descuento:").setFont(fontRegular).setFontSize(10)));
            resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                    .add(new Paragraph(String.format("-$%,.0f", factura.getDescuento()))
                            .setFont(fontRegular).setFontSize(10).setTextAlignment(TextAlignment.RIGHT)
                            .setFontColor(ColorConstants.RED)));
        }
        
        // Impuestos
        if (factura.getImpuestos() > 0) {
            resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                    .add(new Paragraph("IVA (19%):").setFont(fontRegular).setFontSize(10)));
            resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                    .add(new Paragraph(String.format("$%,.0f", factura.getImpuestos()))
                            .setFont(fontRegular).setFontSize(10).setTextAlignment(TextAlignment.RIGHT)));
        }
        
        // Total
        resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph("TOTAL:").setFont(fontBold).setFontSize(14)
                        .setFontColor(ELEGANZA_BLUE)));
        resumenTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph(String.format("$%,.0f", factura.getTotal()))
                        .setFont(fontBold).setFontSize(14).setTextAlignment(TextAlignment.RIGHT)
                        .setFontColor(ELEGANZA_BLUE)));
        
        totalesCell.add(resumenTable);
        totalesTable.addCell(totalesCell);
        document.add(totalesTable);
    }
    
    private void agregarPiePagina(Document document, PdfFont fontRegular) {
        // Línea separadora
        document.add(new Paragraph("\n"));
        
        // Mensaje de agradecimiento
        Paragraph agradecimiento = new Paragraph("¡Gracias por su compra!")
                .setFont(fontRegular)
                .setFontSize(12)
                .setFontColor(ELEGANZA_BLUE)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(agradecimiento);
        
        // Términos y condiciones
        Paragraph terminos = new Paragraph()
                .setFont(fontRegular)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(DARK_GRAY);
        
        terminos.add("• Política de devoluciones: 30 días • ")
                .add("Garantía de calidad en todos nuestros productos • ")
                .add("Seguimiento de envío disponible en nuestra web •\n")
                .add("Para consultas o reclamos: info@eleganzaboutique.com o +57 (4) 123-4567\n")
                .add("Esta factura fue generada electrónicamente el " + 
                       new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        
        document.add(terminos);
    }
}
