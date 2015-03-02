/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core;

/**
 *
 * @author user
 */
import static smallpt.core.Utility.Refl_t.*;

public class Scene 
{
    public Sphere spheres[] = {
         new Sphere(1e5, new Vec( 1e5+1,40.8,81.6), new Vec(), new Vec(.75,.25,.25), DIFF),//Left
         new Sphere(1e5, new Vec(-1e5+99,40.8,81.6),new Vec(), new Vec(.25,.25,.75),DIFF),//Rght
         new Sphere(1e5, new Vec(50,40.8, 1e5), new Vec(), new Vec(.75,.75,.75),DIFF),//Back
         new Sphere(1e5, new Vec(50,40.8,-1e5+170), new Vec(), new Vec(), DIFF),//Frnt
         new Sphere(1e5, new Vec(50, 1e5, 81.6), new Vec(), new Vec(.75,.75,.75),DIFF),//Botm
         new Sphere(1e5, new Vec(50,-1e5+81.6,81.6),new Vec(), new Vec(.75,.75,.75),DIFF),//Top
         new Sphere(16.5,new Vec(27,16.5,47), new Vec(), new Vec(1,1,1).mul(0.999), SPEC),//Mirr
         new Sphere(16.5,new Vec(73,16.5,78), new Vec(), new Vec(1,1,1).mul(.999), REFR),//Glas
         new Sphere(600, new Vec(50,681.6-.27,81.6), new Vec(12,12,12), new Vec(), DIFF) //Lite 
    };
    
    public boolean intersect(Ray r, Isect isect)
    {
        int n = spheres.length;
        double d, inf=isect.t=1e20;
        for(int i = 0; i<n; i++) 
        {
            d = spheres[i].intersect(r);
            
            if(d > 0 && d < isect.t)
            {
                isect.t = d;
                isect.id = i;
            }
        }
        return isect.t<inf; 
    }
}
