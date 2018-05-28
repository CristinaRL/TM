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

public class PruebasAudio{
        ArgumentosAudio argumentos = new ArgumentosAudio();
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
        
        String[] argv = {"--ment", "4", "--mdes", "8", "-c", "data.wav"};
        this.validarParametros(argv);
        
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
            
            if(argumentos.getRutaComprimir() != null){
                this.cargarAudio(argumentos.getRutaComprimir());
                
                boolean res = sum <= (this.txt).length();
                if (!res) {
                    System.err.println("Error: Mdes+Ment>longitud datos a comprimir");
                    return;
                }else{
                    this.comprimir();
                }
               
            }
            if(argumentos.getDescomprimir() != null){
                System.out.println("HOOOLAAAAA");
            }
    }
    
    /**
     *
     * @param argv
     */
    public void comprimir(){
        
        Compresor compresor = new Compresor(argumentos.getMent(), argumentos.getMdes());
        
        long t0 = System.nanoTime();
        System.out.println(txt);
        this.txt = compresor.añadirBits(this.txt);
        this.escribirFichero("cadena.txt", txt);
        String cadenaComprimida = compresor.comprimirString(txt);
        
        long t1 = System.nanoTime();
        double time = (double)(t1-t0)/1000000000.0;
        
        String rutaComprimir = argumentos.getRutaComprimir();
        int index = rutaComprimir.lastIndexOf('.');
        String rutaSinExtension = rutaComprimir.substring(0, index);
        
        this.escribirFichero(rutaSinExtension+"_comprimido.txt", cadenaComprimida);
        
        System.out.println("Factor de compresión: " + compresor.calcularFactorCompresion() + " ("+mdes+", "+ment+") "+time+"s");
    }
    
    /**
     *
     * @param ruta
     */
    public void cargarAudio(String ruta){
        int[] lista = WavReader.Wav2Array(ruta);
        this.txt = "";
        String string = "";
        for(int numero: lista){ //-1104
            string = String.format("%16s", Integer.toBinaryString(numero)).replace(' ', '0');
            string = string.substring(string.length()-16);
            //System.out.println(numero+" "+string);
            this.txt += string;
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

}


