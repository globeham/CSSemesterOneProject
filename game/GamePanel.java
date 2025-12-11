import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {
    private gameMap map;
    private EnemyManager enemyManager; 
    private towerManager towerManager;
    private BufferedImage bgImage;
    
    public GamePanel(gameMap map, EnemyManager enemyManager, towerManager towerManager) {
        this.map = map;
        this.enemyManager = enemyManager; 
        this.towerManager = towerManager;
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
        map.drawPath(g);
        enemyManager.drawEnemies(g);
        towerManager.drawTowers(g);
    }
}