/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;


/**
 *Permite comprimir una o varias cadenas binarias
 * @author levanna
 */
public class CompresorTxt {
    
    private final int ment;
    private final int mdes;
    private final int l;
    private final int d;
    private String asciiComprimido;
    private String asciiSinComprimir;
    private String ceros;
    
    /**
     * Permite comprimir uno o varios strings binarios
     * @param ment
     * @param mdes
     */
    public CompresorTxt(int ment, int mdes){
        this.ment = ment;
        this.mdes = mdes;
        this.l = this.bits(ment);
        this.d = this.bits(mdes);
        this.ceros = this.getCeros();
        this.asciiSinComprimir = "";
        this.asciiComprimido = "";
    }
    
    /**
     * Comprime un string binario
     * @param codificar string en binario a comprimir
     * @return string comprimido
     */
    public String comprimirString(String codificar){
        //String entrada = codificar.substring(0, ment);
        //codificar = codificar.substring(ment, ment+mdes);
        this.asciiSinComprimir = codificar;
        String deslizante;
        String entrada;
        Boolean patron;
        int index_mdes;
        int index_ment;
        boolean acabar = false;
        String substring = codificar.substring(0, mdes-1);   

        codificar = this.añadirBits(codificar);//Añadimos 0s y 1s en el string en caso de ser necesario
        deslizante = codificar.substring(0, mdes);
        entrada = codificar.substring(mdes,mdes+ment);
        codificar = codificar.substring(mdes+ment);
        this.comprime(deslizante);
            
        while(!acabar){                  
            patron = false;
            index_mdes = -1;
            index_ment = -1;
            for(int i=ment; i>0 && patron==false; i--){
                if(deslizante.contains(entrada.substring(0, i))){
                    patron=true;
                    index_mdes = deslizante.lastIndexOf(entrada.substring(0, i));
                    index_ment = i;                    
                }
            }
            
            this.comprime(index_mdes, index_ment);
            if((entrada.length()+codificar.length())>=ment+index_ment){
                deslizante = deslizante.substring(index_ment)+entrada.substring(0,index_ment);
                substring = deslizante.substring(0,mdes-1);
                entrada = entrada.substring(index_ment)+codificar.substring(0,index_ment);
                codificar = codificar.substring(index_ment);  
            }else{
                entrada = entrada.substring(index_ment); 
                acabar = true;
            }
        }
        this.comprime(entrada+codificar);
        return this.asciiComprimido;
    }
    
    /**
     * Añade a la cadena comprimida un string. Útil para el inicio y el final de la compresión.
     * @param inicial
     */
    public void comprime(String inicial){
        this.asciiComprimido += inicial;
    }
    
    /**
     * Transforma la distancia y longitud del patrón encontrado a binario y lo añade a la cadena comprimida.
     * @param index_mdes distancia al patrón
     * @param index_ment longitud del patrón
     */
    public void comprime(int index_mdes, int index_ment){
        index_mdes = mdes-index_mdes;
        //System.out.println("("+index_ment+","+index_mdes+")");
        String binary_index_mdes = this.ceros+Integer.toBinaryString(index_mdes);
        int index = binary_index_mdes.length()-d;
        binary_index_mdes = binary_index_mdes.substring(index);
        String binary_index_ment = this.ceros+Integer.toBinaryString(index_ment);
        index = binary_index_ment.length()-l;
        binary_index_ment = binary_index_ment.substring(index);
        //System.out.println(binary_index_ment+" "+binary_index_mdes);
        this.asciiComprimido += binary_index_ment + binary_index_mdes;
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
     * Añade bits del tipo contrario en caso de que vengan mdes-1 bits iguales
     * @param codificar string que queremos codificar
     * @return string con bits de seguridad añadidos
     */
    public String añadirBits(String codificar){
        int mida = mdes-1;
        String codificar_aux = "";
        String ventana;
        int i = 0;
        for(i=0; i<(codificar.length()-mida); i++){
            ventana = codificar.substring(i, mida+i);
            if(!ventana.contains("0")){ //Si hay mdes-1 bits de 0's, añadimos un 0 después
                codificar_aux += ventana+"0";
                i=i+mida-1;
            }else if(!ventana.contains("1")){//Si hay mdes-1 bits de 1's, añadimos un 1 después
                codificar_aux += ventana+"1";
                i=i+mida-1;
            }else{
                codificar_aux+=codificar.substring(i, i+1);//En caso contrario dejamos el string tal y como está
            }
        }

        return codificar_aux+codificar.substring(i);
    }
    
    /**
     * Calcula el factor de compresión
     * @return factor de compresión
     */
    public float calcularFactorCompresion(){
        float factorCompresion = (float)this.asciiSinComprimir.length()/(float)this.asciiComprimido.length();
        return factorCompresion;
    }

    public String getCadenaSinComprimir(){
        return this.asciiSinComprimir;
    }
}
