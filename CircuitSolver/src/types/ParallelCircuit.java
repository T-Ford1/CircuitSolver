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
public class ParallelCircuit extends Circuit {
    
    public ParallelCircuit(String n, Circuit... c) {
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
    
    private void setCurrent() {
        if(isDefined(getCurrent())) {
            setOtherCurrent();
        } else {
            setThisCurrent();
        }
    }
    
    private void setOtherCurrent() {
        int index = -1;
        float total = 0;
        for (int i = 0; i < subCircuits.size(); i++) {
            if(index == -1 && !isDefined(subCircuits.get(i).getCurrent())) {
                index = i;
            } else if(!isDefined(subCircuits.get(i).getCurrent())) {
                return;
            } else {
                total += subCircuits.get(i).getCurrent();
            }
        }
        if(index == -1) return;
        subCircuits.get(index).setCurrent(getCurrent() - total);
    }
    
    private void setThisCurrent() {
        float total = 0;
        for (Circuit sc : subCircuits) {
            if(!isDefined(sc.getCurrent())) {
                return;
            } else {
                total += sc.getCurrent();
            }
        }
        setCurrent(total);
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
                total += 1.0f/subCircuits.get(i).getResist();
            }
        }
        if(index == -1) return;
        subCircuits.get(index).setResist(1.0f/(1.0f/getResist() - total));
    }
    
    private void setThisResist() {
        float total = 0;
        for (Circuit sc : subCircuits) {
            if(!isDefined(sc.getResist())) {
                return;
            } else {
                total += 1.0f/sc.getResist();
            }
        }
        setResist(1.0f/total);
    }

    private void setVoltage() {
        float f = findVoltage();
        if (!isDefined(f)) {
            return;
        }
        setVoltage(f);
        for (Circuit sc : subCircuits) {
            sc.setVoltage(f);
        }
    }

    private float findVoltage() {
        if (isDefined(getVoltage())) {
            return getVoltage();
        }
        for (Circuit sc : subCircuits) {
            if (isDefined(sc.getVoltage())) {
                return sc.getVoltage();
            }
        }
        return -1;
    }
}
