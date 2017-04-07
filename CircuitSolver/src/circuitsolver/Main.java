/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circuitsolver;

import javax.swing.JOptionPane;
import types.ParallelCircuit;
import types.Resistor;
import types.SeriesCircuit;
import utils.Resist;

/**
 *
 * @author Terrell
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Circuit system = getProblemOne("Problem 1");

        int tries = 100;
        while (tries-- > 0 && !system.isComplete()) {
            system.tryCompute();
        }
        JOptionPane.showMessageDialog(null, printCircuit(system, 0, true));

        system = getProblemOne("Problem 2");

        tries = 100;
        while (tries-- > 0 && !system.isComplete()) {
            system.tryCompute();
        }
        JOptionPane.showMessageDialog(null, printCircuit(system, 0, true));

        system = getProblemTwo("Problem 3");

        tries = 100;
        while (tries-- > 0 && !system.isComplete()) {
            system.tryCompute();
        }
        JOptionPane.showMessageDialog(null, printCircuit(system, 0, false));

        system = getProblemTwo("Problem 4");

        tries = 100;
        while (tries-- > 0 && !system.isComplete()) {
            system.tryCompute();
        }
        JOptionPane.showMessageDialog(null, printCircuit(system, 0, false));
    }

    private static String printCircuit(Circuit c, int level, boolean a) {
        String s = "";
        //s += printWithLevel(c.getName() + " has " + c.subCircuits.size() + " children", level) + "\n";
        if (c.getName().contains("R") || c.getName().contains("L")) {
            s += printWithLevel(c.getName() + " " + (a ? c.toString1() : c.toString2()), level) + "\n";
        }
        for (Circuit sc : c.getCircuits()) {
            s += printCircuit(sc, level + 1, a);
        }
        return s;
    }

    private static String printWithLevel(String s, int level) {
        String str = "";
        for (int i = 0; i < level; i++) {
            str += "\t";
        }
        return str + s.replaceAll("-1.000", "UNDF");
    }

    private static Circuit getProblemOne(String name) {
        String s = JOptionPane.showInputDialog(null, "Input VT,R1,R2,R3,R4,R5,R6,R7,R8,R9", name, 0);
        String[] data = s.split(",");
        float[] vals = new float[data.length];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = Float.parseFloat(data[i]);
        }
        Resistor r1 = new Resistor("R1", new Resist(vals[1]));

        Resistor r2 = new Resistor("R2", new Resist(vals[2]));

        Resistor r3 = new Resistor("R3", new Resist(vals[3]));
        Resistor r4 = new Resistor("R4", new Resist(vals[4]));
        ParallelCircuit p34 = new ParallelCircuit("P34", r3, r4);
        SeriesCircuit s2_p34 = new SeriesCircuit("S2_P34", r2, p34);

        Resistor r5 = new Resistor("R5", new Resist(vals[5]));

        Resistor r6 = new Resistor("R6", new Resist(vals[6]));
        Resistor r7 = new Resistor("R7", new Resist(vals[7]));
        ParallelCircuit p67 = new ParallelCircuit("P67", r6, r7);

        Resistor r8 = new Resistor("R8", new Resist(vals[8]));
        SeriesCircuit s5_p67_8 = new SeriesCircuit("S5_P67_8", r5, p67, r8);

        Resistor r9 = new Resistor("R9", new Resist(vals[9]));

        ParallelCircuit p = new ParallelCircuit("MID", s2_p34, s5_p67_8);

        SeriesCircuit root = new SeriesCircuit("ROOT", r1, p, r9);
        root.setVoltage(vals[0]);
        return root;
    }

    private static Circuit getProblemTwo(String name) {
        String s = JOptionPane.showInputDialog(null, "Input VT,L1,L2,L3,L4", name, 0);
        String[] data = s.split(",");
        float[] vals = new float[data.length];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = Float.parseFloat(data[i]);
        }
        Resistor r1 = new Resistor("L1", new Resist(vals[1]));

        Resistor r2 = new Resistor("L2", new Resist(vals[2]));

        Resistor r3 = new Resistor("L3", new Resist(vals[3]));
        Resistor r4 = new Resistor("L4", new Resist(vals[4]));

        ParallelCircuit p12 = new ParallelCircuit("P12", r1, r2);
        ParallelCircuit p34 = new ParallelCircuit("P34", r3, r4);

        SeriesCircuit root = new SeriesCircuit("ROOT", p12, p34);
        root.setVoltage(vals[0]);
        return root;
    }
}
