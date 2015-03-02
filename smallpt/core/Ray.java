/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt.core;

/**
 *
 * @author user
 */
public class Ray 
{
    public Vec o, d;    
    public Ray(Vec o, Vec d) {this.o = o; this.d = d;}
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("Ray: ").append("\n");
        builder.append("         o    ").append(String.format("(%.5f, %.5f, %.5f)", o.x, o.y, o.z)).append("\n");
        builder.append("         d    ").append(String.format("(%.5f, %.5f, %.5f)", d.x, d.y, d.z)).append("\n");
                        
        return builder.toString();   
    }
}
