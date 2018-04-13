/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import static java.lang.Math.pow;

/**
 *
 * @author levanna
 */
// TODO code application logic here

public class Pruebas{
        Argumentos argumentos = new Argumentos();
        String ment;
        String mdes;
        String cadena;
        
    /**
     *
     */
    public void generarPruebas(){
        
        
        //Apartado 1: generar una cadena de 25 bits y comprobar que se comprime y descomprime correctamente

        cadena = this.generarStringBinario(25);
        String[] argv = {"--ment", "4", "--mdes", "8","-c", cadena, "-d"};

        this.validarParametros(argv);
        Compresor compresor = new Compresor(argumentos.getMent(), argumentos.getMdes());
        String cadenaSinComprimir;
        String cadenaComprimida;
        String cadenaDescomprimida;
        Descompresor descompresor;
        
        cadenaSinComprimir = argumentos.getCadenaSinComprimir();
        cadenaComprimida = compresor.comprimirString(cadenaSinComprimir);
        descompresor = new Descompresor(argumentos.getMent(), argumentos.getMdes());
        cadenaDescomprimida = descompresor.descomprimirString(cadenaComprimida);
        
        System.out.println("Apartado 1: Comprobad que el programa comprime y descomprime correctamente una cadena de 25 bits aleatorios con Mdes = 8 y Ment = 4.");
        System.out.println("Ment: " + String.valueOf(argumentos.getMent()));
        System.out.println("Mdes: " + String.valueOf(argumentos.getMdes()));
        System.out.println(cadenaSinComprimir+" (Cadena a comprimir)");
        System.out.println(cadenaComprimida+" (Cadena comprimida)");
        System.out.println(cadenaComprimida+" (Cadena a descomprimir)");
        System.out.println(cadenaDescomprimida+" (Cadena descomprimida)");
                System.out.println("Factor de compresión: " + compresor.calcularFactorCompresion()+"\n");
        if(cadenaSinComprimir.equals(cadenaDescomprimida)){
            System.out.println("La cadena sin comprimir y la cadena descomprimida son iguales\n");
        }
        

         
        //Apartado 2
        System.out.println("Apartado 2: Leer pruebas hechas en el README.txt");
        for (int i=2; i<12;i++){
            for (int j=i+1; j<12; j++){
                ment = String.valueOf((int)pow(2,i));
                mdes = String.valueOf((int)pow(2,j));
                //System.out.println(j+","+i);
                cadena = this.generarStringBinario(7000);
                String[] argv2 = {"--ment", ment, "--mdes",mdes,"-c", cadena};
                this.validarParametros(argv2);
                compresor = new Compresor(argumentos.getMent(), argumentos.getMdes());
                cadenaSinComprimir = argumentos.getCadenaSinComprimir();
                cadenaComprimida = compresor.comprimirString(cadenaSinComprimir);
                System.out.println("Factor de compresión: " + compresor.calcularFactorCompresion() + " ("+mdes+", "+ment+")");
            }
        }
        
        
    }
    
    /**
     *
     * @param mida
     * @return
     */
    private String generarStringBinario(int mida){
        String string = "";
        for(int i=0; i<mida; i++){
            string += Math.round(Math.random());
        }
        return string;
    }
    
    private void validarParametros(String[] argv){
        JCommander cmd = new JCommander();
            
            //Error: parámetro incorrecto
            try {
                cmd = new JCommander(argumentos, argv);
            } catch (ParameterException ex) {
                System.err.println(ex.getMessage());
                return;
            }
            //Validación: Ventana entrada > ventana deslizante
            if (argumentos.getMent() > argumentos.getMdes()) {
                System.err.println("Error: Ventana entrada > ventada deslizante");
                return;
            }
            // Validación: Mdes+Ment<= longitud datos a comprimir
            int sum = argumentos.getMent() + argumentos.getMdes();
            boolean res = sum <= argumentos.getCadenaSinComprimir().length();
            if (!res) {
                System.err.println("Error: Mdes+Ment>longitud datos a comprimir");
                return;
            }
    } 
    
    

}


