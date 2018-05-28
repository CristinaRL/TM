/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author levanna
 */
public class FileValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        
        // Comprobamos que el archivo exista
        if (!new File(value).exists()) {
            throw new ParameterException("El archivo " + value + " no existe.");
        }

        // Permite comprobar si el archivo tiene extensión ZIP
        if (!checkExtensionByString(value, "wav")) {
            throw new ParameterException("El archivo " + value + " no es ZIP.");
        }
    }

    /**
     * Método que permite comprobar si un archivo tiene una extensión dada.
     *
     * @param nombreArchivo Nombre del archivo
     * @param extensionComprobar Extensión a comprobar
     * @return
     */
    private boolean checkExtensionByString(String nombreArchivo, String extensionComprobar) {
        if (nombreArchivo.lastIndexOf(".") != -1 && nombreArchivo.lastIndexOf(".") != 0) {
            if (!nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).equals(extensionComprobar)) {
                throw new ParameterException("El archivo no tiene extensión una extensión válida");
            }
        } else {
            return false;
        }
        return true;
    }

}