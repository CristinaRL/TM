/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import static java.lang.Math.pow;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author levanna
 */
public class Descompresor {
    
    private final int ment;
    private final int mdes;
    private final int l;
    private final int d;
    private String stringDescomprimido;
    private String ceros;
    
    /**
     *
     * @param ment
     * @param mdes
     */
    public Descompresor(int ment, int mdes){
        this.ment = ment;
        this.mdes = mdes;
        this.l = this.bits(ment);
        this.d = this.bits(mdes);
        this.ceros = this.getCeros();
        this.stringDescomprimido = "";
    }
    
    /**
     * Comprime un string binario
     * @param descodificar string en binario a comprimir
     * @return string comprimido
     */
    public String descomprimirString(String descodificar){
        //String entrada = codificar.substring(0, ment);
        //codificar = codificar.substring(ment, ment+mdes);
        String deslizante;
        String tupla;
        int mida_tupla = l+d;
        String substring; 
        boolean acabar = false;
        int distancia;
        int longitud;
        int index;
        String patron;

        deslizante = descodificar.substring(0, mdes);
        tupla = descodificar.substring(mdes,mdes+mida_tupla);
        descodificar = descodificar.substring(mdes+mida_tupla);
        
        this.descomprime(deslizante);
            
        while(!acabar){         
            distancia = this.getDistancia(tupla);
            index = this.mdes-distancia;
            longitud = this.getLongitud(tupla);
            patron = deslizante.substring(index,index+longitud);
            
            //System.out.println("Deslizante = "+deslizante);
            //System.out.println("Tupla = "+tupla);
            //System.out.println("Resto = "+descodificar);  
            //System.out.println("("+longitud+","+distancia+")");  
            //System.out.println("Descomprimida = "+stringDescomprimido);
            
            this.descomprime(patron);
            
            if((tupla.length()+descodificar.length())>mida_tupla*2){
                deslizante = deslizante.substring(longitud)+patron;
                tupla = descodificar.substring(0,mida_tupla);
                descodificar = descodificar.substring(mida_tupla);      
            }else{
                acabar = true;  
            } 
        }
        this.descomprime(descodificar);        
        this.stringDescomprimido = this.quitarBits(stringDescomprimido); //Quitamos los bits añadidos al hacer la compresion
        return this.stringDescomprimido;                                 // que permiten hacer referencia a 0s o 1s en caso de que haya demasiados iguales seguidos
    }
    
    /**
     * Añade a la cadena descomprimida el string.
     * @param string
     */
    public void descomprime(String string){
        this.stringDescomprimido += string;
    }
    
    /**
     * Retorna el número de bits necesarios para representar un número decimal
     * @param entero número decimal
     * @return número de bits 
     */
    public int bits(int entero){
        int numBits = 0;
        boolean acabado = false;
        while (!acabado) {
            if (Math.pow(2, numBits) < entero) {
                numBits++;
            } else {
                acabado = true;
            }
        }
        return numBits;
    }
    
    /**
     * Crea un string de ceros util para la conversion de decimal a binario de longitud fija
     * @return un string de longitud d
     */
    public String getCeros(){
        String ceros = "";
        for(int i=0; i<d; i++){
            ceros += "0";
        }
        return ceros;
    }
    
    /**
     *
     * @param string
     * @return
     */
    public int getDistancia(String string){
        double dist = Integer.parseInt(string.substring(l), 2);
        if(dist==0){
            dist = pow(2,d);
        }
        return (int)dist;
    }
    
    /**
     *
     * @param string
     * @return
     */
    public int getLongitud(String string){
        double lon = Integer.parseInt(string.substring(0,l), 2);
        if(lon==0){
            lon = pow(2,l);
        }
        return (int)lon;
    }
    
    /**
     *
     * @param codificar
     * @return
     */
    public String quitarBits(String codificar){    
        int mida = mdes-1;
        String codificar_aux = "";
        String ventana;
        int i = 0;
        for(i=0; i<(codificar.length()-mida); i++){
            ventana = codificar.substring(i, mida+i);
            if(!ventana.contains("0") || !ventana.contains("1")){ //Si hay mdes-1 bits de 0's, añadimos un 0 después
                codificar_aux += ventana;
            }else{
                codificar_aux+= ventana.substring(0,1);//En caso contrario dejamos el string tal y como está
            }
        }

        //return codificar_aux+codificar.substring(i);
        return codificar_aux.substring(0,codificar_aux.length())+codificar.substring(i);
    }
}
