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
import static smallpt.core.Utility.*;

public class Scatter 
{
    public static Ray diff(Vec n, Vec x)
    {
        double r1=2*M_PI*rand();
        double r2=rand(), r2s=sqrt(r2);
        Vec w=n;
        Vec u=((abs(w.x)>.1? new Vec(0, 1, 0): new Vec(1, 0, 0)).cross(w)).norm();
        Vec v=w.cross(u);
        Vec d = (u.mul(cos(r1)).mul(r2s).add(v.mul(sin(r1)).mul(r2s)).add(w.mul(sqrt(1-r2)))).norm();
        return new Ray(x, d);        
    }
    
    public static Ray refl(Vec n, Vec x, Vec i) //normal, position, incidence
    {
        return new Ray(x, i.sub(n.mul(2*n.dot(i))));
    }
    
    public static Ray refr(Vec n, Vec x, Vec i, double n1, double n2) //normal, position, incidence
    {
        double nn  = n1 / n2;
        double cosI = -n.dot(i);
        double sinT2 = nn * nn * (1. - cosI * cosI);
        if(sinT2 > 1.0) return null;//return refl(n, x, i);//return null; //TIR
        double cosT = sqrt(1. - sinT2);
        Vec d = i.mul(nn).add(n.mul(nn * cosI - cosT));
        return new Ray(x, d);
    }
    
    public static boolean tir(Vec n, Vec x, Vec i, double n1, double n2)
    {
        double nn  = n1 / n2;
        double cosI = -n.dot(i);
        double sinT2 = nn * nn * (1. - cosI * cosI);
        if(sinT2 > 1.0)
            return true;
        else
            return false;
    }
    
    public static double schlick(Vec n, Vec i, double n1, double n2) //normal, incidence
    {
        double r0 = (n1 - n2) / (n1 + n2);
        r0 *= r0;
        double cosX = -n.dot(i);
        if(n1 > n2)
        {
            double nn = n1 / n2;
            double sinT2 = nn * nn * (1. - cosX * cosX);
            if(sinT2 > 0) //TIR
                return 1; 
            cosX = sqrt(1.0 - sinT2);
        }
        double x = 1. - cosX;
        return r0 + (1. - r0) * x * x * x * x * x;
    }
}
