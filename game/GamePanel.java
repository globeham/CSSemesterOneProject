import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private gameMap map;
    private EnemyManager enemyManager; 
    
    public GamePanel(gameMap map, EnemyManager enemyManager) {
        this.map = map;
        this.enemyManager = enemyManager; 
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GREEN);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        map.drawPath(g);
        enemyManager.drawEnemies(g);
        
        g.setColor(Color.RED);
        for (point2D point : map.getPath()) {
            g.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
        }
    }
}