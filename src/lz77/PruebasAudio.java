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
        int ment;
        int mdes;
        String txtBinarioNatural = null;
        String txtCodigoRice = null;
        
    /**
     *
     */
    public void generarPruebas(String[] argv){
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
                if(this.txtBinarioNatural == null){
                    this.cargarAudioBinarioNatural(argumentos.getRutaComprimir());
                    this.cargarAudioCodigoRice(argumentos.getRutaComprimir(), 128);
                }
                
                boolean res = sum <= (this.txtBinarioNatural).length();
                if (!res) {
                    System.err.println("Error: Mdes+Ment>longitud datos a comprimir");
                    return;
                }else{
                    ment = argumentos.getMent();
                    mdes = argumentos.getMdes();   
                    this.comprimirDoble();
                }
               
            }
            if(argumentos.getDescomprimir() != null){
                //System.out.println("HOOOLAAAAA");
            }
    }
    
    /**
     *
     * @param argv
     */
    public void comprimirDoble(){
        
        Compresor compresor = new Compresor(argumentos.getMent(), argumentos.getMdes());

        long t0 = System.nanoTime();
        long longitudOriginalBin = this.txtBinarioNatural.length();
        long longitudOriginalCR = this.txtCodigoRice.length();
        
        this.txtBinarioNatural = compresor.añadirBits(this.txtBinarioNatural);
        
        String cadenaComprimidaBin= compresor.comprimirString(txtBinarioNatural);
        String cadenaComprimidaCR = compresor.comprimirString(txtCodigoRice);
        
        float longitudComprimidaBin = cadenaComprimidaBin.length();
        float longitudComprimidaCR = cadenaComprimidaCR.length();
        long t1 = System.nanoTime();
        double time = (double)(t1-t0)/1000000000.0;
        
        float factorCompresionLZ77 = longitudOriginalBin/longitudComprimidaBin;
        float factorCompresionLZ77CodigoRice = longitudOriginalBin/longitudComprimidaCR;
        
        //String rutaComprimir = argumentos.getRutaComprimir();
        //int index = rutaComprimir.lastIndexOf('.');
        //String rutaSinExtension = rutaComprimir.substring(0, index);
        //this.escribirFichero(rutaSinExtension+"_comprimido.txt", cadenaComprimida);
        
        System.out.println(mdes+" "+ment+" "+factorCompresionLZ77+" "+factorCompresionLZ77CodigoRice);
    }
    
    /**
     *
     * @param ruta
     */
    public void cargarAudioCA2(String ruta){ //Convertir los numeros a binario en CA2
        int[] lista = WavReader.Wav2Array(ruta);
        this.txtBinarioNatural = "";
        String string = "";
        for(int numero: lista){ //-1104
            string = String.format("%16s", Integer.toBinaryString(numero)).replace(' ', '0');
            string = string.substring(string.length()-16);
            //System.out.println(numero+" "+string);
            this.txtBinarioNatural += string;
        }
    }
    
    /**
     *
     * @param ruta
     */
    public void cargarAudioBinarioNatural(String ruta){ //Convertir los numeros a binario natural con signo
        int[] lista = WavReader.Wav2Array(ruta);
        this.txtBinarioNatural = "";
        String string = "";
        for(int numero: lista){ //-1104
            
            string = String.format("%15s", Integer.toBinaryString(Math.abs(numero))).replace(' ', '0');
            string = string.substring(string.length()-15);
            
            if(numero>=0){
                string = '0'+string;
            }else{
                string = '1'+string;
            }
            
            //System.out.println(numero+" "+string);
            this.txtBinarioNatural += string;
        }
    }
    
    
    /**
     *
     * @param ruta
     */
    public void cargarAudioCodigoRice(String ruta, int m){ //Convertir los numeros a binario natural con signo
        int[] lista = WavReader.Wav2Array(ruta);
        this.txtCodigoRice = "";
        String string = "";
        
        for(int numero: lista){ //-1104
            String numeroRice = this.codificarRice(numero, m);
            this.txtCodigoRice += numeroRice;
            //System.out.println(numero+" "+numeroRice);
        }
        
        
    }
    
//    def codificar(n, m):
//    
//    #SIGNO
//    codigo = "1"
//    if n < 0: #Si el número es negativo, substituimos el 1 por un 0
//        codigo = "0" 
//    
//    #COCIENTE
//    cociente = abs(n)//m #Calculamos el cociente
//    for i in range(cociente): #Añadimos tantos 1s como indique el cociente
//        codigo += "1"
//    codigo += "0"
//    
//    #RESIDUO
//    bits_r =  len(str(bin(m-1)[2:])) #Calculamos cuantos bits son necesarios para representar el residuo
//    residuo = bin(n%m)[2:] #Calculamos el residuo en binario
//    residuo = residuo.zfill(bits_r) #Rellenamos con los ceros a la izquierda que falten
//    codigo += residuo
    
    private String codificarRice(int n, int m){
        
        //SIGNO
        String string = "1"; 
        if(n<0){
            string = "0"; //Si el número es negativo, substituimos el 1 por un 0
        }
        
        //COCIENTE
        int cociente = (int)(Math.abs(n)/m);
        for(int i=0; i<cociente; i++){
            string += '1';
        }
        string += '0';
        
        //RESIDUO
        int bits_r = Integer.toBinaryString(Math.abs(m-1)).length();
        int r = n%m;
        String residuo = String.format("%"+bits_r+"s", Integer.toBinaryString(Math.abs(r))).replace(' ', '0');
        string += residuo.substring(residuo.length()-bits_r);
        return string;
        
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


