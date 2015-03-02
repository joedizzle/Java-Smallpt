/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smallpt;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;



/**
 *
 * @author Joe Mwangi
 */
public class Tile extends BufferedImage
{    
    int width, height;
    DataBuffer dataBuffer;
    private int w1;
    private int h1;

    public Tile(int width, int height)
    {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        this.width = width;
        this.height = height;
        this.w1 = (width - 1);
        this.h1 = (height - 1);
        this.dataBuffer = this.getRaster().getDataBuffer();
        this.setNull();
    }
    
    public final void setNull()
    {
        for(int j = 0; j<height; j++)
            for(int i = 0; i<width; i++)
                setRGB(i, j, Color.BLACK.getRGB());
    }

    @Override
    public synchronized void setRGB(int x, int y, int rgb)
    {
        dataBuffer.setElem(y*width + x, rgb);
    }
    
    public synchronized void setRGB(int x, int y, int w, int h, int rgb)
    {
        for (int dx = Math.min(x + w , this.w1); dx >= x; dx--)
            for (int dy = Math.min(y + h, this.h1); dy >= y; dy--)
                this.dataBuffer.setElem(dy * this.width + dx, rgb);

    }
    
    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }
  
}
