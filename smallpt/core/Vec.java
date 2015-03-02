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

public class Vec {
    public double x, y, z;
    
    public Vec() {x = 0; y = 0; z = 0;}
    public Vec(double x) {this(x, x, x);}
    public Vec(double x, double y, double z){this.x = x; this.y = y; this.z = z;}
    public Vec(Vec b) {this.x = b.x; this.y = b.y; this.z = b.z;}
    
    public Vec add(Vec b) {return new Vec(x+b.x, y+b.y, z+b.z);}
    public Vec sub(Vec b) {return new Vec(x-b.x, y-b.y, z-b.z);}
    public Vec mul(Vec b) {return new Vec(x*b.x, y*b.y, z*b.z);}
    public Vec mul(double b) {return new Vec(x*b, y*b, z*b);}
    
    public Vec norm() {return new Vec(this.mul(1./sqrt(x*x + y*y + z*z)));}
    public double dot(Vec b) {return x*b.x + y*b.y + z*b.z;}
    public Vec cross(Vec b) {return new Vec(y*b.z-z*b.y,z*b.x-x*b.z,x*b.y-y*b.x);}
    
    public Vec neg(){return new Vec(x*-1, y*-1, z*-1);}
    @Override
    public Vec clone(){return new Vec(x, y, z);}
    
    @Override
    public String toString()
    {
        return "x" +x+ " y " +y+ " z " +z;
    }
}
