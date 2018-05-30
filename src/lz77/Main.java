/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import static java.lang.Math.pow;

/**
 *
 * @author levanna
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PruebasAudio pruebas = new PruebasAudio();
        System.out.println("Utilizando los programas anteriores comprimid en “LZ-77” i en “Rice+LZ-77” el archivo “data.wav” que se os proporciona.");

        for (int i=2; i<13;i++){
            for (int j=i; j<13; j++){
                String ment = String.valueOf((int)pow(2,i));
                String mdes = String.valueOf((int)pow(2,j));
                String[] argv = {"--ment", ment, "--mdes", mdes , "-c", "data.wav"};;
               pruebas.generarPruebas(argv);
            }
        }
    }
}
