/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package types;

import circuitsolver.Circuit;
import utils.CircuitParam;
import utils.Current;
import utils.Resist;
import utils.Voltage;

/**
 *
 * @author Terrell
 */
public class SeriesCircuit extends Circuit {
    
    public SeriesCircuit(String n, Circuit... c) {
        super(n, c);
    }

    public void tryCompute() {
        
        setCurrent();

        setResist();
        
        setVoltage();
        
        computeValues();
        
        for (Circuit sc : subCircuits) {
            if (!sc.isComplete()) {
                sc.tryCompute();
            }
        }
    }
    
    private void setVoltage() {
        if(isDefined(getVoltage())) {
            setOtherVoltage();
        } else {
            setThisVoltage();
        }
    }
    
    private void setOtherVoltage() {
        int index = -1;
        float total = 0;
        for (int i = 0; i < subCircuits.size(); i++) {
            if(index == -1 && !isDefined(subCircuits.get(i).getVoltage())) {
                index = i;
            } else if(!isDefined(subCircuits.get(i).getVoltage())) {
                return;
            } else {
                total += subCircuits.get(i).getVoltage();
            }
        }
        if(index == -1) return;
        subCircuits.get(index).setVoltage(getVoltage() - total);
    }
    
    private void setThisVoltage() {
        float total = 0;
        for (Circuit sc : subCircuits) {
            if(!isDefined(sc.getVoltage())) {
                return;
            } else {
                total += sc.getVoltage();
            }
        }
        setVoltage(total);
    }
    
    private void setResist() {
        if(isDefined(getResist())) {
            setOtherResist();
        } else {
            setThisResist();
        }
    }
    
    private void setOtherResist() {
        int index = -1;
        float total = 0;
        for (int i = 0; i < subCircuits.size(); i++) {
            if(index == -1 && !isDefined(subCircuits.get(i).getResist())) {
                index = i;
            } else if(!isDefined(subCircuits.get(i).getResist())) {
                return;
            } else {
                total += subCircuits.get(i).getResist();
            }
        }
        if(index == -1) return;
        subCircuits.get(index).setResist(getResist() - total);
    }
    
    private void setThisResist() {
        float total = 0;
        for (Circuit sc : subCircuits) {
            if(!isDefined(sc.getResist())) {
                return;
            } else {
                total += sc.getResist();
            }
        }
        setResist(total);
    }

    private void setCurrent() {
        float f = findCurrent();
        if (!isDefined(f)) {
            return;
        }
        setCurrent(f);
        for (Circuit sc : subCircuits) {
            sc.setCurrent(f);
        }
    }

    private float findCurrent() {
        if (isDefined(getCurrent())) {
            return getCurrent();
        }
        for (Circuit sc : subCircuits) {
            if (isDefined(sc.getCurrent())) {
                return sc.getCurrent();
            }
        }
        return -1;
    }
}
