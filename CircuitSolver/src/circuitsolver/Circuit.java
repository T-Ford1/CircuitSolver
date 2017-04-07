/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circuitsolver;

import java.util.ArrayList;
import utils.CircuitParam;
import utils.Current;
import utils.Voltage;
import utils.Resist;

/**
 *
 * @author Terrell
 */
public abstract class Circuit {
    
    public final ArrayList<Circuit> subCircuits;
    private final String name;
    
    private float v;
    private float c;
    private float r;
    
    public Circuit(String n, Circuit... c) {
        this(n);
        for (int i = 0; i < c.length; i++) {
            subCircuits.add(c[i]);
        }
    }
    
    public Circuit(String n, CircuitParam... vals) {
        v = -1;
        c = -1;
        r = -1;
        for (int i = 0; i < vals.length; i++) {
            if(vals[i] instanceof Voltage) {
                v = ((Voltage) vals[i]).getVoltage();
            } else if(vals[i] instanceof Current) {
                c = ((Current) vals[i]).getCurrent();
            } else if(vals[i] instanceof Resist) {
                r = ((Resist) vals[i]).getResist();
            }
        }
        subCircuits = new ArrayList();
        name = n;
    }
    
    public Circuit(String n) {
        v = -1;
        c = -1;
        r = -1;
        subCircuits = new ArrayList();
        name = n;
    }
    
    public void setVoltage(float f) {
        v = f;
    }
    
    public void setCurrent(float f) {
        c = f;
    }
    
    public void setResist(float f) {
        r = f;
    }
    
    public float getVoltage() {
        return v;
    }
    
    public float getCurrent() {
        return c;
    }
    
    public float getResist() {
        return r;
    }
    
    public static boolean isDefined(float f) {
        return f > 0;
    }
    
    public float[] computeValues() {
        if(!isDefined(v) || !isDefined(c) || !isDefined(r)) {
            if(!isDefined(v)) {
                if(isDefined(c) && isDefined(r)) {
                    v = c * r;
                }
            } else if(!isDefined(c)) {
                if(isDefined(v) && isDefined(r)) {
                    c = v / r;
                }
            } else {
                if(isDefined(v) && isDefined(c)) {
                    r = v / c;
                }
            }
        }
        return new float[]{v, c, r};
    }
    
    public void findSolution() {
        computeValues();
    }
    
    public boolean isComplete() {
        boolean b = true;
        for (Circuit sc : subCircuits) {
            b = b && sc.isComplete();
        }
        return b && isDefined(v) && isDefined(c) && isDefined(r);
    }
    
    public ArrayList<Circuit> getCircuits() {
        return subCircuits;
    }
    
    public String toString1() {
        return String.format("V:%.4f, I:%.4f, R:%.4f", v, c, r);
    }
    
    public String toString2() {
        return String.format("P:%.4f,", v * c);
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void tryCompute();
}
