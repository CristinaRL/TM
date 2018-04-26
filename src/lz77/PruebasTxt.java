/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.FileWriter;
import java.io.PrintWriter;
import static java.lang.Math.pow;

/**
 *
 * @author levanna
 */
// TODO code application logic here

public class PruebasTxt{
        ArgumentosTxt argumentos = new ArgumentosTxt();
        txtReader lector = new txtReader();
        String ment;
        String mdes;
        String txt;
    /**
     *
     */
    public void generarPruebas(){

//Apartado 1: Comprobad el correcto funcionamiento del programa; es decir, que es capaz de comprimir texto en LZ-77 y de recuperarlo tras la descompresión.
        System.out.println("Apartado 1: Comprobad el correcto funcionamiento del programa; es decir, que es capaz de comprimir texto en LZ-77 y de recuperarlo tras la descompresión.");
        
        String[] argv = {"--ment", "4", "--mdes", "8","-c", "quijote_short.txt", "-d"};
        this.comprimirDescomprimirExtenso(argv);
        String[] argv2 = {"--ment", "4", "--mdes", "8","-c", "hamlet_short.txt", "-d"};
        this.comprimirDescomprimirExtenso(argv2);
     
//Apartado 2: Comprimid el archivo “hamlet_short.txt” con distintos valores de Mdes y Ment entre 4 y 4096 y analizad el factor de compresión y el tiempo invertido para conseguirlo.
        System.out.println("\nApartado 2: Comprimid el archivo “hamlet_short.txt” con distintos valores de Mdes y Ment entre 4 y 4096 y analizad el factor de compresión y el tiempo invertido para conseguirlo.");
        
        int j = 3;
        for (int i=2; i<13;i++){
            if(i==2){j=3;}else{j=i;}
            for (j=j; j<13; j++){
                
                ment = String.valueOf((int)pow(2,i));
                mdes = String.valueOf((int)pow(2,j));
                String[] argv3 = {"--ment", ment, "--mdes",mdes,"-c", "hamlet_short.txt"};
                this.comprimirDescomprimir(argv3);
            }
        }
        
//Apartado 3:Comprimid ahora el archivo “quijote_short.txt” y analizad para qué combinación de Mdes y Ment se obtiene el mejor factor de compresión. 
        System.out.println("\nApartado 3: Comprimid ahora el archivo “quijote_short.txt” y analizad para qué combinación de Mdes y Ment se obtiene el mejor factor de compresión. ");
        
        j = 3;
        for (int i=2; i<13;i++){
            if(i==2){j=3;}else{j=i;}
            for (j=j; j<13; j++){
                
                ment = String.valueOf((int)pow(2,i));
                mdes = String.valueOf((int)pow(2,j));
                String[] argv3 = {"--ment", ment, "--mdes",mdes,"-c", "quijote_short.txt"};
                this.comprimirDescomprimir(argv3);
            }
        }
        
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
            this.cargarTxt(argumentos.getRutaComprimir());
            boolean res = sum <= (this.txt).length();
            if (!res) {
                System.err.println("Error: Mdes+Ment>longitud datos a comprimir");
                return;
            }
    }
    
    private void escribirFichero(String ruta, String string){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(ruta);
            pw = new PrintWriter(fichero);
            pw.println(string);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    /**
     *
     * @param argv
     */
    public void comprimirDescomprimirExtenso(String[] argv){
        
        String rutaComprimir;
        String cadenaComprimida;
        String cadenaDescomprimida = "";
        this.validarParametros(argv);
        CompresorTxt compresor = new CompresorTxt(argumentos.getMent(), argumentos.getMdes());
        
        rutaComprimir = argumentos.getRutaComprimir();
        int index = rutaComprimir.lastIndexOf('.');
        String rutaSinExtension = rutaComprimir.substring(0, index);
        long t0 = System.nanoTime();
        cadenaComprimida = compresor.comprimirString(txt);
        this.escribirFichero(rutaSinExtension+"_comprimido.txt", cadenaComprimida);
        DescompresorTxt descompresor = new DescompresorTxt(argumentos.getMent(), argumentos.getMdes());
        cadenaDescomprimida = descompresor.descomprimirString(cadenaComprimida);
        this.escribirFichero(rutaSinExtension+"_descomprimido.txt", cadenaDescomprimida);
        long t1 = System.nanoTime();

        System.out.println("Ment: " + String.valueOf(argumentos.getMent()));
        System.out.println("Mdes: " + String.valueOf(argumentos.getMdes()));
        System.out.println(compresor.getCadenaSinComprimir().substring(0,100)+" (Cadena a comprimir (parcial))");
        System.out.println(cadenaComprimida.substring(0, 100)+" (Cadena comprimida (parcial))");
        System.out.println(cadenaComprimida.substring(0,100)+" (Cadena a descomprimir (parcial))");
        System.out.println(cadenaDescomprimida.substring(0,100)+" (Cadena descomprimida (parcial))");
        System.out.println(descompresor.getStringDescomprimido().substring(0,100)+" (Cadena descomprimida convertida (parcial))");
        if(compresor.getCadenaSinComprimir().equals(cadenaDescomprimida)){
            System.out.println("La cadena sin comprimir y la cadena descomprimida son iguales.");
        }
        System.out.println("Factor de compresión: " + compresor.calcularFactorCompresion()+"");
        long time = t1-t0;
        System.out.println("Tiempo empleado en la compresión y descompresión: "+time / 1000000000.0+" segundos\n");
    }
    
    /**
     *
     * @param argv
     */
    public void comprimirDescomprimir(String[] argv){
        
        this.validarParametros(argv);
        CompresorTxt compresor = new CompresorTxt(argumentos.getMent(), argumentos.getMdes());
       
        long t0 = System.nanoTime();
        String cadenaComprimida = compresor.comprimirString(txt);
        long t1 = System.nanoTime();
        double time = (double)(t1-t0)/1000000000.0;
        System.out.println("Factor de compresión: " + compresor.calcularFactorCompresion() + " ("+mdes+", "+ment+") "+time+"s");
    }
    
    /**
     *
     * @param ruta
     */
    public void cargarTxt(String ruta){
        this.txt = (txtReader.cargarTxt(ruta)).toString();
    }

}


