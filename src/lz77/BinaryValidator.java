/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

/**
 *
 * @author levanna
 */
public class BinaryValidator implements IValueValidator<String> {

    /**
     *
     * @param nombre
     * @param parametro
     * @throws ParameterException
     */
    @Override
    public void validate(String nombre, String parametro) throws ParameterException {

        boolean isBinary = parametro.matches("[01]+");

        if (!isBinary) {
            throw new ParameterException("El parámetro " + nombre + " no es binario");
        }
    }
}

