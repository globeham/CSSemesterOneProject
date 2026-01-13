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
        this(map, enemyManager, TowerManager, "images/kingtowerdefense map1.png");
    }
    
    public GamePanel(GameMap map, EnemyManager enemyManager, TowerManager TowerManager, String imageFile) {
        this.map = map;
        this.enemyManager = enemyManager; 
        this.TowerManager = TowerManager;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GREEN);
        
        try {
            bgImage = ImageIO.read(new File(imageFile));
        } catch (IOException e) {
            System.out.println("Could not load background image: " + e.getMessage());
            bgImage = null;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelW = this.getWidth();
        int panelH = this.getHeight();

        // draw background image stretched to panel size (keeps old behavior)
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, panelW, panelH, null);
        } else {
            g.setColor(getBackground());
            g.fillRect(0,0,panelW,panelH);
        }

        // tell map the current display size so it can scale the original path
        map.setDisplaySize(panelW, panelH);

        // push the scaled display path into the enemy manager (so it uses display coords)
        if (enemyManager != null) {
            enemyManager.setPath(map.getPath());
        }

        // draw enemies and towers (they expect display coords now)
        if (enemyManager != null) enemyManager.drawEnemies(g);
        if (TowerManager != null) TowerManager.drawTowers(g);

        // UI overlays
        if (TowerManager != null) displayMoney(g, TowerManager.getMoney());
        if (enemyManager != null) displayHealth(g, enemyManager.getHealth());
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