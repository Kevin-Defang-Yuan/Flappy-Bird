import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Pipe {
    private int x;
    private int y;
    private final int MOVESPEED = 5;
    private int orientation;
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    private ImageIcon pipeImageIcon;
    private ImageIcon pipeExtImageIcon; 
    private int width;
    private int height;

    public int getOrientation() {
        return orientation;
    }
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public Pipe(int x, int y, int height, int orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.height = height;
        if (orientation == TOP) {
            this.pipeImageIcon = loadImageIcon("./TopPipe.png");
        }
        else {
            this.pipeImageIcon = loadImageIcon("./BottomPipe.png");
        }

        
        this.pipeExtImageIcon = loadImageIcon("./PipeExtension.png");
        this.width = pipeImageIcon.getIconWidth();
        
        
        
    }

    public void paint(Graphics2D g2d) {
        if (this.orientation == TOP) {
            g2d.drawImage(pipeImageIcon.getImage(), x, height - pipeImageIcon.getIconHeight(), null);
            int drawHeight = height - pipeImageIcon.getIconHeight();
            while (drawHeight > 0) {
                g2d.drawImage(pipeExtImageIcon.getImage(), x, drawHeight - pipeExtImageIcon.getIconHeight(), null);
                drawHeight -= pipeExtImageIcon.getIconHeight();
            } 
        }
        else {
            g2d.drawImage(pipeImageIcon.getImage(), x, y, null);
            int drawHeight = pipeImageIcon.getIconHeight();
            while (drawHeight < height) {
                g2d.drawImage(pipeExtImageIcon.getImage(), x, y + drawHeight, null);
                drawHeight += pipeExtImageIcon.getIconHeight();
            }
        }
       
    }

    public void move() {
        x -= MOVESPEED;
    }
    public Rectangle toShape() {
        return new Rectangle(x, y, width, height);
    }

    private ImageIcon loadImageIcon(String url) {
        return new ImageIcon(url);
    }
    
    
}
