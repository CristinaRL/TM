/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.Parameter;

/**
 *
 * @author levanna
 */
public class Argumentos {

    @Parameter(names = {"-me","--ment"}, description = "Tamaño ventana de entrada",required=true,validateValueWith = Power2Validator.class)
    private Integer ment;

    @Parameter(names = {"-md","--mdes"}, description = "Tamaño ventana deslizante" , required=true, validateValueWith = Power2Validator.class)
    private Integer mdes;

    @Parameter(names = {"-c","--comprimir"}, description = "Cadena a comprimir", required=true, validateValueWith = BinaryValidator.class)
    private String cadenaComprimir;
    
    @Parameter(names = {"-d","--descomprimir"}, description = "Cadena a descomprimir", required=false)
    private boolean descomprimir = false;
    
    /**
     *
     * @return
     */
    public Integer getMent() {
        return ment;
    }

    /**
     *
     * @param ment
     */
    public void setMent(Integer ment) {
        this.ment = ment;
    }

    /**
     *
     * @return
     */
    public Integer getMdes() {
        return mdes;
    }

    /**
     *
     * @param mdes
     */
    public void setMdes(Integer mdes) {
        this.mdes = mdes;
    }

    /**
     *
     * @return
     */
    public String getCadenaSinComprimir() {
        return cadenaComprimir;
    }

    /**
     *
     * @param cadenaComprimir
     */
    public void setCadenaComprimir(String cadenaComprimir) {
        this.cadenaComprimir = cadenaComprimir;
    }
    
    /**
     *
     * @return
     */
    public boolean getDescomprimir() {
        return descomprimir;
    }

    /**
     *
     * @param descomprimir
     */
    public void setCadenaDescomprimir(boolean descomprimir) {
        this.descomprimir = descomprimir;
    }
}
