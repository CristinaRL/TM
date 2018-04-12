/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

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
    private String stringComprimido;
    private String ceros;
    
    /**
     *
     * @param ment
     * @param mdes
     */
    public Descompresor(int ment, int mdes){
        this.ment = 6;
        this.mdes = mdes;
        this.l = this.bits(ment);
        this.d = this.bits(mdes);
        this.ceros = this.getCeros();
        this.stringComprimido = "";
    }
    
    /**
     * Comprime un string binario
     * @param codificar string en binario a comprimir
     * @return string comprimido
     */
    public String comprimirString(String codificar){
        //String entrada = codificar.substring(0, ment);
        //codificar = codificar.substring(ment, ment+mdes);
        String deslizante;
        String entrada;
        Iterator iter;
        Boolean patron;
        int index_mdes;
        int index_ment;
        String substring;
        String deslizanteAux;  
        boolean acabar = false;
        System.out.println(codificar);
        substring = codificar.substring(0, mdes-1);   
        if (!substring.contains("1")){//Si no aparece ningun uno en los primeros mdes-1 bits del string, añadimos un 1
            deslizante = codificar.substring(0, mdes-1)+"1";
            entrada = codificar.substring(mdes-1,mdes+ment-1);
            codificar = codificar.substring(mdes+ment-1);
        }else if(!substring.contains("0")){//Si no aparece ningun cero en los primeros mdes-1 bits del string, añadimos un 0
            deslizante = codificar.substring(0, mdes-1)+"0";
            entrada = codificar.substring(mdes-1,mdes+ment-1);
            codificar = codificar.substring(mdes+ment-1);
            System.out.println("hola");
        }else{
           deslizante = codificar.substring(0, mdes);
           entrada = codificar.substring(mdes,mdes+ment);
           codificar = codificar.substring(mdes+ment);
        }
        
        this.comprime(deslizante);
            
        while(!acabar){
            
            System.out.println("V. deslizante = "+deslizante);
            System.out.println("V. entrada = "+entrada);    
            
            patron = false;
            index_mdes = -1;
            index_ment = -1;
            for(int i=ment; i>0 && patron==false; i--){
                if(deslizante.contains(entrada.substring(0, i))){
                    patron=true;
                    index_mdes = deslizante.lastIndexOf(entrada.substring(0, i));
                    index_ment = i;
                    System.out.println("Patron encontrado");
                }
            }
            
            this.comprime(index_mdes, index_ment);
            if((entrada.length()+codificar.length())>ment+index_ment){
                deslizante = deslizante.substring(index_ment)+entrada.substring(0,index_ment);
                substring = deslizante.substring(0,mdes-1);
                if (!substring.contains("1")){//Si no aparece ningun uno en los primeros mdes-1 bits de la ventana deslizante, añadimos un 1
                    deslizante = codificar.substring(1, mdes)+"1"; //Y deslizamos un bit de más
                }else if(!substring.contains("0")){//Si no aparece ningun cero en los primeros mdes-1 bits de la ventana deslizante, añadimos un 0
                    deslizante = codificar.substring(1, mdes)+"0"; //Y deslizamos un bit de más
                }
                entrada = entrada.substring(index_ment)+codificar.substring(0,index_ment);
                codificar = codificar.substring(index_ment);  
            }else{
                entrada = entrada.substring(index_ment); 
                acabar = true;
            }
        }
        this.comprime(entrada+codificar);
        return this.stringComprimido;
    }
    
    /**
     * Descomprime un string
     * @param string string comprimido
     * @return string descomprimido
     */
    public String descomprimirString(String string){
        
        return null;
    }
    
    /**
     * Añade a la cadena comprimida un string. Útil para el inicio y el final de la compresión.
     * @param inicial
     */
    public void comprime(String inicial){
        this.stringComprimido += inicial;
    }
    
    /**
     * Transforma la distancia y longitud del patrón encontrado a binario y lo añade a la cadena comprimida.
     * @param index_mdes distancia al patrón
     * @param index_ment longitud del patrón
     */
    public void comprime(int index_mdes, int index_ment){
        index_mdes = mdes-index_mdes;
        System.out.println("("+index_ment+","+index_mdes+")");
        String binary_index_mdes = this.ceros+Integer.toBinaryString(index_mdes);
        int index = binary_index_mdes.length()-d;
        binary_index_mdes = binary_index_mdes.substring(index);
        String binary_index_ment = this.ceros+Integer.toBinaryString(index_ment);
        index = binary_index_ment.length()-l;
        binary_index_ment = binary_index_ment.substring(index);
        System.out.println(binary_index_ment+" "+binary_index_mdes);
        this.stringComprimido += binary_index_ment + binary_index_mdes;
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
}
