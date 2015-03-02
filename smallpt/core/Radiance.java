/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core;

/**
 *
 * @author user
 */

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static smallpt.core.Utility.Refl_t.DIFF;
import static smallpt.core.Utility.Refl_t.SPEC;
import static smallpt.core.Utility.*;

public class Radiance 
{
    
    public static Vec radiance2(Ray r, int depth, Scene scene)
    {
        Isect isect = new Isect();
        
        if(!scene.intersect(r, isect)) return new Vec(); //if misses, return black
        Sphere obj = scene.spheres[isect.id];            //the hit object
        
        if(depth>10) return new Vec();
        
        Vec x=r.o.add(r.d.mul(isect.t)); //ray intersection point
        Vec n=(x.sub(obj.p)).norm();     //sphere normal
        Vec nl=n.dot(r.d)<0?n:n.mul(-1); //proper oriented surface normal
        Vec f=obj.c;                     //object color (BRDF modulator)
                
        //Use maximum reflectivity amount for Russian Roulette
        double p = f.x>f.y && f.x>f.z ? f.x : f.y>f.z ? f.y : f.z; // max refl
        if (++depth>5) if (rand()<p) f=f.mul(1/p); else return obj.e; //R.R.
        
        // Ideal DIFFUSE reflection
        if (obj.refl == DIFF)
        { 
            double r1=2*M_PI*rand();
            double r2=rand(), r2s=sqrt(r2);
            Vec w=nl;
            Vec u=((abs(w.x)>.1? new Vec(0, 1, 0): new Vec(1, 0, 0)).cross(w)).norm();
            Vec v=w.cross(u);
            Vec d = (u.mul(cos(r1)).mul(r2s).add(v.mul(sin(r1)).mul(r2s)).add(w.mul(sqrt(1-r2)))).norm();
            return obj.e.add(f.mul(radiance2(new Ray(x,d),depth, scene)));
        }
        else if (obj.refl == SPEC) // Ideal SPECULAR reflection            
            return obj.e.add(f.mul(radiance2(new Ray(x, r.d.sub(n.mul(2*n.dot(r.d)))), depth, scene)));
        
        //OTHERWISE WE HAVE A DIELECTRIC (GLASS) SURFACE
        Ray reflRay = new Ray(x, r.d.sub(n.mul(2*n.dot(r.d)))); // Ideal dielectric REFRACTION
        boolean into = n.dot(nl)>0; // Ray from outside going in?
        double nc=1, nt=1.5, nnt=into?nc/nt:nt/nc, ddn=r.d.dot(nl), cos2t;
        
        //IF TOTAL INTERNAL REFLECTION, REFLECT
        if ((cos2t=1-nnt*nnt*(1-ddn*ddn))<0) // Total internal reflection
            return obj.e.add(f.mul(radiance2(reflRay,depth,scene)));
        
        //OTHERWISE, CHOOSE REFLECTION OR REFRACTION
        Vec tdir = (r.d.mul(nnt).sub(n.mul((into?1:-1)*(ddn*nnt+sqrt(cos2t))))).norm();
        double a=nt-nc, b=nt+nc, R0=a*a/(b*b), c = 1-(into?-ddn:tdir.dot(n));
        double Re=R0+(1-R0)*c*c*c*c*c,Tr=1-Re,P=.25+.5*Re,RP=Re/P,TP=Tr/(1-P);
        return obj.e.add(f.mul(depth>2 ? (rand())<P ? // Russian roulette
                radiance2(reflRay,depth,scene).mul(RP)  :  radiance2(new Ray(x,tdir),depth,scene).mul(TP) :
                radiance2(reflRay,depth,scene).mul(Re).add(radiance2(new Ray(x,tdir),depth,scene).mul(Tr)))); 
    }
    
