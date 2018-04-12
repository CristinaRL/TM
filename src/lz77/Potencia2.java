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
 * @author alejandro
 */
public class Potencia2 implements IValueValidator<Integer> {

    @Override
    public void validate(String name, Integer value) throws ParameterException {

        boolean res = (value > 0) && ((value & (value - 1)) == 0);

        if (!res) {
            throw new ParameterException("Parametro " + name + " debe de ser potencias de dos");
        }
    }
}
