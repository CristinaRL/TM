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
 * @author alejandro
 */
public class ValidarBinario implements IValueValidator<String> {

    @Override
    public void validate(String name, String value) throws ParameterException {

        boolean res = value.matches("[01]+");

        if (!res) {
            throw new ParameterException("Parametro " + name + " no es binario");
        }
    }
}

