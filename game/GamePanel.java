import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private gameMap map;
    private EnemyManager enemyManager; 
    private towerManager towerManager;
    
    public GamePanel(gameMap map, EnemyManager enemyManager, towerManager towerManager) {
        this.map = map;
        this.enemyManager = enemyManager; 
        this.towerManager = towerManager;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GREEN);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bgImage = imageIO.read(new File("kingtowerdefense map1.png"));
        g.drawImage(bgImage, 0, 0, null);
        map.drawPath(g);
        enemyManager.drawEnemies(g);
        towerManager.drawTowers(g);
    }
}