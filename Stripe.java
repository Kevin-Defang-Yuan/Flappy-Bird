import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Stripe {
    private int x;
    private int y;
    private int frameWidth;
    private ImageIcon stripeImageIcon;
    private final int MOVESPEED = 5;
    

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

    public ImageIcon getStripeImageIcon() {
        return stripeImageIcon;
    }

    public void setStripeImageIcon(ImageIcon stripeImageIcon) {
        this.stripeImageIcon = stripeImageIcon;
    }

    public Stripe(int x, int y, int frameWidth) {
        this.x = x;
        this.frameWidth = frameWidth;
        this.y = y;
        this.stripeImageIcon = loadImage();
    }

    private ImageIcon loadImage() {
        return new ImageIcon("./Stripe.png");
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(stripeImageIcon.getImage(), x, y, null);
    }

    public void move() {
        x -= MOVESPEED;
        if (exited()) {
            this.x = frameWidth;
        }
    }

    public boolean exited() {
        if (x + stripeImageIcon.getIconWidth() <= 0) {
            return true;
        }
        return false;
    }



}
