import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import javax.sound.sampled.AudioFileFormat;
import javax.swing.ImageIcon;

public class Bird {
    // private final int SIZE = 50;
    private int width;
    private int height;
    private int x;
    private int y;
    private int v;
    private final int ACCELERATION = 2;
    private final int JUMP_VELOCITY = -15;
    private final int STARTING_VELOCITY = 20;
    private final int STARTING_X_POSITION = 40;
    private Game game;
    private ImageIcon birdImageIcon;


    public ImageIcon getBirdImageIcon() {
        return birdImageIcon;
    }


    public void setBirdImage(ImageIcon birdImageIcon) {
        this.birdImageIcon = birdImageIcon;
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


    public int getV() {
        return v;
    }


    public void setV(int v) {
        this.v = v;
    }





    public Bird(Game game) {
        this.x = STARTING_X_POSITION;
        this.y = 200;
        this.birdImageIcon = loadImageIcon();
        this.width = birdImageIcon.getIconWidth();
        this.height = birdImageIcon.getIconHeight();
        this.v = STARTING_VELOCITY;
        this.game = game;
        
    }

    public void paint(Graphics2D g2d) {
        // g2d.drawImage(birdImageIcon.getImage(), x, y, null);
        // AffineTransform backup = g2d.getTransform();
        // AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI/4, getX() + getWidth() / 2, getY() + getHeight() / 2);
        // g2d.setTransform(rotate);
        // g2d.drawImage(birdImageIcon.getImage(), x, y, null);
        // g2d.setTransform(backup);
        // g2d.drawImage(birdImageIcon.getImage(), x, y, null);
        AffineTransform backup = g2d.getTransform();
        g2d.rotate(calculateAngle(), getX() + getWidth() / 2, getY() + getHeight() / 2);
  
        g2d.drawImage(birdImageIcon.getImage(), x, y, null);
        g2d.setTransform(backup);


        
    }

    private double calculateAngle() {
        
        if (v > 35) {
            return Math.PI/2;
        }
        if (v < 15) {
            return -Math.PI/4;
        }
        return ((v-15) * 3 * Math.PI / 4) / 20 - Math.PI / 4;
    }


    // For now, the height and width are equal to the width of bird
    private ImageIcon loadImageIcon() {
        return new ImageIcon("./FlappyBird3.png");
    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            v = JUMP_VELOCITY;
        }
    }

    public void move() {
        y += v;
        v += ACCELERATION;        
    }

    public void fakeMove() {
        int fakeAcceleration = 5;
        if (y < 150) {
            fakeAcceleration = 5;
        } 
        if (y > 275) {
            fakeAcceleration = -5;
        }
        y += v;
        v += fakeAcceleration;
    }

    public void autoMove() {
        Obstacle o = game.getLandscape().getNextObstacle();
        if (y + height >= o.getBottomPipe().getY() - 20) {
            v = JUMP_VELOCITY;
            // System.out.println("y: " + y);
            // System.out.println("pipe: " + o.getBottomPipe().getY());
        }
        move();
        
    }



    public Ellipse2D toShape() {
        return new Double(x, y, width, height);
    } 

    // public Rectangle toShape() {
    //     // return new Rectangle(x, y, width, height);
    // }

} 
