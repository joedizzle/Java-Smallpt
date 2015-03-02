/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core;

/**
 *
 * @author user
 */

import java.awt.Color;
import static java.lang.Math.pow;

public class Utility 
{
    public enum Refl_t{DIFF, SPEC, REFR};
    
    public static final double M_PI = Math.PI;
    public static final double M_1_PI = 1./M_PI;
    
    public static double clamp(double x) 
    {
        return x<0 ? 0 : x>1 ? 1 : x;
    }
    
    public static int intRGB(Vec a)
    {
        int x = toInt(a.x);
        int y = toInt(a.y);
        int z = toInt(a.z);
        
        return new Color(x, y, z).getRGB();
    }
    
    public static int toInt(double x)
    { 
        return (int)(pow(clamp(x),1/2.2)*255+.5);
    }
    
    public static double rand()
    {
        return Math.random();
    }
    
    public static float cos(double rad)
    {
        return (float)Math.cos(rad);
    }
    
    public static float sin(double rad)
    {
        return (float)Math.sin(rad);
    }
    
    public static float sqrt(double value)
    {
        return (float)Math.sqrt(value);
    }
}
