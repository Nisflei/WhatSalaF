package com.aulas.whatsalaf.tool;

import java.util.Base64;

public class Base64Custom {
    public static String codificar(String texto){
        return Base64.getEncoder().encodeToString(texto.getBytes()).replaceAll("\\n|\\r","");
    }

    public  static String decodificar(String texto){
        return new String(Base64.getDecoder().decode(texto));
    }
}
