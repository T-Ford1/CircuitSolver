/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package types;

import circuitsolver.Circuit;
import utils.CircuitParam;

/**
 *
 * @author Terrell
 */
public class Resistor extends Circuit {
    
    public Resistor(String n, CircuitParam... vals) {
        super(n, vals);
    }
    
    public Resistor(String n) {
        super(n);
    }

    public void tryCompute() {
        computeValues();
    }
}
