/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core;

/**
 *
 * @author user
 */
public class Camera {
    public Ray cam;
    public Vec cx;
    public Vec cy;
    
    public Camera(int w, int h)
    {
        cam = new Ray(new Vec(50,52,295.6), new Vec(0,-0.042612,-1).norm());
        cx = new Vec(w*.5135/h, 0, 0);
        cy = (cx.cross(cam.d)).norm().mul(.5135);
    }
    
    public Camera(Vec pos, Vec dir, double w, double h)
    {
        cam = new Ray(pos, dir.norm());
        cx = new Vec(w*.5135/h, 0, 0);
        cy = (cx.cross(cam.d)).norm().mul(.5135);
    }
    
    public Vec o()
    {
        return cam.o;
    }
    
    public Vec d()
    {
        return cam.d;
    }
}
