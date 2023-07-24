import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class Game extends JPanel {
    private static final int DEFAULT = 0;
    private static final int AUTO = 1;
    public int GAME_HEIGHT = 600;
    private int LABEL_HEIGHT = 50;
    private int FRAME_WIDTH = 400;
    
    private Bird bird;
    private int score = 0;
    private boolean start = false;

    private Landscape landscape;
    private int mode; 

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public Landscape getLandscape() {
        return landscape;
    }

    public void setLandscape(Landscape landscape) {
        this.landscape = landscape;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Game () {
        mode = DEFAULT;
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (!start) {
                    start = true;
                    bird.setV(0);
                }
                else {
                    bird.keyPressed(e);
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {}

        });
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        landscape.paint(g2d);
        g2d.setColor(new Color(210, 180, 140));
        bird.paint(g2d);
        

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
        g2d.drawString(Integer.toString(score), FRAME_WIDTH / 2 - g2d.getFont().getSize() / 2, 100);


        
        
    }

    public void move() {
        if (start) {
            if (mode == DEFAULT) {
                bird.move();
            }
            else {
                bird.autoMove();
            }
            
            landscape.move();
            if (landscape.obstacleCleared(bird.getX())) {
                updateScore();
            }
            //Check for collision
            if (landscape.collide(bird)) {
                gameOver();
            }
        }
        else {
            // bird.fakeMove();
            landscape.fakeMove();
        }
        
        
    }

    public void updateScore() {
        score += 1;
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }


    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();

        Game game  = new Game();
        game.setPreferredSize(new Dimension(game.FRAME_WIDTH, game.GAME_HEIGHT));
        game.setBackground(new Color(135, 206, 235));
        
        // JLabel label = new JLabel(Integer.toString(game.score), JLabel.CENTER);
        // label.setBackground(Color.red);
        // label.setPreferredSize(new Dimension(game.FRAME_WIDTH, game.LABEL_HEIGHT));
        JButton b = new JButton("Auto Mode");
        b.setFocusable(false);
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                game.setMode(Game.AUTO);
                game.setStart(true);
            }
            
        });

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(b, BorderLayout.PAGE_START);
        contentPane.add(game, BorderLayout.CENTER);

        // Dependencies

        game.bird = new Bird(game);
        game.landscape = new Landscape(game.FRAME_WIDTH, game.GAME_HEIGHT);
        
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("Flappy Bird");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Todo: remove
        
        while (true) {
            game.repaint();

            game.move();

            
            Thread.sleep(30);
            
            
        }
        
        
    }
}