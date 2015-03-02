/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core.radiance;

import static smallpt.core.Utility.Refl_t.*;
import static smallpt.core.Utility.*;
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
            if (!scene.intersect(r, isect)) return cl; // if miss, return black                        
            Sphere obj = scene.spheres[isect.id];        // the hit object
            Vec x=r.o.add(r.d.mul(isect.t)), n=(x.sub(obj.p)).norm(), nl=n.dot(r.d)<0?n:n.neg(), f=obj.c.clone();            
            double p = f.x>f.y && f.x>f.z ? f.x : f.y>f.z ? f.y : f.z; // max refl
            cl = cl.add(cf.mul(obj.e));
            if (++depth>5) if (Math.random()<p) f=f.mul(1/p); else return cl; //R.R.
            cf = cf.mul(f);
            
            if (obj.refl == DIFF) // Ideal DIFFUSE reflection
            { 
                double r1=2*M_PI*Math.random(), r2=Math.random(), r2s=sqrt(r2);
                Vec w=nl, u=((Math.abs(w.x)>.1? new Vec(0,1,0):new Vec(1)).cross(w)).norm(), v=w.cross(u);
                Vec d = (u.mul(cos(r1)*r2s).add(v.mul(sin(r1)*r2s)).add(w.mul(sqrt(1-r2)))).norm();
                //return obj.e + f.mult(radiance(Ray(x,d),depth,Xi));
                r = new Ray(x,d);
                continue;
            }            
        }
    }     
}
