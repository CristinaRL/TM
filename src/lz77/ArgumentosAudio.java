/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.Parameter;
import java.io.File;

/**
 *
 * @author levanna
 */
public class ArgumentosAudio {

    @Parameter(names = {"-me","--ment"}, description = "Tamaño ventana de entrada",required=true,validateValueWith = Power2Validator.class)
    private Integer ment;

    @Parameter(names = {"-md","--mdes"}, description = "Tamaño ventana deslizante" , required=true, validateValueWith = Power2Validator.class)
    private Integer mdes;

    @Parameter(names = {"-c","--comprimir"}, description = "Ruta del audio a comprimir", required=false, validateWith = FileValidator.class)
    private String rutaComprimir;
    
    @Parameter(names = {"-d","--descomprimir"}, description = "Ruta del audio a descomprimir", required=false)
    private String descomprimir = "12345";
    
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
    public String getRutaComprimir() {
        return rutaComprimir;
    }

    /**
     *
     * @param cadenaComprimir
     */
    public void setRutaComprimir(String cadenaComprimir) {
        this.rutaComprimir = cadenaComprimir;
    }
    
    /**
     *
     * @return
     */
    public String getDescomprimir() {
        return descomprimir;
    }

    /**
     *
     * @param descomprimir
     */
    public void setRutaDescomprimir(String descomprimir) {
        this.descomprimir = descomprimir;
    }
}
