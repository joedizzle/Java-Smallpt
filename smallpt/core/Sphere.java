/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core;

/**
 *
 * @author user
 */

import static java.lang.Math.sqrt;
import smallpt.core.Utility.Refl_t;

public class Sphere
{
    public double rad;
    public Vec p, e, c;
    public Refl_t refl;
    
    public Sphere(double rad, Vec p, Vec e, Vec c, Refl_t refl)
    {
        this.rad = rad; this.p = p; this.e = e; this.c = c; this.refl = refl;
    }
    
    public double intersect(Ray r)
    {        
        Vec op = p.sub(r.o);
        double t, eps=1e-4, b=op.dot(r.d), det=b*b-op.dot(op)+rad*rad;
        if (det<0) 
            return 0; 
        else 
            det=sqrt(det);
        
        return (t=b-det)>eps ? t : ((t=b+det)>eps ? t : 0); 
    }
}
