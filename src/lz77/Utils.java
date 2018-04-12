/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import java.math.BigDecimal;
import java.util.Random;

/**
 *
 * @author alejandro
 */
public class Utils {

    /* 
    Metodo estatico que genera una cadena de número aleatorios
     */
    public static String generarNumAleatorios(int i) {
        String output = "";
        Random random = new Random();

        int nextInt = random.nextInt();
        for (int p = 0; p < i; p++) {
            nextInt = random.nextInt();
            if (nextInt % 2 == 0) {
                output = output + "0";
            } else {
                output = output + "1";
            }
        }
        return output;
    }
 public static BigDecimal factorCompresion(float numero, int posiciones) {
        BigDecimal bd = new BigDecimal(Float.toString(numero));
        bd = bd.setScale(posiciones, BigDecimal.ROUND_HALF_UP);       
        return bd;
}
}
