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
import smallpt.RenderDisplay;
import static smallpt.core.Utility.clamp;
import static smallpt.core.Utility.rand;
import smallpt.core.radiance.Forward;

public class ImageLoop 
{
    private Scene scene = new Scene();
    private RenderDisplay display;
    
    private int w, h, samps = 1;
    private double iteration = 1;
    private Vec r = new Vec();
    private Vec [] c;
    private Camera cam;
    
    public ImageLoop(RenderDisplay display)
    {
        this.display = display;
        
        this.w = display.width();
        this.h = display.height();
        
        this.c = new Vec[w*h];
        this.cam = new Camera(w, h);
        
        for(int i = 0; i<c.length; i++)
        {
            c[i] = new Vec();
        }
    }
   
    public void loop()
    {
        System.out.println("Looping in scene");
        
        for (int y=0; y<h; y++){ // Loop over image rows
            //fprintf(stderr,"\rRendering (%d spp) %5.2f%%",samps*4,100.*y/(h-1));            
            //short [] Xi = {0, 0, (short)(y*y*y)};
            
            for (short x=0; x<w; x++) // Loop cols
                for (int sy=0, i=(h-y-1)*w+x; sy<2; sy++) // 2x2 subpixel rows
                    for (int sx=0; sx<2; sx++, r = new Vec())
                    { // 2x2 subpixel cols
                        for (int s=0; s<samps; s++)
                        {
                            double r1=2*rand(), dx=r1<1 ? sqrt(r1)-1: 1-sqrt(2-r1);
                            double r2=2*rand(), dy=r2<1 ? sqrt(r2)-1: 1-sqrt(2-r2);
                            Vec d = cam.cx.mul(((sx+.5 + dx)/2 + x)/w - .5).add(
                                    cam.cy.mul(((sy+.5 + dy)/2 + y)/h - .5)).add(cam.d());
                            r = r.add(Forward.radiance(new Ray(cam.o().add(d.mul(140)), d.norm()), 0, scene).mul(.25));                           
                        }
                        // Camera rays are pushed ^^^^^ forward to start in interior
                        c[i] = c[i].add(new Vec(clamp(r.x),clamp(r.y),clamp(r.z)).mul(.25/iteration));                        
                    }            
        }           
        iteration++;
                
        System.out.println("Finished looping in scene now updating image");
        
        for(int j = 0; j<h; j++)
            for(int i = 0; i<w; i++)
            {
                int index = getScreenIndex(i, j);
                int rgb = Utility.intRGB(c[index]);                
                display.setRGB(i, j, rgb);
            }
        System.out.println("Finished updating image");
        display.repaint();
    }
    
    private int getScreenIndex(int x, int y)
    {
        return y*w + x;
    }
}
