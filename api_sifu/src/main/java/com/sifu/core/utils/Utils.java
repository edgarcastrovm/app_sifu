package com.sifu.core.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> obtenerProvincias() {
        return Arrays.asList("PICHINCHA", "GUAYAS", "AZUAY", "COTOPAXI", "CHIMBORAZO", "ESMERALDAS"," IMBABURA", "PASTAZA", "TUNGURAHUA");
    }

    public static Map<String, List<String>> cantonPorProvincia() {
        Map<String, List<String>> mapa = new HashMap<>();
        mapa.put("PICHINCHA", Arrays.asList("Quito", "Mejía", "Rumiñahui","Cayambe"));
        mapa.put("GUAYAS", Arrays.asList("Guayaquil", "Samborondón", "Durán", "Daule","Colimes", "Playas", "Salitre"));
        mapa.put("AZUAY", Arrays.asList("Cuenca", "Gualaceo", "Paute", "Sigsi"));
        mapa.put("COTOPAXI", Arrays.asList("Latacunga", "Salcedo", "Saquisili", "Pujili", "Sigchos"));
        mapa.put("CHIMBORAZO", Arrays.asList("Riobamba", "Alausi", "Colta", "Guano"));
        mapa.put("ESMERALDAS", Arrays.asList("Atacames", "Quininde", "San Lorenzo"));
        mapa.put("IMBABURA", Arrays.asList("Ibarra", "Otavalo", "Cotacachi", "Pimampiro"));
        mapa.put("PASTAZA", Arrays.asList("Pastaza", "Mera"));
        mapa.put("TUNGURAHUA", Arrays.asList("Ambato", "Patate", "Quero", "Baños de Agua Santa"));
        
        return mapa;
    }
}
