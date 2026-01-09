import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {
    private GameMap map;
    private EnemyManager enemyManager; 
    private TowerManager TowerManager;
    private BufferedImage bgImage;
    
    public GamePanel(GameMap map, EnemyManager enemyManager, TowerManager TowerManager) {
        this.map = map;
        this.enemyManager = enemyManager; 
        this.TowerManager = TowerManager;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GREEN);
        
        try {
            bgImage = ImageIO.read(new File("images/kingtowerdefense map1.png"));
        } catch (IOException e) {
            System.out.println("Could not load background image: " + e.getMessage());
        }
    }
    
        @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        // removed map.drawPath(g); to hide the yellow path
        enemyManager.drawEnemies(g);
        TowerManager.drawTowers(g);

        displayMoney(g, TowerManager.getMoney());
        displayHealth(g, enemyManager.getHealth());
    }

    public void displayMoney(Graphics g, int money) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Money: " + money, 10, 20);
    }

    public void displayHealth(Graphics g, int health) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Health: " + health, 10, 40);
    }
}