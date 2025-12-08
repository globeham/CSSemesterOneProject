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
        
        map.drawPath(g);
        enemyManager.drawEnemies(g);
        towerManager.drawTowers(g);
        
        g.setColor(Color.RED);
        for (point2D point : map.getPath()) {
            g.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
            BufferedImage myPicture = ImageIO.read(new File("brownpath.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel);
            picLabel.setVerticalAlignment(point.getY());
            picLabel.setHorizontalAlignment(point.getX());

        }
    }
}