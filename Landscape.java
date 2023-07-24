import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Landscape {
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Stripe> stripes = new ArrayList<>();
    private int width;
    private int height;
    private final int XDIFF = 250;
    public int GROUND_HEIGHT = 70;


    public Landscape(int width, int height) {
        this.width = width;
        this.height = height;
        this.obstacles.add(new Obstacle(this.width, this.height, GROUND_HEIGHT, this.width));
        this.obstacles.add(new Obstacle(this.width, this.height, GROUND_HEIGHT, this.width + XDIFF));
        this.stripes.add(new Stripe(0, this.height - GROUND_HEIGHT, width));
        this.stripes.add(new Stripe(width, this.height - GROUND_HEIGHT, width));
    }

    public void paint(Graphics2D g2d) {
        for (Obstacle o : obstacles) {
            o.paint(g2d);
        }
        
        g2d.setColor(new Color(210, 180, 140));
        g2d.fillRect(0, height - GROUND_HEIGHT, width, GROUND_HEIGHT);
        for (Stripe s : stripes) {
            s.paint(g2d);
        }
    }

    public void move() {
        for (Obstacle o : obstacles) {
            o.move();
        }
        for (Stripe s : stripes) {
            s.move();
        }
    }

    public void fakeMove() {
        for (Stripe s : stripes) {
            s.move();
        }
    }

    public boolean obstacleCleared(int x) {
        for (Obstacle o : obstacles) {
            if (o.cleared(x)) {
                return true;
            }
        }
        return false;

    }

    public boolean collide(Bird b) {
        // Pass in the center point and radius
        //collide with pipes
        for (Obstacle o : obstacles) {
            if (o.collide(b.toShape())) {
                return true;
            }
        }

        //collide with ground
        if (b.getY() + b.getHeight() >= height - GROUND_HEIGHT) {
            return true;
        }

        return false;
    }

    public Obstacle getNextObstacle() {
        if (obstacles.get(0).isCompleted()) {
            return obstacles.get(1);
        }
        if (obstacles.get(1).isCompleted()) {
            return obstacles.get(0);
        }
        
        if (obstacles.get(0).getTopPipe().getX() <= obstacles.get(1).getTopPipe().getX()) {
            return obstacles.get(0);
        }
        return obstacles.get(1);
    }
}
