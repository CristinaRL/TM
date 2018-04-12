/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.math.BigDecimal;
import static lz77.Utils.factorCompresion;

/**
 *
 * @author alejandro
 */
public class LZ77 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Argumentos argumentos = new Argumentos();
        BigDecimal ratioCompresion;

        String[] argv = {"-ment", "4", "-mdes", "8","-c", "1111111111110111001010011110101000100", "-d", "1011010101010001110010100001110100100101001110"};
        JCommander cmd = new JCommander();

        try {
            cmd = new JCommander(argumentos, argv);
        } catch (ParameterException ex) {
            System.err.println(ex.getMessage());
            return;
        }

        if (argumentos.getMent() > argumentos.getMdes()) {
            System.err.println("Error: Ventana entrada > ventada deslizante");
            return;
        }

        // Validación: Mdes+Ment<= longitud datos a comprimir
        int sum = argumentos.getMent() + argumentos.getMdes();
        boolean res = sum <= argumentos.getCadenaComprimir().length();

        if (!res) {
            System.err.println("Error: Mdes+Ment>longitud datos a comprimir");
            return;
        }

        Compresor codec = new Compresor(argumentos.getMent(), argumentos.getMdes());
        String cadenaComprimida= codec.comprimirString(argumentos.getCadenaComprimir());
        
        System.out.println("Ment: " + String.valueOf(argumentos.getMent()));
        System.out.println("Mdes: " + String.valueOf(argumentos.getMdes()));
        System.out.println("Cadena a comprimir: " + argumentos.getCadenaComprimir());
        System.out.println("Cadena comprimida: " + cadenaComprimida);

    }

}
