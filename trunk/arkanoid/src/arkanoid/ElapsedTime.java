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
    public static double microsecondsSince(long _start)
    {
        long total = System.nanoTime() - _start;
        
        return (double)total / 1000000;
        
    }
}
