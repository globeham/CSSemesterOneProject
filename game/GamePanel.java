import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
GamePanel class handles the game display, including all game elements
*/
public class GamePanel extends JPanel {
    private GameMap map;
    private EnemyManager enemyManager; 
    private TowerManager TowerManager;
    private BufferedImage bgImage;
    private BufferedImage[] towerImages;
    private BufferedImage fireballImage;
    private int draggingTowerIndex = -1;
    private int dragX = -1, dragY = -1;
    
    public GamePanel(GameMap map, EnemyManager enemyManager, TowerManager TowerManager) {
        this(map, enemyManager, TowerManager, "images/kingtowerdefense map1.png");
    }
    
    public GamePanel(GameMap map, EnemyManager enemyManager, TowerManager TowerManager, String imageFile) {
        this.map = map;
        this.enemyManager = enemyManager; 
        this.TowerManager = TowerManager;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GREEN);
        
        // Load tower images for dragging preview
        String[] towerImageFiles = {"images/knight.png", "images/devil.png", "images/king.png", "images/ninja.png", "images/wizard.png"};
        towerImages = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            try {
                towerImages[i] = ImageIO.read(new File(towerImageFiles[i]));
            } catch (IOException e) {
                System.out.println("Could not load tower image: " + towerImageFiles[i]);
            }
        }
        
        // Load fireball image for wizard projectiles
        try {
            fireballImage = ImageIO.read(new File("images/fireball.png"));
        } catch (IOException e) {
            System.out.println("Could not load fireball image");
            fireballImage = null;
        }
        
        try {
            bgImage = ImageIO.read(new File(imageFile));
        } catch (IOException e) {
            System.out.println("Could not load background image: " + e.getMessage());
            bgImage = null;
        }
    }
    
    public void setDraggingTower(int index) {
        this.draggingTowerIndex = index;
        this.dragX = -1;
        this.dragY = -1;
        ((KingTowerDefense3000)javax.swing.SwingUtilities.getWindowAncestor(this)).showGlassPane(true);
    }
    
    public boolean isDragging() {
        return draggingTowerIndex != -1;
    }
    
    public void updateDragPosition(int frameX, int frameY) {
        Point p = getLocation();
        this.dragX = frameX - p.x;
        this.dragY = frameY - p.y;
        repaint();
    }
    
    public void tryPlaceTower(int frameX, int frameY) {
        if (draggingTowerIndex != -1) {
            // Convert to panel coordinates
            Point p = getLocation();
            int panelX = frameX - p.x;
            int panelY = frameY - p.y;
            
            // Check distance to path
            if (map.distanceToPath(panelX, panelY) < 50) {
                JOptionPane.showMessageDialog(null, "Cannot place tower here (too close to path).");
                draggingTowerIndex = -1;
                dragX = -1;
                dragY = -1;
                ((KingTowerDefense3000)javax.swing.SwingUtilities.getWindowAncestor(this)).showGlassPane(false);
                return;
            }
            
            Tower t = createTower(draggingTowerIndex, 0, 0);
            if (!TowerManager.placeTower(t, panelX, panelY)) {
                JOptionPane.showMessageDialog(null, "Cannot place tower here (not enough money).");
            }
            draggingTowerIndex = -1;
            dragX = -1;
            dragY = -1;
            repaint();
            ((KingTowerDefense3000)javax.swing.SwingUtilities.getWindowAncestor(this)).showGlassPane(false);
        }
    }
    
    private Tower createTower(int index, int x, int y) {
        Tower tower;
        switch (index) {
            case 0: tower = new Knight(x, y, 0, 0, null); break;
            case 1: tower = new Devil(x, y, 0, 0, null); break;
            case 2: tower = new King(x, y, 0, 0, null); break;
            case 3: tower = new Ninja(x, y, 0, 0, null); break;
            case 4: 
                tower = new Wizard(x, y, 0, 0, null);
                tower.setProjectileImage(fireballImage);
                break;
            default: return null;
        }
        return tower;
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

        // Draw drag preview
        if (draggingTowerIndex != -1 && dragX != -1) {
            double dist = map.distanceToPath(dragX, dragY);
            g.setColor(dist >= 50 ? Color.GREEN : Color.RED);
            g.drawOval(dragX - 25, dragY - 25, 50, 50);
            if (towerImages[draggingTowerIndex] != null) {
                g.drawImage(towerImages[draggingTowerIndex], dragX - 16, dragY - 16, 32, 32, null);
            }
        }

        // UI overlays
        if (TowerManager != null) displayMoney(g, TowerManager.getMoney());
        if (enemyManager != null) displayHealth(g, enemyManager.getHealth());
    }

    public void displayMoney(Graphics g, int money) {
        // Draw semi-transparent background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(5, 5, 120, 20);
        
        // Draw text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Money: " + money, 10, 20);
    }

    public void displayHealth(Graphics g, int health) {
        // Draw semi-transparent background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(5, 25, 120, 20);
        
        // Draw text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Health: " + health, 10, 40);
    }
}