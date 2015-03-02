/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smallpt;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import smallpt.core.ImageLoop;

/**
 *
 * @author user
 */
public class SmallPT extends JFrame
{

    /**
     * @param args the command line arguments
     */
    
    RenderDisplay display = new RenderDisplay(400, 400);
    
    public static void main(String[] args) 
    {
        SmallPT frame = new SmallPT();
        frame.setSize(600, 600);
        frame.add(frame.display);
        frame.setTitle("SmallPT");
        frame.centreFrame();         
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.start();
        
    }
    
    public void start()
    {
        ImageLoop loop = new ImageLoop(display);
        while(true)
        {                    
            loop.loop();
        }
        
    }
    
    public void centreFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        int x = (screenSize.width - frameSize.width)/2;
        int y = (screenSize.height - frameSize.height)/2;

        setLocation(x, y);
    }
}
