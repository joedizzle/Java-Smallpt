/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class RenderDisplay extends JPanel
{
    Tile tile = null;
    
    public RenderDisplay(int width, int height)
    {
        tile = new Tile(width, height);
    }
    
    public void setRGB(int x, int y, int rgb)
    {
        tile.setRGB(x, y, rgb);
    }
    
    public int width()
    {
        return tile.getWidth();
    }
    
    public int height()
    {
        return tile.getHeight();
    }
            
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        int width = getWidth();
        int height = getHeight();
        
        if(tile != null)
        {
            int w = tile.getWidth(), h = tile.getHeight();
            
            int x = (width - w)/2;
            int y = (height - h)/2;
            
            g2d.drawImage(tile, x, y, w, h, this);
        }        
    }
}