    public static Vec radiance(Ray r_, int depth_, Scene scene)
    {
        Isect isect = new Isect();                              // id of intersected object
        Ray r=r_;
        int depth=depth_;
        
        // L0 = Le0 + f0*(L1)
        //    = Le0 + f0*(Le1 + f1*L2)
        //    = Le0 + f0*(Le1 + f1*(Le2 + f2*(L3))
        //    = Le0 + f0*(Le1 + f1*(Le2 + f2*(Le3 + f3*(L4)))
        //    = ...
        //    = Le0 + f0*Le1 + f0*f1*Le2 + f0*f1*f2*Le3 + f0*f1*f2*f3*Le4 + ...
        // 
        // So:
        // F = 1
        // while (1){
        //   L += F*Lei
        //   F *= fi
        // }

        Vec cl = new Vec(0,0,0);   // accumulated color
        Vec cf = new Vec(1,1,1);  // accumulated reflectance
        
        while(true)
        {
            if(!scene.intersect(r, isect)) return new Vec(); //if misses, return black
            Sphere obj = scene.spheres[isect.id];            //the hit object
        
            Vec x=r.o.add(r.d.mul(isect.t)); //ray intersection point
            Vec n=(x.sub(obj.p)).norm();     //sphere normal
            Vec nl=n.dot(r.d)<0?n:n.mul(-1); //proper oriented surface normal
            Vec f=obj.c;                     //object color (BRDF modulator)
            
            //Use maximum reflectivity amount for Russian Roulette
            double p = f.x>f.y && f.x>f.z ? f.x : f.y>f.z ? f.y : f.z; // max refl
            cl = cl.add(cf.mul(obj.e));
            if (++depth>5) if (rand()<p) f=f.mul(1/p); else return obj.e; //R.R.
            cf = cf.mul(f);
            
             // Ideal DIFFUSE reflection
            if (obj.refl == DIFF)
            { 
                double r1=2*M_PI*rand();
                double r2=rand(), r2s=sqrt(r2);
                Vec w=nl;
                Vec u=((abs(w.x)>.1? new Vec(0, 1, 0): new Vec(1, 0, 0)).cross(w)).norm();
                Vec v=w.cross(u);
                Vec d = (u.mul(cos(r1)).mul(r2s).add(v.mul(sin(r1)).mul(r2s)).add(w.mul(sqrt(1-r2)))).norm();
                //return obj.e.add(f.mul(radiance(new Ray(x,d),depth, scene)));
                r = new Ray(x,d);
                continue;
            }
            else if (obj.refl == SPEC) // Ideal SPECULAR reflection   
            {
                //return obj.e.add(f.mul(radiance(new Ray(x, r.d.sub(n.mul(2*n.dot(r.d)))), depth, scene)));
                r = new Ray(x, r.d.sub(n.mul(2*n.dot(r.d))));
                continue;             
            }
            
            //OTHERWISE WE HAVE A DIELECTRIC (GLASS) SURFACE
            Ray reflRay = new Ray(x, r.d.sub(n.mul(2*n.dot(r.d)))); // Ideal dielectric REFRACTION
            boolean into = n.dot(nl)>0; // Ray from outside going in?
            double nc=1, nt=1.5, nnt=into?nc/nt:nt/nc, ddn=r.d.dot(nl), cos2t;
        
            //IF TOTAL INTERNAL REFLECTION, REFLECT
            if ((cos2t=1-nnt*nnt*(1-ddn*ddn))<0) // Total internal reflection
            {
                //return obj.e.add(f.mul(radiance(reflRay,depth,scene)));
                r = reflRay;
                continue;
            }
            
            //OTHERWISE, CHOOSE REFLECTION OR REFRACTION
            Vec tdir = (r.d.mul(nnt).sub(n.mul((into?1:-1)*(ddn*nnt+sqrt(cos2t))))).norm();
            double a=nt-nc, b=nt+nc, R0=a*a/(b*b), c = 1-(into?-ddn:tdir.dot(n));
            double Re=R0+(1-R0)*c*c*c*c*c,Tr=1-Re,P=.25+.5*Re,RP=Re/P,TP=Tr/(1-P);
            //return obj.e.add(f.mul(depth>2 ? (rand())<P ? // Russian roulette
            //        radiance(reflRay,depth,scene).mul(RP)  :  radiance(new Ray(x,tdir),depth,scene).mul(TP) :
            //        radiance(reflRay,depth,scene).mul(Re).add(radiance(new Ray(x,tdir),depth,scene).mul(Tr))));     
            
            if (rand()<P)
            {
                cf = cf.mul(RP);
                r = reflRay;
            }
            else
            {
                cf = cf.mul(TP);
                r = new Ray(x,tdir);
            }
            continue;
        }
        //return null;
    }
}
