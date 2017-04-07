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
public class Voltage extends CircuitParam {
    
    private float voltage;

    public Voltage(float v) {
        voltage = v;
    }
    
    public Voltage() {
        voltage = -1;
    }
    
    public void setVoltage(float v) {
        voltage = v;
    }
    
    public float getVoltage() {
        return voltage;
    }
}
