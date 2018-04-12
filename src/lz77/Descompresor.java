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
        Iterator iter;
        int index_mdes;
        int index_ment;
        String substring;
        String deslizanteAux;  
        boolean acabar = false;
        int distancia;
        int longitud;
        int index;
        String patron;
        System.out.println(descodificar);
        
        substring = descodificar.substring(0, mdes-1);   
        if (!substring.contains("1")){//Si no aparece ningun uno en los primeros mdes-1 bits del string, añadimos un 1
            deslizante = descodificar.substring(0, mdes-1)+descodificar.substring(mdes, mdes+1);
            tupla = descodificar.substring(mdes+1,mdes+1+mida_tupla);
            descodificar = descodificar.substring(mdes+1+mida_tupla);
        }else if(!substring.contains("0")){//Si no aparece ningun cero en los primeros mdes-1 bits del string, añadimos un 0
            deslizante = descodificar.substring(0, mdes-1)+descodificar.substring(mdes, mdes+1);
            tupla = descodificar.substring(mdes+1,mdes+1+mida_tupla);
            descodificar = descodificar.substring(mdes+1+mida_tupla);
        }else{
           deslizante = descodificar.substring(0, mdes);
           tupla = descodificar.substring(mdes,mdes+mida_tupla);
           descodificar = descodificar.substring(mdes+mida_tupla);
        }
        
        this.descomprime(deslizante);
            
        while(!acabar){         
            distancia = this.getDistancia(tupla);
            index = this.mdes-distancia;
            longitud = this.getLongitud(tupla);
            
            System.out.println("Deslizante = "+deslizante);
            System.out.println("Resto = "+descodificar);  
            System.out.println("("+longitud+","+distancia+")");  
            patron = deslizante.substring(index,index+longitud);
            System.out.println("Patron = "+patron);
            
            this.descomprime(patron);
            System.out.println("Descomprimido = "+this.stringDescomprimido); 
            
            if((tupla.length()+descodificar.length())>mida_tupla*2){
                deslizante = deslizante.substring(longitud)+patron;
                tupla = descodificar.substring(0,mida_tupla);
                descodificar = descodificar.substring(mida_tupla);  
                
            }else{
                acabar = true;  
            } 
        }
        this.descomprime(descodificar);
        return this.stringDescomprimido;
    }
    
    /**
     * Añade a la cadena descomprimida el string.
     * @param inicial
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
    
    public void eliminaBitsSeguridad(){
        
    }
    
    public int getDistancia(String string){
        double dist = Integer.parseInt(string.substring(l), 2);
        if(dist==0){
            dist = pow(2,d);
        }
        return (int)dist;
    }
    
    public int getLongitud(String string){
        double lon = Integer.parseInt(string.substring(0,l), 2);
        if(lon==0){
            lon = pow(2,l);
        }
        return (int)lon;
    }
}
