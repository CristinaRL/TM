/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

/**
 * Permite comprobar si ment o mvent son potencias de dos.
 * @author levanna
 */
public class Power2Validator implements IValueValidator<Integer> {

    /**
     *
     * @param nombre
     * @param parametro
     * @throws ParameterException
     */
    @Override
    public void validate(String nombre, Integer parametro) throws ParameterException {

        boolean isPotencia = (parametro > 0) && ((parametro & (parametro - 1)) == 0);

        if (!isPotencia) {
            throw new ParameterException("El parámetro " + nombre + "no es una potencia de dos (" +parametro+")" );
        }
    }
}
