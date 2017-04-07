/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Terrell
 */
public class Current extends CircuitParam {
    
    private float current;

    public Current(float c) {
        current = c;
    }
    
    public Current() {
        current = -1;
    }
    
    public void setCurrent(float c) {
        current = c;
    }
    
    public float getCurrent() {
        return current;
    }
}
