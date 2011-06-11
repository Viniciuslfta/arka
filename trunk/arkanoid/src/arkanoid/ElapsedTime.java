/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

/**
 *
 * @author sPeC
 */
public class ElapsedTime {

    public static double milisecondsSince(long _start) {
        long total = System.nanoTime() - _start;

        return ((double) total / 1000000);
    }

    public static double milisecondsSince(long _start, double _clampValue) {
        double total = milisecondsSince(_start);
        total = total > _clampValue ? _clampValue : total;

        return total;
    }
}
