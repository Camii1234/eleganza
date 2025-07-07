package com.camila.eleganza.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Clase de utilidades para validaciones y operaciones comunes
 */
public class ValidationUtils {
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    /**
     * Valida el formato de un correo electrónico
     * @param email El correo a validar
     * @return true si el formato es válido, false en caso contrario
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Valida que una cadena no sea nula o vacía
     * @param value La cadena a validar
     * @return true si la cadena es válida, false si es nula o vacía
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Valida que una contraseña cumpla con los requisitos mínimos
     * @param password La contraseña a validar
     * @return true si la contraseña es válida, false en caso contrario
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        // Al menos 6 caracteres
        return password.length() >= 6;
    }
    
    /**
     * Valida que un documento de identidad tenga un formato válido
     * @param documento El documento a validar
     * @return true si el documento es válido, false en caso contrario
     */
    public static boolean isValidDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }
        
        String docTrimmed = documento.trim();
        // Validar que contenga solo números o números con letras (para casos como pasaportes)
        // Mínimo 6 caracteres, máximo 15
        return docTrimmed.length() >= 6 && docTrimmed.length() <= 15 && 
               docTrimmed.matches("^[a-zA-Z0-9]+$");
    }
    
    /**
     * Valida que un teléfono tenga un formato básico válido
     * @param telefono El teléfono a validar
     * @return true si el teléfono es válido, false en caso contrario
     */
    public static boolean isValidTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        
        String telTrimmed = telefono.trim().replaceAll("[-\\s\\(\\)\\+]", "");
        // Validar que contenga solo números y tenga entre 7 y 15 dígitos
        return telTrimmed.matches("^[0-9]{7,15}$");
    }
    
    /**
     * Genera un hash SHA-256 de una contraseña
     * @param password La contraseña en texto plano
     * @return El hash de la contraseña
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes("UTF-8"));
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            System.err.println("Error al generar hash de contraseña: " + e.getMessage());
            // En caso de error, devolver la contraseña sin hash (no recomendado para producción)
            return password;
        }
    }
    
    /**
     * Limpia y normaliza una cadena de texto
     * @param value La cadena a limpiar
     * @return La cadena limpia o null si la entrada es null
     */
    public static String cleanString(String value) {
        if (value == null) {
            return null;
        }
        return value.trim();
    }
    
    /**
     * Normaliza un correo electrónico a minúsculas
     * @param email El correo a normalizar
     * @return El correo normalizado o null si la entrada es null
     */
    public static String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase();
    }
}
