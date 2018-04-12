/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.Parameter;

/**
 *
 * @author alejandro
 */
public class Argumentos {

    @Parameter(names = "-ment", description = "Ventana de entrada",required=true,validateValueWith = Potencia2.class)
    private Integer ment;

    @Parameter(names = "-mdes", description = "Ventana deslizante" , required=true, validateValueWith = Potencia2.class)
    private Integer mdes;

    @Parameter(names = "-c", description = "Cadena a comprimir", required=true, validateValueWith = ValidarBinario.class)
    private String cadenaComprimir;
    
    @Parameter(names = "-d", description = "Cadena a comprimir", required=true, validateValueWith = ValidarBinario.class)
    private String cadenaDescomprimir;
    
    public Integer getMent() {
        return ment;
    }

    public void setMent(Integer ment) {
        this.ment = ment;
    }

    public Integer getMdes() {
        return mdes;
    }

    public void setMdes(Integer mdes) {
        this.mdes = mdes;
    }

    public String getCadenaComprimir() {
        return cadenaComprimir;
    }

    public void setCadenaComprimir(String cadenaComprimir) {
        this.cadenaComprimir = cadenaComprimir;
    }
    
     public String getCadenaDescomprimir() {
        return cadenaDescomprimir;
    }

    public void setCadenaDescomprimir(String cadenaComprimir) {
        this.cadenaDescomprimir = cadenaComprimir;
    }
}
