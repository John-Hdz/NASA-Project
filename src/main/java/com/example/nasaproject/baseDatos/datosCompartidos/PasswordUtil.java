package com.example.nasaproject.baseDatos.datosCompartidos;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil
{
    // Método para hashear una contraseña
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Método para verificar una contraseña hasheada
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
