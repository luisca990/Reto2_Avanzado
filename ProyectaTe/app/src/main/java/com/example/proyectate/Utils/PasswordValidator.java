package com.example.proyectate.Utils;

import java.util.regex.Pattern;

public class PasswordValidator {
    public static String validatePassword(String password) {
        if (password.length() < 6 || password.length() > 8) {
            return "La contraseña debe tener entre 6 y 8 caracteres";
        } else if (!containsUpperCase(password)) {
            return "La contraseña debe contener al menos una letra mayúscula";
        } else if (!containsLowerCase(password)) {
            return "La contraseña debe contener al menos una letra minúscula";
        } else if (!containsDigit(password)) {
            return "La contraseña debe contener al menos un número";
        } else if (!containsSpecialCharacter(password)) {
            return "La contraseña debe contener al menos un carácter especial (*/-+_)";
        } else {
            return null; // No hay error, la contraseña es válida
        }
    }

    private static boolean containsUpperCase(String input) {
        return Pattern.compile("[A-Z]").matcher(input).find();
    }

    private static boolean containsLowerCase(String input) {
        return Pattern.compile("[a-z]").matcher(input).find();
    }

    private static boolean containsDigit(String input) {
        return Pattern.compile("[0-9]").matcher(input).find();
    }

    private static boolean containsSpecialCharacter(String input) {
        return Pattern.compile("[*/\\-+_]").matcher(input).find();
    }
}
