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
public class Resist extends CircuitParam {
    
    private float resist;

    public Resist(float r) {
        resist = r;
    }
    
    public Resist() {
        resist = -1;
    }
    
    public void setResist(float r) {
        resist = r;
    }
    
    public float getResist() {
        return resist;
    }
}
