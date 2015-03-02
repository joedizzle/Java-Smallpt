/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core.radiance;

import smallpt.core.Isect;
import smallpt.core.Ray;
import smallpt.core.Scene;
import smallpt.core.Vec;

/**
 *
 * @author user
 */
public class Explicit
{
    public Vec radiance(Ray r, int depth, Scene scene)
    { 
        Isect isect = new Isect();
        float t;
        int id = 0;
        if(!scene.intersect(r, isect)) return new Vec();
        
        
        return null;
    }
}
