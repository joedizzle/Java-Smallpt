/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core.radiance;

import static smallpt.core.Utility.Refl_t.*;
import static smallpt.core.Utility.rand;
import smallpt.core.*;

/**
 *
 * @author user
 */
public class Forward 
{
    public static Vec radiance(Ray r_, int depth_, Scene scene)
    {
        Isect isect = new Isect();
        Ray r = r_;
        int depth = depth_;
        
        Vec cl = new Vec();
        Vec cf = new Vec(1, 1, 1);
        
        while(true)
        {
            break;
        }
        return new Vec();
    }
}
