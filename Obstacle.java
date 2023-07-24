import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Obstacle {
    private Pipe topPipe;
    private Pipe bottomPipe;
    private final int GAP = 150;

    private int height;
    private boolean completed = false;
    private int width;
    
    

    public Pipe getTopPipe() {
        return topPipe;
    }
    public void setTopPipe(Pipe topPipe) {
        this.topPipe = topPipe;
    }
    public Pipe getBottomPipe() {
        return bottomPipe;
    }
    public void setBottomPipe(Pipe bottomPipe) {
        this.bottomPipe = bottomPipe;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public ArrayList<Pipe> getPipes() {
        ArrayList<Pipe> pipes = new ArrayList<>();
        pipes.add(topPipe);
        pipes.add(bottomPipe);
        return pipes;
    }
    public void spawn(int x) {
        // Generate 1 random number between 10% and 60%
        double ran_percent = ((Math.random() * 50) + 10) / 100; 

        // topPipe
        int topHeight = (int) (height * ran_percent);
        topPipe = new Pipe(x, 0, topHeight, Pipe.TOP);

        // bottomPipe
        int bottomY = topPipe.getHeight() + GAP;
        int bottomHeight = height - topPipe.getHeight() - GAP;
        bottomPipe = new Pipe(x, bottomY, bottomHeight, Pipe.BOTTOM);


        // Reset clear attribute
        setCompleted(false);
    }

    public void paint(Graphics2D g2d) {
        topPipe.paint(g2d);
        bottomPipe.paint(g2d);
    }


    public void move() {
        topPipe.move();
        bottomPipe.move();
        if (exited()) {
            this.spawn(width);
        }
    }

    public boolean exited() {
        if (topPipe.getX() + topPipe.getWidth() <= 0) {
            return true;
        }
        return false;
    }

    // Old collision logic assuming rectangles
    // public boolean collide(Rectangle r) {
    //     if (r.intersects(topPipe.toShape()) || r.intersects(bottomPipe.toShape())) {
    //         return true;
    //     }
    //     return false;
    // }

    public boolean collide(Ellipse2D e) {
        if (e.intersects(topPipe.getX(), topPipe.getY(), topPipe.getWidth(), topPipe.getHeight()) || 
            e.intersects(bottomPipe.getX(), bottomPipe.getY(), bottomPipe.getWidth(), bottomPipe.getHeight())) {
            return true;
        }
        return false;
    }

    public boolean cleared(int x) {
        if (!completed && x >= this.topPipe.getX() + topPipe.getWidth()) {
            setCompleted(true);
            return true;
        }
        return false;
    }

    public Obstacle(int gameWidth, int gameHeight, int groundHeight, int x) {
        
        this.height = gameHeight - groundHeight;
        this.width = gameWidth;
        this.spawn(x);

    }
    
}
